/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gibbslda;

import gibbslda.Documents.Document;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author YuhaoZhu
 */
public class LDAModel {

    double alpha;
    double beta;
    int iterations;
    
    double W;

    int[][] doc;//doc[n][] is the Nth Doc and doc[n][m] is the Mth word in Nth Document
    int V, K, M;//V is vocabury size, k is the topic num, M is the document number
    int[][] z;// z[n][m] the topic disturbation

    int[][] nmk;//given document m, count times of topic k. M*K  //Could save
    int[][] nkt;//given topic k, count times of word t. K*V  // Must save
    int[] nmkSum;//Sum for each row in nmk  
    int[] nktSum;//Sum for each row in nkt  // Could save
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
        W=docs.getWordCounts();

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
        
        //
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
                //no need for infer
                nkt[topic][doc[m][j]]++;
                //word that has been assigned to topic
                //no need for infer
                nktSum[topic]++;
            }
            //
            nmkSum[m] = size;
        }

    }

    public void trainingModel(Documents docs, int saveAtIter, String modelPath) throws IOException {
        java.util.Date date = new java.util.Date();

        for (int iter = 0; iter < iterations; iter++) {
            date = new java.util.Date();
            System.out.print(new Timestamp(date.getTime()));
            System.out.println(" Iterations: " + iter);

            //sampling a new topic to update z[][]
            for (int m = 0; m < M; m++) {
                int size = docs.allDocumentsContent.get(m).mappedDoc.length;
                for (int j = 0; j < size; j++) {
                    int newTopic = samplingTopic(m, j);
                    z[m][j] = newTopic;
                }
            }

            if (iter >= saveAtIter - 1) {
                updatePara();
                displayLDA(docs,modelPath,20,iter);
                //saveModel(iter + 1, docs, modelPath);
            }
            if (iter % 10 == 1) {
                System.out.println("Preplexity is :" + String.valueOf(getPerplexity(docs)));
            }
        }

        updatePara();
        saveModel(iterations, docs, modelPath);
        displayLDA(docs,modelPath,20,0);
    }

    private void saveModel(int iter, Documents docs, String modelPath) throws IOException {
        String modelName = "LDA";

        //K*V
        BufferedWriter writer = new BufferedWriter(new FileWriter(modelPath + modelName + ".nkt"));
        for (int k = 0; k < K; k++) {
            for (int v = 0; v < V; v++) {
                writer.write(nkt[k][v] + "\t");
            }
            writer.write("\n");
        }
        writer.close();

        //M*K
        writer = new BufferedWriter(new FileWriter(modelPath + modelName + ".nmk"));
        for (int m = 0; m < M; m++) {
            for (int k = 0; k < K; k++) {
                writer.write(nmk[m][k] + "\t");
            }
            writer.write("\n");
        }
        writer.close();
        
        writer = new BufferedWriter(new FileWriter(modelPath + modelName + ".nktSum"));
        for (int k=0;k<K;k++){
            writer.write(nktSum[k]+"\t");
        }
        writer.close();
        
        writer = new BufferedWriter(new FileWriter(modelPath + modelName + ".nmkSum"));
        for (int m=0;m<M;m++){
            writer.write(nmkSum[m]+"\t");
        }
        writer.close();
        
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(modelPath + modelName + ".featureToIndex"));
        oos.writeObject(docs.featureToIndexMap);
        oos.flush();
        oos.close();
        
        //writer = new BufferedWriter(new FileWriter(modelPath + modelName + ".featureToIndex"));
        //HashMap<String,Integer> map=docs.featureToIndexMap;
        //for(Entry<String, Integer> entry :map.entrySet()) {
        //    writer.write(entry.getKey().replace("\n", "")+"\t"+entry.getValue()+"\n");
        //}
        //writer.close();
        
        oos = new ObjectOutputStream(new FileOutputStream(modelPath + modelName + ".indexToFeature"));
        oos.writeObject(docs.indexToFeatureMap);
        oos.flush();
        oos.close();
        
        //writer = new BufferedWriter(new FileWriter(modelPath + modelName + ".indexToFeature"));
        //int size=docs.indexToFeatureMap.size();
        //for (int i=0;i<size;i++){
        //    writer.write(String.valueOf(docs.indexToFeatureMap.get(i).replace("\n", ""))+"\n");
        //}
        //writer.close();
        
        writer = new BufferedWriter(new FileWriter(modelPath + modelName + ".param"));
        writer.write(String.valueOf(this.alpha)+"\n");
        writer.write(String.valueOf(this.beta)+"\n");
        writer.write(String.valueOf(this.iterations)+"\n");
        
        writer.close();
                
    }
    

    public void displayLDA(Documents docs, String modelPath, int topNum,int iter) throws IOException {
        String modelName = "LDA"+String.valueOf(iter);
        BufferedWriter writer = new BufferedWriter(new FileWriter(modelPath + modelName + ".display"));
        for (int k = 0; k < K; k++) {
            List<Integer> indexToFeatureArray = new ArrayList<Integer>();
            for (int v = 0; v < V; v++) {
                indexToFeatureArray.add(new Integer(v));
            }
            Collections.sort(indexToFeatureArray, new LDAModel.tmpVar(phi[k]));
            writer.write("topic " + k + "\t:\t");
            for (int t = 0; t < topNum; t++) {
                writer.write(docs.indexToFeatureMap.get(indexToFeatureArray.get(t)) + " " + phi[k][indexToFeatureArray.get(t)] + "\t");
            }
            writer.write("\n");
        }
        writer.close();
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
            p[k] = (nkt[k][doc[m][n]] + beta) / (nktSum[k] + V * beta) * (nmk[m][k] + alpha); // (nmkSum[m] + K * alpha);
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
        //change to topic
        nkt[newTopic][doc[m][n]]++;
        nmkSum[m]++;
        //change to topic
        nktSum[newTopic]++;
        return newTopic;
    }

    public class tmpVar implements Comparator<Integer> {

        public double[] prob;

        public tmpVar(double[] sortProb) {
            this.prob = sortProb;
        }

        @Override
        public int compare(Integer o1, Integer o2) {

            if (prob[o1] > prob[o2]) {
                return -1;
            } else if (prob[o1] < prob[o2]) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    private double getPerplexity(Documents docs) {
        double sumLogOverDocs = 0;
        double theta_jw, phi_jw;
        int w;
        int[] thisDoc;
        for (int m = 0; m < M; m++) {
            //float sumOfTheraOverT = 0;
            //for (int k = 0; k < K; k++) { // assume K is globle
            //    sumOfTheraOverT += (float) nmk[m][k];
            //}

            double sumLogOverTokens = 0;
            double sumOverTopics;
            //thisDoc = /*docs[m];*/ docs.allDocumentsContent.get(m).mappedDoc;
            int size = /*docs[m].length;*/ docs.allDocumentsContent.get(m).mappedDoc.length;
            for (int j = 0; j < size; j++) {
                sumOverTopics = 0;
                w = doc[m][j];
                for (int k = 0; k < K; k++) // assume numOfTopics is globle
                {
                    theta_jw = (alpha + (float) (nmk[m][k]))
                            / (alpha * K + nmkSum[m]);

                    phi_jw = (beta + (float) (nkt[k][w]))
                            / ((float) V * beta + (float) nktSum[k]);

                    sumOverTopics += theta_jw * phi_jw;
                }
                sumLogOverTokens += Math.log(sumOverTopics);
            }
            sumLogOverDocs += sumLogOverTokens;
        }
        double logProbabilities;

        //int numOfTotalWords = 1000000;   // assume numOfTotalWords is globle
        logProbabilities = (-sumLogOverDocs) / W;

        return Math.exp(logProbabilities);
    }

}
