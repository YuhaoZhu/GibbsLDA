package com.usc;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.CharArraySet;
import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by zhoutsby on 4/13/15.
 */
public class DataParser {
    private String dataInputPath = "";
    private String dataOutputPath = "";
    private Map<String, List<String>> resultAns;
    private Map<String, List<String>> resultQus;
    private Map<String, String> relations;

    public DataParser(String inputPath, String outputPath) {
        this.dataInputPath = inputPath;
        this.dataOutputPath = outputPath;
        resultAns = new HashMap<String, List<String>>();
        resultQus = new HashMap<String, List<String>>();
        relations = new HashMap<String, String>();
    }

    /**
     * read the data from xml and parse it to plain texts
     */
    public void parse() {
        File input = new File(dataInputPath);
        if(input.exists() && input.isFile()) {

            try {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(input);

                doc.getDocumentElement().normalize();

//                System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

                NodeList nList = doc.getElementsByTagName("row");
                for(int i = 0; i < nList.getLength(); i++) {
                    Node node = nList.item(i);
                    if(node.getNodeType() == Node.ELEMENT_NODE) {
                        NamedNodeMap attributes = node.getAttributes();
                        String id = attributes.getNamedItem("Id").getNodeValue();
                        if(attributes.getNamedItem("PostTypeId").getNodeValue().equals("1")) {
                            //question
                            String title = attributes.getNamedItem("Title").getNodeValue();
                            String body = attributes.getNamedItem("Body").getNodeValue();
                            List<String> feats = tokenize(title + " " + body);
                            resultQus.put(id, feats);
                        } else if(attributes.getNamedItem("PostTypeId").getNodeValue().equals("2")) {
                            // answer
                            String body = attributes.getNamedItem("Body").getNodeValue();
                            List<String> feats = tokenize(body);
                            resultAns.put(id, feats);
                            String parentId = attributes.getNamedItem("ParentId").getNodeValue();
                            relations.put(id, parentId);
                        }
                    }
                }

                buildResult();

            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File does not exists!");
            System.exit(-1);
        }
    }


    /**
     * build the List of plain text doc to List of doc <tokens, tf-idf> format
     */
    private void buildResult() {
        List<List<String>> ansArray = new ArrayList<List<String>>();
        List<List<String>> qusArray = new ArrayList<List<String>>();
        Map<String, Integer> newAnsMapping = new HashMap<String, Integer>();
        Map<String, Integer> newQusMapping = new HashMap<String, Integer>();
        Map<String, String> newRelation = new HashMap<String, String>();

        Iterator<Map.Entry<String, List<String>>> iterator = resultAns.entrySet().iterator();
        int i = 1, j = 1;
        while (iterator.hasNext()) {
            Map.Entry<String, List<String>> entry = iterator.next();
            String ansId = entry.getKey();
            // add to a new ansArray
            List<String> ansCont = entry.getValue();
            ansArray.add(ansCont);
            // add new Mapping
            String newAnsId = String.valueOf(i);
            newAnsMapping.put(ansId, i);
            i++;
            // find its parent question
            String parentId = relations.get(ansId);
            String newParentId;
            if(newQusMapping.containsKey(parentId)) {
                newParentId = String.valueOf(newQusMapping.get(parentId));
            } else {
                // mapping parent ID to new constructed parent ID
                newParentId = String.valueOf(j);
                qusArray.add(resultQus.get(parentId));
                newQusMapping.put(parentId, j);
                j++;
            }
            newRelation.put(newAnsId, newParentId);
        }

        TFIDFCalculator calculator = new TFIDFCalculator();
        Map<String, Map<String, Float>> newAns = calculator.calc(ansArray);
        Map<String, Map<String, Float>> newQus = calculator.calc(qusArray);
        dumps(newAns, dataOutputPath + "_answer.txt");
        dumps(newQus, dataOutputPath + "_question.txt");

        // write relationship
        try {
            FileWriter fw = new FileWriter(dataOutputPath + "_relation.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            for(Map.Entry<String, String> entry : newRelation.entrySet()) {
                bw.write(entry.getKey() + " " + entry.getValue() + "\n");
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * dumps Map file to the disk
     * @param docs
     * @param output
     */
    public void dumps(Map<String, Map<String, Float>> docs, String output) {
        try {
            FileWriter fw = new FileWriter(output);
            BufferedWriter bw = new BufferedWriter(fw);

            int numDoc = docs.size();
            for(int i = 0; i < numDoc; i++) {
                String key = String.valueOf(i + 1);
                Map<String, Float> doc = docs.get(key);
                List<String> line = new ArrayList<String>();
                for(Map.Entry<String, Float> entry : doc.entrySet()) {
                    line.add(entry.getKey());
                    line.add(String.valueOf(entry.getValue()));
                }
                String lineRes = join(line.toArray());
                bw.write(lineRes + '\n');
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * convert Array to whitespace separated String
     * @param a
     * @return
     */
    public String join(Object[] a) {
        if(a == null)
            return "null";
        int iMax = a.length - 1;
        if(iMax == -1)
            return "";
        StringBuilder sb = new StringBuilder();
        for(int i = 0;;i++) {
            sb.append(String.valueOf(a[i]));
            if(i == iMax)
                return sb.toString();
            sb.append(" ");
        }
    }


    /**
     * tokenize the body text field of input xml
     * @param body
     * @return
     */
    public List<String> tokenize(String body) {
        String text = Jsoup.parse(StringEscapeUtils.unescapeHtml4(body)).text();
//        Analyzer analyzer = new StandardAnalyzer(CharArraySet.EMPTY_SET);
        Analyzer analyzer = new StandardAnalyzer();
        List<String> result = new ArrayList<String>();
        try {
            TokenStream tokenStream = analyzer.tokenStream("body", text);
            CharTermAttribute cattr = tokenStream.addAttribute(CharTermAttribute.class);
            tokenStream.reset();
            while(tokenStream.incrementToken()) {
                String token = cattr.toString();
                result.add(token);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        String inputPath = "/Users/zhoutsby/Downloads/english.stackexchange.com/test.xml";
        String outputPath = "/Users/zhoutsby/Downloads/english.stackexchange.com/features_test";
//        String inputPath = "/Users/zhoutsby/Downloads/english.stackexchange.com/Posts.xml";
//        String outputPath = "/Users/zhoutsby/Downloads/english.stackexchange.com/features";
        DataParser parser = new DataParser(inputPath, outputPath);
        parser.parse();
//        parser.dumps();
    }
}
