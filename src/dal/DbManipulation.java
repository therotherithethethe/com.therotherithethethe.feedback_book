package dal;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DbManipulation {
    public static String dbToString(File file) {
        StringBuilder text = new StringBuilder();

        try(FileReader reader = new FileReader(file))
        {
            int c;
            while((c=reader.read())!=-1){

                text.append((char)c);
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        return text.toString();
    }

    public static void writeFeedBackToDb(String name, String password, String feedbackTxt) {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("db.txt", true)))
        {
            bw.append(name).append(password).append(feedbackTxt);
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }

}
