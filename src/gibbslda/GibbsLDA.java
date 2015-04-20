/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gibbslda;

import gibbslda.Documents.Document;
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
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        
        String documentsPath=args[0];
        String modelPath=args[1];
        int iter=Integer.parseInt(args[2]);
        int topicNum=Integer.parseInt(args[3]);
        int saveAtiter=Integer.parseInt(args[4]);
        double alpha=Double.parseDouble(args[5]);
        
        System.out.println("Documents Path is "+documentsPath);
        System.out.println("Model Path is "+modelPath);
        System.out.println("Iteration # is "+ iter);
        System.out.println("Topic num is "+topicNum);
        System.out.println("Model will be saved at iter # "+saveAtiter);
        
        java.util.Date date= new java.util.Date();
        Documents docs = new Documents();
        docs.readTrainingDocuments(documentsPath);
        
        //System.out.println(new Timestamp(date.getTime()));
        System.out.println("Loading Modeling Parameters");
        LDAModel ldaModel = new LDAModel(alpha, 0.5, iter, topicNum);
        
        System.out.print(new Timestamp(date.getTime()));
        System.out.println(" Initializing the Model");
        ldaModel.init(docs);
        
        System.out.print(new Timestamp(date.getTime()));
        System.out.println(" Start Training Model");
        ldaModel.trainingModel(docs,saveAtiter,modelPath); 
        //ldaModel.displayLDA(docs, modelPath,10,0);
        
    }
    
}
