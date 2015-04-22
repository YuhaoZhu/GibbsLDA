/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gibbslda;

import gibbslda.Documents.Document;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;

/**
 *
 * @author YuhaoZhu
 */
public class GibbsLDA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException {
        // TODO code application logic here
        if (args.length == 6) {
            String documentsPath = args[0];
            String modelPath = args[1];
            int iter = Integer.parseInt(args[2]);
            int topicNum = Integer.parseInt(args[3]);
            int saveAtiter = Integer.parseInt(args[4]);
            double alpha = Double.parseDouble(args[5]);
            
            
            System.out.println("LDA rebuilt at 201504021-01:14");
            System.out.println("Documents Path is " + documentsPath);
            System.out.println("Model Path is " + modelPath);
            System.out.println("Iteration # is " + iter);
            System.out.println("Topic num is " + topicNum);
            System.out.println("Model will be saved at iter # " + saveAtiter);

            java.util.Date date = new java.util.Date();
            Documents docs = new Documents();
            docs.readTrainingDocuments(documentsPath);

            //System.out.println(new Timestamp(date.getTime()));
            System.out.println("Loading Modeling Parameters");
            LDAModel ldaModel = new LDAModel(alpha, 0.1, iter, topicNum);

            System.out.print(new Timestamp(date.getTime()));
            System.out.println(" Initializing the Model");
            ldaModel.init(docs);

            System.out.print(new Timestamp(date.getTime()));
            System.out.println(" Start Training Model");
            ldaModel.trainingModel(docs, saveAtiter, modelPath);
            //ldaModel.displayLDA(docs, modelPath,10,0);
        }else if (args.length==2){
            java.util.Date date = new java.util.Date();
            String documentPath=args[0];
            String modelPath=args[1];
            //String resultPath=args[2];
            
            Documents docs = new Documents();
            docs.readTrainingDocuments(documentPath);
            
            System.out.print(new Timestamp(date.getTime()));
            System.out.println(" Initializing the Model");
            
            LDAInfer ldaInfer=new LDAInfer(documentPath,modelPath);
            ldaInfer.init();
            ldaInfer.infer(docs);
            
        } else{
            System.out.println("Training Model Usage:");
            System.out.println("Usage: java -jar GibbsLDA.jar documentPath ModelPath iterNum topicNUM saveAtiter alpha");
            System.out.println("Learn a model");
            System.out.println("Usage: java -jar GibbsLDA.jar documentPath ModelPath");
        }

    }

}
