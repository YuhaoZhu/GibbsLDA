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
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LDADataParser {
    private String dataInputPath = "";
    private String dataOutputPath = "";
    private Map<String, String> resultAns;
    private Map<String, String> resultQus;
    private Map<String, String> relations;
    private Map<String, Double> idfs;
    private Pattern pattern = Pattern.compile("^[a-zA-Z_0-9'\\u2019]+$");
//    private Pattern pattern = Pattern.compile("^[\\u0000-\\u0080]+$");

    public LDADataParser(String inputPath, String outputPath) {
        this.dataInputPath = inputPath;
        this.dataOutputPath = outputPath;
        resultAns = new HashMap<String, String>();
        resultQus = new HashMap<String, String>();
//        relations = new HashMap<String, List<String>>();
        relations = new HashMap<String, String>();
    }

    public void parse() {
        File input = new File(dataInputPath);
        if(input.exists() && input.isFile()) {

            try {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(input);

                doc.getDocumentElement().normalize();
                NodeList nList = doc.getElementsByTagName("row");
                List<List<String>> docs = new ArrayList<List<String>>();
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
                            String res = join(feats.toArray());
                            resultQus.put(id, res);
                        } else if(attributes.getNamedItem("PostTypeId").getNodeValue().equals("2")) {
                            // answer
                            String body = attributes.getNamedItem("Body").getNodeValue();
                            List<String> feats = tokenize(body);
                            String res = join(feats.toArray());
                            resultAns.put(id, res);
                            String parentId = attributes.getNamedItem("ParentId").getNodeValue();
                            relations.put(id, parentId);
                        }
                    }
                }

//                IDFCalculator calc = new IDFCalculator(docs);
//                idfs = calc.calc();

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

    public void dumps() {
        Map<String, List<String>> answer = new HashMap<String, List<String>>();
        Map<String, String> question = new HashMap<String, String>();
        for(Map.Entry<String, String> entry : resultAns.entrySet()) {
            String qid = relations.get(entry.getKey());
            if(answer.containsKey(qid)) {
                answer.get(qid).add(entry.getValue());
            } else {
                List<String> res = new ArrayList<String>();
                res.add(entry.getValue());
                answer.put(qid, res);
            }

            if(!question.containsKey(qid)) {
                question.put(qid, resultQus.get(qid));
            }
        }

        // write to answer folder
        for (Map.Entry<String, List<String>> entry : answer.entrySet()) {
            try {
                for (int i = 0; i < entry.getValue().size();i++) {
                    String line = entry.getValue().get(i);
                    String qid = String.format("%08d", Integer.parseInt(entry.getKey()));
                    String path = dataOutputPath + "/Answer/ans_" + qid + "_" + String.format("%02d", i) + ".txt";
                    FileWriter fw = new FileWriter(path);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(line + "\n");
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // write to question folder
        for(Map.Entry<String, String> entry : question.entrySet()) {
            String qid = String.format("%08d", Integer.parseInt(entry.getKey()));
            String path = dataOutputPath + "/Question/qus_" + qid + ".txt";
            try {
                FileWriter fw = new FileWriter(path);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(entry.getValue() + "\n");
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void dumpIDF() {
        String filename = dataOutputPath + "_idfs";
        File ans = new File(filename);
        try {
            FileWriter ansOut = new FileWriter(ans.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(ansOut);
            for(Map.Entry<String, Double> entry : idfs.entrySet()) {
                bw.write(entry.getKey() + "\t" + entry.getValue() + "\n");
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<String> tokenize(String body) {
        String text = Jsoup.parse(StringEscapeUtils.unescapeHtml4(body)).text();
        Analyzer analyzer = new StandardAnalyzer(CharArraySet.EMPTY_SET);
//        Analyzer analyzer = new StandardAnalyzer();
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

    boolean isEnglish(String string) {
//        return CharMatcher.ASCII.matchesAllOf(string);
        Matcher matcher = pattern.matcher(string);
        return matcher.find();
    }

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

    public static void main(String[] args) {
        String inputPath = "/Users/zhoutsby/Downloads/english.stackexchange.com/sample.xml";
        String outputPath = "/Users/zhoutsby/Downloads/english.stackexchange.com/result2";
        LDADataParser parser = new LDADataParser(inputPath, outputPath);
        parser.parse();
        parser.dumps();
    }
}
