package com.usc;

import java.util.*;

/**
 * Created by zhoutsby on 4/21/15.
 */
public class IDFCalculator {
    List<List<String>> array;

    public IDFCalculator(List<List<String>> array) {
        this.array = array;
    }

    public Map<String, Double> calc() {
        int numDoc = 0;
        Map<String, Double> idfs = new HashMap<String, Double>();
        for(List<String> sentence : array) {
            Set<String> tmp = new HashSet<String>();
            for(String word: sentence) {
                tmp.add(word);
            }
            for(String word: tmp) {
                if (idfs.containsKey(word)) {
                    idfs.put(word, idfs.get(word) + 1);
                } else {
                    idfs.put(word, 1.0);
                }
            }
            numDoc++;
        }

        for(String key: idfs.keySet()) {
//            if(key.equals("mars")) {
//                System.out.println("showUp docs: " + idfs.get(key) + ", Total Docs: " + numDoc);
//            }
            idfs.put(key, Math.log(numDoc / (idfs.get(key) + 1)));
        }

        return idfs;
    }
}
