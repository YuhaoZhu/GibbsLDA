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
        java.util.Date date= new java.util.Date();
        Documents docs = new Documents();
        docs.readTrainingDocuments("/Users/YuhaoZhu/LDATraining/");
        
        System.out.println(new Timestamp(date.getTime()));
        System.out.println("Loading Modeling Parameters");
        LDAModel ldaModel = new LDAModel(0.5, 0.5, 100, 10);
        
        System.out.println(new Timestamp(date.getTime()));
        System.out.println("Initializing the Model");
        ldaModel.init(docs);
        
        System.out.println(new Timestamp(date.getTime()));
        System.out.println("Start Training Model");
        ldaModel.trainingModel(docs,80);    
    }
    
}
