/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gibbslda;

import gibbslda.Documents.Document;
import java.io.IOException;

/**
 *
 * @author YuhaoZhu
 */
public class GibbsLDA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException  {
        // TODO code application logic here
        Documents docs=new Documents();
        docs.readTrainingDocuments("/Users/YuhaoZhu/LDATraining/");
        for (Document doc:docs.allDocumentsContent){
            for (int i=0;i<doc.mappedDoc.length;i++){
                System.out.println(doc.mappedDoc[i]);
            }
        }
    }
    
}
