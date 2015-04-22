package com.usc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhoutsby on 4/22/15.
 */
public class TFIDFCalculator {

    public Map<String, Integer> tf(List<String> doc) {
        Map<String, Integer> tfVector = new HashMap<String, Integer>();
        for (String word : doc) {
            if (tfVector.containsKey(word)) {
                tfVector.put(word, tfVector.get(word) + 1);
            } else {
                tfVector.put(word, 1);
            }
        }
        return tfVector;
    }

    public Map<String, Map<String, Integer>> tfAll(List<List<String>> docs) {
        Map<String, Map<String, Integer>> resTF = new HashMap<String, Map<String, Integer>>();
        for (int i = 0; i < docs.size(); i++) {
            List<String> doc = docs.get(i);
            resTF.put(String.valueOf(i + 1), tf(doc));
        }
        return resTF;
    }

    public Map<String, Float> idf(Map<String, Map<String, Integer>> resTF) {
        HashMap<String, Float> resIDF = new HashMap<String, Float>();
        int numDoc = resTF.size();

        for (int i = 0; i < numDoc; i++) {
            Map<String, Integer> tfVector = resTF.get(String.valueOf(i + 1));
            for (String word : tfVector.keySet()) {
                if (resIDF.containsKey(word)) {
                    resIDF.put(word, resIDF.get(word) + 1);
                } else {
                    resIDF.put(word, 1.0f);
                }
            }
        }

        for (String word : resIDF.keySet()) {
            float value = (float) Math.log(numDoc / (resIDF.get(word) + 1));
            resIDF.put(word, value);
        }

        return resIDF;
    }

    public Map<String, Map<String, Float>> tfIdf(Map<String, Map<String, Integer>> resTF, Map<String, Float> resIDF) {
        Map<String, Map<String, Float>> resTfIdf = new HashMap<String, Map<String, Float>>();

        int numDoc = resTF.size();
        for (int i = 0; i < numDoc; i++) {
            String key = String.valueOf(i + 1);
            Map<String, Integer> tfVector = resTF.get(key);
            Map<String, Float> tmp = new HashMap<String, Float>();
            try {
                for (String word : tfVector.keySet()) {
                        float value = tfVector.get(word) * resIDF.get(word);
                        tmp.put(word, value);

                }
            } catch (Exception e) {
                System.out.println(tfVector);
            }
            resTfIdf.put(key, tmp);
        }

        return resTfIdf;
    }

    public Map<String, Map<String, Float>> calc(List<List<String>> docs) {
        System.out.println(docs.size());
        Map<String, Map<String, Integer>> resTF = tfAll(docs);
        System.out.println(resTF.size());
        Map<String, Float> resIDF = idf(resTF);
        return tfIdf(resTF, resIDF);
    }
}
