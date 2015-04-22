package com.usc;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
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
    private Map<String, List<String>> relations;
    private Map<String, Double> idfs;
    private Pattern pattern = Pattern.compile("^[a-zA-Z_0-9'\\u2019]+$");
//    private Pattern pattern = Pattern.compile("^[\\u0000-\\u0080]+$");

    public LDADataParser(String inputPath, String outputPath) {
        this.dataInputPath = inputPath;
        this.dataOutputPath = outputPath;
        resultAns = new HashMap<String, String>();
        resultQus = new HashMap<String, String>();
        relations = new HashMap<String, List<String>>();
    }

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
                List<List<String>> docs = new ArrayList<List<String>>();
                for(int i = 0; i < nList.getLength(); i++) {
                    Node node = nList.item(i);
                    if(node.getNodeType() == Node.ELEMENT_NODE) {
                        NamedNodeMap attributes = node.getAttributes();
                        String id = attributes.getNamedItem("Id").getNodeValue();
                        if(attributes.getNamedItem("PostTypeId").getNodeValue().equals("1")) {
                            //question
                            List<String> sentence = new ArrayList<String>();
                            String title = attributes.getNamedItem("Title").getNodeValue();
                            String body = attributes.getNamedItem("Body").getNodeValue();
                            String feats = tokenize(title + " " + body, sentence);
                            resultQus.put(id, feats);
                            docs.add(sentence);
                        } else if(attributes.getNamedItem("PostTypeId").getNodeValue().equals("2")) {
                            // answer
                            List<String> sentence = new ArrayList<String>();
                            String body = attributes.getNamedItem("Body").getNodeValue();
                            String feats = tokenize(body, sentence);
                            resultAns.put(id, feats);
                            String parentId = attributes.getNamedItem("ParentId").getNodeValue();
                            if(relations.containsKey(parentId)) {
                                relations.get(parentId).add(id);
                            } else {
                                List<String> list = new ArrayList<String>();
                                list.add(id);
                                relations.put(parentId, list);
                            }
                            docs.add(sentence);
                        }
                    }
                }

                IDFCalculator calc = new IDFCalculator(docs);
                idfs = calc.calc();

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
        try {
            Iterator<Map.Entry<String, List<String>>> it = relations.entrySet().iterator();
            while(it.hasNext()) {
                Map.Entry<String, List<String>> pair = it.next();
                String fileName = dataOutputPath + "_" + pair.getKey() + ".txt";
                File ans = new File(fileName);
                FileWriter ansOut = new FileWriter(ans.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(ansOut);
//                bw.write(resultQus.get(pair.getKey()) + '\n');
                for(String ansKey : pair.getValue()) {
                    bw.write(resultAns.get(ansKey) + '\n');
                }
                bw.close();
                it.remove();
            }
            System.out.println("DONE!");
        } catch (IOException e) {
            e.printStackTrace();
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

    public String tokenize(String body, List<String> sentence) {
        String text = Jsoup.parse(StringEscapeUtils.unescapeHtml4(body)).text();
//        Analyzer analyzer = new StandardAnalyzer(CharArraySet.EMPTY_SET);
        Analyzer analyzer = new StandardAnalyzer();
        try {
            TokenStream tokenStream = analyzer.tokenStream("body", text);
            CharTermAttribute cattr = tokenStream.addAttribute(CharTermAttribute.class);
            tokenStream.reset();
            while(tokenStream.incrementToken()) {
                String token = cattr.toString();
                if(isEnglish(token)) {
                    sentence.add(token);
                } else {
//                    System.out.println(token);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return join(sentence.toArray());
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
        String inputPath = "/Users/zhoutsby/Downloads/english.stackexchange.com/Posts.xml";
        String outputPath = "/Users/zhoutsby/Downloads/english.stackexchange.com/result2/features";
        LDADataParser parser = new LDADataParser(inputPath, outputPath);
        parser.parse();
        parser.dumps();
        parser.dumpIDF();
    }
}
