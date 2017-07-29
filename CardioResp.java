import javax.swing.*;
import java.util.*;
import java.io.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.text.*;

public class CardioResp{
   public static void main(String[] args) throws FileNotFoundException, IOException, NumberFormatException, ParseException, ArrayIndexOutOfBoundsException, 
                                                 ClassNotFoundException{
      
      String fileLocationRespiration = JOptionPane.showInputDialog("Please enter the file path to your respiration file.\nRight click your file and"
                                                                   +" select properties, copy and paste the file path in the form of C:\\Path\\NameOfYourFile.txt"
                                                                   +" \nEx. C:\\User\\Andrew\\Desktop\\Respiration.txt");
      String fileLocationCardiac = JOptionPane.showInputDialog("Please enter the file path to your cardiac file.\nRight click your file and select properties, "
                                                               +"copy and paste the file path in the form of C:\\Path\\NameOfYourFile.txt \nEx. C:\\User\\Andrew\\Desktop\\Cardiac.txt");
      int countLinesR = countResp(fileLocationRespiration); 
      List<Date> respirationList = processResp(countLinesR, fileLocationRespiration);
      int countLinesC = countCard(fileLocationCardiac);
      List<Date> cardiacList = processCard(countLinesC, fileLocationCardiac);
      compareTime(cardiacList, respirationList);
     
   }

         
    public static int countResp(String fileLocationRespiration)throws FileNotFoundException, IOException, NumberFormatException, ParseException, 
                                                                         ArrayIndexOutOfBoundsException, ClassNotFoundException{
      FileReader fileReaderRespiration = new FileReader(fileLocationRespiration);
      BufferedReader bufferedReaderR = new BufferedReader(fileReaderRespiration);
      String lineR = bufferedReaderR.readLine();
      int countR = 0;
      while((lineR = bufferedReaderR.readLine()) != null){
         countR++;
      }
      return countR;
    }
         
         
    public static int countCard(String fileLocationCardiac)throws FileNotFoundException, IOException, NumberFormatException, ParseException,
                                                                       ArrayIndexOutOfBoundsException, ClassNotFoundException{
      FileReader fileReaderCardiac = new FileReader(fileLocationCardiac);
      BufferedReader bufferedReaderC = new BufferedReader(fileReaderCardiac);
      String lineC = bufferedReaderC.readLine();
      int countC = 0;
      while((lineC = bufferedReaderC.readLine()) != null){
         countC++;
      }
      return countC;
    }
         
         
    public static List<Date> processResp(int countLinesR, String fileLocationRespiration)throws FileNotFoundException, IOException, NumberFormatException, 
                                                                                   ParseException, ArrayIndexOutOfBoundsException, ClassNotFoundException{
      FileReader fileReaderRespiration = new FileReader(fileLocationRespiration);
      BufferedReader bufferedReaderR = new BufferedReader(fileReaderRespiration);
      SimpleDateFormat df = new SimpleDateFormat("hh:mm.s");
      String lineR;
      List<Date> result = new ArrayList<Date>(countLinesR);
      while((lineR = bufferedReaderR.readLine()) != null){
         String[] rawDataR = lineR.split(",");
         Date RD1=df.parse(rawDataR[6]);
         result.add(RD1); 
      }
      return result;
    }
    
         
    public static List<Date> processCard(int countLinesC, String fileLocationCardiac)throws FileNotFoundException, IOException, NumberFormatException, 
                                                                                    ParseException, ArrayIndexOutOfBoundsException, ClassNotFoundException{
      FileReader fileReaderCardiac = new FileReader(fileLocationCardiac);
      BufferedReader bufferedReaderC = new BufferedReader(fileReaderCardiac);
      SimpleDateFormat df = new SimpleDateFormat("hh:mm.s");
      String lineC;
      List<Date> result = new ArrayList<Date>(countLinesC);
      while((lineC = bufferedReaderC.readLine()) != null){
         String[] rawDataC = lineC.split(",");
         Date RD1=df.parse(rawDataC[1]);
         result.add(RD1); 
      }
      return result;         
    }
         
         
    public static void compareTime(List<Date> cardiacList, List<Date> respirationList) throws FileNotFoundException, IOException, NumberFormatException, 
                                                                                 ParseException, ArrayIndexOutOfBoundsException, ClassNotFoundException{
      SimpleDateFormat cpuFormat = new SimpleDateFormat("hh:mm:ss");
      SimpleDateFormat myFormat = new SimpleDateFormat("hh:mm.s");
      //System.out.println("Period,    Respiration Peak,    Cardiac#,    Cardiac Time,    Resp to Beat,");
      System.out.format("%s %30s %20s %20s %20s", "Period", "Respiration Peak", "Cardiac#", "Cardiac Time", "Resp. to Beat\n");
      for (int i = 0; i < respirationList.size(); i++) {
         Date RD1 = respirationList.get(i);
         String time = RD1.toString();
         String [] timeparse = time.split(" ");
         String formatTime = myFormat.format(cpuFormat.parse(timeparse[3]));
         //System.out.println(i + ",         " + formatTime + (","));
         System.out.printf("%-20d %-20s\n", i, formatTime);
         int periodCount = i;
         String[] units = formatTime.split(":"); 
         float minutes = Float.parseFloat(units[0]); 
         float seconds = Float.parseFloat(units[1]); 
         float duration = 60 * minutes + seconds; 
         Date RD2 = respirationList.get(i+1);
         int cardiacCount=0;
            for (int b = 0; b < cardiacList.size(); b++) {
               Date CD1 = cardiacList.get(b);
               String timeC = CD1.toString();
               String [] timeParseC = timeC.split(" ");
               String formatTime2 = myFormat.format(cpuFormat.parse(timeParseC[3]));
               if(CD1.before(RD2)&& CD1.after(RD1) || CD1.equals(RD2)){
                  cardiacCount++;     
                  String[] units2 = formatTime2.split(":"); 
                  float minutes2 = Float.parseFloat(units2[0]);
                  float seconds2 = Float.parseFloat(units2[1]);
                  float duration2 = 60 * minutes2 + seconds2;
                  System.out.format("%d %49d\t\t %10s\t\t %-5f\n", periodCount, cardiacCount, formatTime2, Math.abs(duration-duration2));
               } 
            }
      }
    }
    
}
