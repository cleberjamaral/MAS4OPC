package resources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Replacer
    {
     public static void replace(String srca, String dsta, String srcb, String dstb)
         {
         try
             {
             File file = new File("planner_modelo.asl");
             BufferedReader reader = new BufferedReader(new FileReader(file));
             String line = "", oldtext = "";
             while((line = reader.readLine()) != null)
                 {
                 oldtext += line + "\r\n";
             }
             reader.close();
             String newtext = oldtext.replaceAll(srca, dsta);
             newtext = newtext.replaceAll(srcb, dstb);
            
            
             FileWriter writer = new FileWriter("..//mas4ssp-demo-defesa//src//asl//planner.asl");
             writer.write(newtext);writer.close();
         }
         catch (IOException ioe)
             {
             ioe.printStackTrace();
         }
     }
}