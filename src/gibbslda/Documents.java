/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gibbslda;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author YuhaoZhu
 */
public class Documents {

    ArrayList<Document> allDocumentsContent;
    HashMap<String, Integer> featureToIndexMap;
    HashMap<String, Integer> featureCountsMap;
    ArrayList<String> indexToFeatureMap;

    public Documents() {
        allDocumentsContent = new ArrayList<Document>();
        featureToIndexMap = new HashMap<String, Integer>();
        featureCountsMap = new HashMap<String, Integer>();
        indexToFeatureMap = new ArrayList<String>();
    }

    public void readTrainingDocuments(String trainingPath) throws IOException {
        for (File docPath : new File(trainingPath).listFiles()) {
            Document doc = new Document(docPath.getAbsolutePath(), featureToIndexMap, featureCountsMap, indexToFeatureMap);
            allDocumentsContent.add(doc);
        }
    }

    public double getWordCounts() {
        double result = 0;
        for (int i = 0; i < allDocumentsContent.size(); i++) {
            result+=allDocumentsContent.get(i).mappedDoc.length;
        }
        return result;
    }

    public static class Document {

        private String absolutePath;
        int[] mappedDoc;

        public Document(String absolutePath, HashMap<String, Integer> featureToIndexMap, HashMap<String, Integer> featureCountsMap, ArrayList<String> indexToFeatureMap) throws IOException {
            List<String> words = new ArrayList<String>();

            this.absolutePath = absolutePath;
            String text = Files.toString(new File(absolutePath), Charsets.UTF_8);
            text = text.replaceAll("\\n", " ");
            String[] tmp = text.split(" ");

            words = Arrays.asList(tmp);
            for (int i = 0; i < words.size(); i++) {
                if (isStopFeature(words.get(i))) {
                    words.remove(i);
                    i = i - 1;
                }
            }

            this.mappedDoc = new int[words.size()];
            for (int i = 0; i < words.size(); i++) {
                String word = words.get(i);
                if (!featureToIndexMap.containsKey(word)) {
                    int newIndex = featureToIndexMap.size();
                    featureToIndexMap.put(word, newIndex);
                    indexToFeatureMap.add(word);
                    featureCountsMap.put(word, new Integer(1));
                    mappedDoc[i] = newIndex;
                } else {
                    mappedDoc[i] = featureToIndexMap.get(word);
                    featureCountsMap.put(word, featureCountsMap.get(word) + 1);
                }
            }

        }

        private boolean isStopFeature(String str) {
            return false;
        }

    }

}
