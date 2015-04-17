/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gibbslda;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author YuhaoZhu
 */
public class LDAInfer {

    int[][] nmk;//given document m, count times of topic k. M*K  //output
    int[][] nkt;//given topic k, count times of word t. K*V  // Must save
    int[] nmkSum;//Sum for each row in nmk  
    int[] nktSum;//Sum for each row in nkt  // Could save
    int[][] z;
    int[][] doc;
    int iter;

    double alpha;
    double beta;

    int V, K, M;//V is vocabury size, k is the topic num, M is the document number

    String documentPath;
    String modelPath;
    //String resultPath;

    HashMap<String, Integer> featureToIndexMap;//must save
    ArrayList<String> indexToFeatureMap;

    public LDAInfer(String documentPath, String modelPath) {
        featureToIndexMap = new HashMap<String, Integer>();
        indexToFeatureMap = new ArrayList<String>();
        this.documentPath = documentPath;
        this.modelPath = modelPath;
        //this.resultPath = resultPath;
    }

    public void init(Documents docs) throws FileNotFoundException, IOException {

        this.M = docs.allDocumentsContent.size();

        String thisLine;

        //restore indexToFeature
        BufferedReader reader = new BufferedReader(new FileReader(modelPath + "LDA.indexToFeature"));
        while ((thisLine = reader.readLine()) != null) {
            //System.out.println(thisLine);
            indexToFeatureMap.add(thisLine);
        }
        //System.out.println(indexToFeatureMap.size());
        this.V = indexToFeatureMap.size();
        reader.close();

        reader = new BufferedReader(new FileReader(modelPath + "LDA.featureToIndex"));
        String[] splited;
        while ((thisLine = reader.readLine()) != null) {
            splited = thisLine.split("\t");
            featureToIndexMap.put(splited[0], Integer.parseInt(splited[1]));
        }
        //System.out.println(featureToIndexMap.size());
        reader.close();

        reader = new BufferedReader(new FileReader(modelPath + "LDA.nktSum"));
        thisLine = reader.readLine();
        splited = thisLine.split("\t");
        this.K = splited.length;
        nktSum = new int[K];
        for (int k = 0; k < K; k++) {
            nktSum[k] = Integer.parseInt(splited[k]);
        }
        //System.out.println(K);
        reader.close();

        reader = new BufferedReader(new FileReader(modelPath + "LDA.nkt"));
        nkt = new int[K][V];
        for (int k = 0; k < K; k++) {
            thisLine = reader.readLine();
            splited = thisLine.split("\t");
            for (int v = 0; v < V; v++) {
                nkt[k][v] = Integer.parseInt(splited[v]);
            }
        }
        reader.close();

        reader = new BufferedReader(new FileReader(modelPath + "LDA.param"));
        this.alpha = Double.parseDouble(reader.readLine());
        this.beta = Double.parseDouble(reader.readLine());
        this.iter = Integer.parseInt(reader.readLine())/10;
        reader.close();

        //System.out.println(this.alpha);
        //System.out.println(this.beta);
        nmk = new int[M][K];
        nmkSum = new int[M];

        doc = new int[M][];
        for (int i = 0; i < M; i++) {
            Documents.Document thisDoc = docs.allDocumentsContent.get(i);
            int[] mappedDoc = thisDoc.mappedDoc;
            int size = mappedDoc.length;
            doc[i] = new int[size];
            for (int j = 0; j < size; j++) {
                doc[i][j] = mappedDoc[j];
            }
        }

        z = new int[M][];
        for (int m = 0; m < M; m++) {
            Documents.Document thisDoc = docs.allDocumentsContent.get(m);
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
                //nkt[topic][doc[m][j]]++;
                //word that has been assigned to topic
                //no need for infer
                //nktSum[topic]++;
            }
            //
            nmkSum[m] = size;
        }

        System.out.println("Model restored successfully");

    }

    public void infer(Documents docs) throws IOException {
        java.util.Date date = new java.util.Date();
        for (int i = 0; i < iter; i++) {
            
            date = new java.util.Date();
            System.out.print(new Timestamp(date.getTime()));
            System.out.println(" Iterations: " + i);
            
            for (int m = 0; m < M; m++) {
                int size = docs.allDocumentsContent.get(m).mappedDoc.length;
                for (int j = 0; j < size; j++) {
                    int newTopic = samplingTopic(m, j);
                    z[m][j] = newTopic;
                }
            }
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(this.modelPath + "LDA.result"));
        for (int m = 0; m < M; m++) {
            writer.write(docs.filenameList.get(m)+"\t");
            for (int k = 0; k < K; k++) {
                writer.write(nmk[m][k] + "\t");
            }
            writer.write("\n");
        }
        writer.close();

    }

    private int samplingTopic(int m, int n) {
        int topic = z[m][n];

        //nkt[topic][doc[m][n]]--;
        //nmkSum[m]--;
        //nktSum[topic]--;
        double[] p = new double[K];
        for (int k = 0; k < K; k++) {
            //System.out.print(nkt[k]);
            //System.out.print(doc[m][n]);
            //System.out.print(beta);
            //System.out.print(nktSum[k]);
            //System.out.print(V);
            //System.out.print(alpha);
            //System.out.print(nmk[m][k]);
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

        nmk[m][topic]--;
        nmk[m][newTopic]++;
        //change to topic
        //nkt[newTopic][doc[m][n]]++;
        //nmkSum[m]++;
        //change to topic
        //nktSum[newTopic]++;
        return newTopic;
    }

}