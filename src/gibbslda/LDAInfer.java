/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gibbslda;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author YuhaoZhu
 */
public class LDAInfer {

    int[][] nmk;//given document m, count times of topic k. M*K  //Could save
    int[][] nkt;//given topic k, count times of word t. K*V  // Must save
    int[] nmkSum;//Sum for each row in nmk  
    int[] nktSum;//Sum for each row in nkt  // Could save

    int V, K, M;//V is vocabury size, k is the topic num, M is the document number

    String documentPath;
    String modelPath;

    HashMap<String, Integer> featureToIndexMap;//must save
    ArrayList<String> indexToFeatureMap;

    public LDAInfer(String documentPath, String modelPath) {
        featureToIndexMap = new HashMap<String, Integer>();
        indexToFeatureMap = new ArrayList<String>();
        this.documentPath = documentPath;
        this.modelPath = modelPath;
    }

    public void init() throws FileNotFoundException, IOException {
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
        thisLine=reader.readLine();
        splited=thisLine.split("\t");
        this.K=splited.length;
        int[] nktSum=new int[K];
        for (int k=0;k<K;k++){
            nktSum[k]=Integer.parseInt(splited[k]);
        }
        //System.out.println(K);
        reader.close();
        
        reader = new BufferedReader(new FileReader(modelPath + "LDA.nkt"));
        int[][] nkt=new int[K][V];
        for (int k=0;k<K;k++){
            thisLine=reader.readLine();
            splited=thisLine.split("\t");
            for (int v=0;v<V;v++){
                nkt[k][v]=Integer.parseInt(splited[v]);
            }
        }
        reader.close();

    }

}
