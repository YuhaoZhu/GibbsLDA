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
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author YuhaoZhu
 */
public class Documents {

    ArrayList<Document> allDocumentsContent;
    HashMap<String, Integer> featureToIndexMap;//must save
    HashMap<String, Integer> featureCountsMap;
    ArrayList<String> indexToFeatureMap; //must save
    static HashMap<String,Integer> stopWordMap;
    ArrayList<String> filenameList;

    public Documents() throws IOException {
        allDocumentsContent = new ArrayList<Document>();
        featureToIndexMap = new HashMap<String, Integer>();
        featureCountsMap = new HashMap<String, Integer>();
        indexToFeatureMap = new ArrayList<String>();
        stopWordMap=new HashMap<String,Integer>();
        filenameList=new ArrayList<String>();
        loadStopWords();    
    }
    
    private void loadStopWords() throws FileNotFoundException, IOException{
        BufferedReader reader = new BufferedReader(new FileReader("stopwords.txt"));
        String thisLine;
        while ((thisLine=reader.readLine())!=null){
            stopWordMap.put(thisLine, 0);
        }
        
    }
    public void readTrainingDocuments(String trainingPath) throws IOException {
        for (File docPath : new File(trainingPath).listFiles(new FileNameSelector("txt"))) {
            filenameList.add(docPath.getName());
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
            
            for (int i=0;i<tmp.length;i++){
                if (isStopFeature(tmp[i])==false) {
                    words.add(tmp[i]);
                }
            }
            
           // words = Arrays.asList(tmp);
            //for (int i = 0; i < words.size(); i++) {
            //    if (isStopFeature(words.get(i))) {
            //        words.remove(i);
            //        i = i - 1;
            //    }
            //}

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
            return stopWordMap.containsKey(str.toLowerCase());
        }
    }

    public static class FileNameSelector implements FilenameFilter {
        
        String ext=".";
        public FileNameSelector(String txt) {
            ext=ext+txt;
        }

        @Override
        public boolean accept(File dir, String name) {
            return name.endsWith(ext); //To change body of generated methods, choose Tools | Templates.
        }
    }

}
