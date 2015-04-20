/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gibbslda;

import gibbslda.Documents.Document;
import java.sql.Timestamp;

/**
 *
 * @author YuhaoZhu
 */
public class LDAModel {

    double alpha;
    double beta;
    int iterations;

    int[][] doc;//doc[n][] is the Nth Doc and doc[n][m] is the Mth word in Nth Document
    int V, K, M;//V is vocabury size, k is the topic num, M is the document number
    int[][] z;// z[n][m] the topic disturbation

    int[][] nmk;//given document m, count times of topic k. M*K  
    int[][] nkt;//given topic k, count times of term t. K*V  
    int[] nmkSum;//Sum for each row in nmk  
    int[] nktSum;//Sum for each row in nkt  
    double[][] phi;//Parameters for topic-word distribution K*V  
    double[][] theta;//Parameters for doc-topic distribution M*K  

    public LDAModel(double alpha, double beta, int iterations, int topicNums) {
        this.alpha = alpha;
        this.beta = beta;
        this.iterations = iterations;
        this.K = topicNums;
    }

    public void init(Documents docs) {
        M = docs.allDocumentsContent.size();
        V = docs.indexToFeatureMap.size();

        nmk = new int[M][K];
        nkt = new int[K][V];
        nmkSum = new int[M];
        nktSum = new int[K];
        phi = new double[K][V];
        theta = new double[M][K];

        doc = new int[M][];
        for (int i = 0; i < M; i++) {
            Document thisDoc = docs.allDocumentsContent.get(i);
            int[] mappedDoc = thisDoc.mappedDoc;
            int size = mappedDoc.length;
            doc[i] = new int[size];
            for (int j = 0; j < size; j++) {
                doc[i][j] = mappedDoc[j];
            }
        }

        z = new int[M][];
        for (int m = 0; m < M; m++) {
            Document thisDoc = docs.allDocumentsContent.get(m);
            int[] mappedDoc = thisDoc.mappedDoc;
            int size = mappedDoc.length;
            z[m] = new int[size];
            for (int j = 0; j < size; j++) {
                //random pick a topic
                int topic = (int) (Math.random() * K);
                //words in document m set with topic
                z[m][j] = topic;
                //document m has been labeled as topic
                nmk[m][topic]++;
                //for topic, word doc[m][j] has been assigned as to this topic
                nkt[topic][doc[m][j]]++;
                //word that has been assigned to topic
                nktSum[topic]++;
            }
            //
            nmkSum[m] = size;
        }

    }

    public void trainingModel(Documents docs, int saveAtIter) {
        java.util.Date date = new java.util.Date();

        for (int iter = 0; iter < iterations; iter++) {
            System.out.print("Iterations: " + iter + " begins at");
            System.out.println(new Timestamp(date.getTime()));

            //sampling a new topic to update z[][]
            for (int m = 0; m < M; m++) {
                int size = docs.allDocumentsContent.get(m).mappedDoc.length;
                for (int j = 0; j < size; j++) {
                    int newTopic = samplingTopic(m, j);
                    z[m][j] = newTopic;
                }
            }
        }
    }

    private void updatePara() {
        for (int k = 0; k < K; k++) {
            for (int t = 0; t < V; t++) {
                phi[k][t] = (nkt[k][t] + beta) / (nktSum[k] + V * beta);
            }
        }

        for (int m = 0; m < M; m++) {
            for (int k = 0; k < K; k++) {
                theta[m][k] = (nmk[m][k] + alpha) / (nmkSum[m] + K * alpha);
            }
        }
    }

    private int samplingTopic(int m, int n) {
        int topic = z[m][n];
        nmk[m][topic]--;
        nkt[topic][doc[m][n]]--;
        nmkSum[m]--;
        nktSum[topic]--;

        double[] p = new double[K];
        for (int k = 0; k < K; k++) {
            p[k] = (nkt[k][doc[m][n]] + beta) / (nktSum[k] + V * beta) * (nmk[m][k] + alpha) / (nmkSum[m] + K * alpha);
        }

        for (int k = 1; k < K; k++) {
            p[k] += p[k - 1];
        }

        //System.out.println(p[K - 1]);
        double u = Math.random() * p[K - 1]; //p[] is unnormalised  
        int newTopic;
        for (newTopic = 0; newTopic < K; newTopic++) {
            if (u < p[newTopic]) {
                break;
            }
        }

        nmk[m][newTopic]++;
        nkt[newTopic][doc[m][n]]++;
        nmkSum[m]++;
        nktSum[newTopic]++;
        return newTopic;
    }
}
