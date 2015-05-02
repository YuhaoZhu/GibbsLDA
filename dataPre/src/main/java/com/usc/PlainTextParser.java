package com.usc;

import org.apache.commons.cli.*;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoutsby on 4/28/15.
 */
public class PlainTextParser {

    public List<String> answer;
    public List<String> question;

    public PlainTextParser() {
        answer = new ArrayList<String>();
        question = new ArrayList<String>();
    }

    /**
     * read the data from xml and parse it to plain texts
     */
    public void parse(String inputPath) {
        File input = new File(inputPath);
        if(input.exists() && input.isFile()) {
            try {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(input);

                doc.getDocumentElement().normalize();
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
                            question.add(join(feats.toArray()));
                        } else if(attributes.getNamedItem("PostTypeId").getNodeValue().equals("2")) {
                            // answer
                            String body = attributes.getNamedItem("Body").getNodeValue();
                            List<String> feats = tokenize(body);
                            answer.add(join(feats.toArray()));
                        }
                    }
                }

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

    public void dump(String outputDir) {
        String outQusPath = outputDir + "/Question.txt";
        String outAnsPath = outputDir + "/Answer.txt";
        try {
            FileWriter outQus = new FileWriter(outQusPath);
            FileWriter outAns = new FileWriter(outAnsPath);
            BufferedWriter bwQus = new BufferedWriter(outQus);
            BufferedWriter bwAns = new BufferedWriter(outAns);

            for(String line : question) {
                bwQus.write(line + "\n");
            }

            for(String line: answer) {
                bwAns.write(line + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        Options options = new Options();

        //add t option
        options.addOption("input", true, "input data file");
        options.addOption("output", true, "output dir to store the question and answer");

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            if(cmd.hasOption("input") && cmd.hasOption("output")) {
                String inputPath = cmd.getOptionValue("input");
                String outputDir = cmd.getOptionValue("output");
                PlainTextParser ptparser = new PlainTextParser();
                ptparser.parse(inputPath);
                ptparser.dump(outputDir);
            } else {
                System.err.println("You must specify the input and output to run this program");
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
