package ZiyadBhombal;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import mdlaf.*;
import opennlp.tools.tokenize.SimpleTokenizer;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Hello world!
 *
 */
@SuppressWarnings("ALL")
public class App
{
    static File pdfFile;
    static int RESULT_OK= 1;
    static int RESULT_FAILED = 0;
    static int RESULT_UNDEFINED=13;

    static List<String> Qualifications = Arrays.asList("educational","qualifications","academic","academics");

    static List<String> PersonalDetails = Arrays.asList("personal","details");

    static List<String> Skills = Arrays.asList("languages","programming");

    static List<String> Languages = Arrays.asList("java","python","c","c++","html","css","sql");


    public static void p(String str){
        System.out.println(str);
    }





    public static void main( String[] args ) throws Exception
    {
        EventQueue.invokeAndWait(new Runnable() {
            public void run() {

                final JFrame frame = new JFrame("Test Window");
                frame.setMinimumSize(new Dimension(600,400));

                JMenuBar mBar = new JMenuBar();

                JMenu file = new JMenu("File");
                JMenu close = new JMenu("Close");

                final JMenuItem import_file = new JMenuItem("Import file");
                JMenuItem export_file = new JMenuItem("Export file");

                JMenuItem exit = new JMenuItem("Exit");

                file.add(import_file);
                file.add(export_file);
                import_file.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JFileChooser chooser = new JFileChooser();
                        chooser.setMinimumSize(new Dimension(400,600));
                        FileFilter f = new FileNameExtensionFilter("Pdf and Docx","pdf","docx");
                        chooser.setFileFilter(f);
                        chooser.showDialog(frame,"Import");
                        File importedFile = chooser.getSelectedFile();
                        if(importedFile.getName().contains(".pdf")){
                            int result = createFileDump(importedFile);
                        }
                        else if(importedFile.getName().contains(".docx")){

                        }
                    }
                });

                exit.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));                    }
                });

                close.add(exit);


                mBar.add(file);
                mBar.add(close);

                frame.add(mBar,BorderLayout.PAGE_START);

                //animation
                MaterialUIMovement animate = new MaterialUIMovement( new Color(34,167,240),5,1000/30);

                animate.add(file);
                animate.add(import_file);
                animate.add(export_file);

                animate.add(close);
                animate.add(exit);

                frame.pack();
                frame.setVisible(true);
            }
        });
    }

    private static int createFileDump(File f) {

        try{PdfReader reader = new PdfReader(f.getAbsolutePath());
            BufferedWriter writer = new BufferedWriter(new FileWriter("/home/ziyadbhombal/Documents/temp.txt",false));
            String line;
            int n = reader.getNumberOfPages();
            System.out.println("Started writing to dump");
            for(int i =1 ; i<=n;i++){
                line = PdfTextExtractor.getTextFromPage(reader,i, new SimpleTextExtractionStrategy());
                String[] arr = line.split("\n");
                for(String s : arr){
                    writer.write(s);
                    writer.newLine();
                }
            }
            writer.close();

            System.out.println("Finished writing to dump");
            File file = new File("/home/ziyadbhombal/Documents/temp.txt");

            List<String> techSkills,personalDetails,qualifications;


            techSkills = findTechnicalSkills();
            personalDetails = findPersonalDetails();
            qualifications = findQualifications();
            return RESULT_OK;

        }
        catch (IOException ex){ex.printStackTrace();}


        return RESULT_FAILED;
    }


    public static String[] getTokens(String fileData,boolean Do){
        try {
//            String fileData = new String(Files.readAllBytes(Paths.get("logs/temp.txt")));
            SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
            String[] tokens = tokenizer.tokenize(fileData);
            if(Do){
                for(String sr : tokens){
                    p("Token: "+sr);
                }
            }



            return tokens;


        }
        catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }



    private static List<String> findTechnicalSkills() {
        try{
            String convertedPdfFile = new String(Files.readAllBytes(Paths.get("/home/ziyadbhombal/Documents/temp.txt")));
            String[] stringArrayOfWords = getTokens(convertedPdfFile,false);
//            find starting point for scanning text
            for(String s : stringArrayOfWords){
                p(s);
            }
            int indexOfFirstMatchedKeyword=0;
            for(String eachWord : stringArrayOfWords){
                if(Skills.contains(eachWord.toLowerCase())){
                    break;
                }
                indexOfFirstMatchedKeyword++;
            }

//            find ending point for scanning text
            List<String> listOfExcludedCorpus = new ArrayList<>(PersonalDetails.size()+Qualifications.size());
            listOfExcludedCorpus.addAll(PersonalDetails);
            listOfExcludedCorpus.addAll(Qualifications);
            int endPoint=indexOfFirstMatchedKeyword;
            for(int ix =indexOfFirstMatchedKeyword;ix<stringArrayOfWords.length;ix++){
                if(listOfExcludedCorpus.contains(stringArrayOfWords[ix].toLowerCase())){
                    break;
                }
                endPoint = ix;
            }
            p("Starting point "+indexOfFirstMatchedKeyword);
            p("Ending point "+endPoint);
            p("Size of array "+stringArrayOfWords.length);

            BufferedWriter wr = new BufferedWriter(new FileWriter("logs/skills.txt",false));
            for (int ix =indexOfFirstMatchedKeyword ;ix<=endPoint; ix++){
                wr.write(stringArrayOfWords[ix]);
                wr.write(" ");
            }
            wr.flush();
            wr.close();




            return null;



        }
        catch (IOException ex){
            ex.printStackTrace();
            return null;
        }
    }
    private static List<String> findQualifications() {
        try{
            String convertedPdfFile = new String(Files.readAllBytes(Paths.get("/home/ziyadbhombal/Documents/temp.txt")));
            String[] stringArrayOfWords = getTokens(convertedPdfFile,false);
//            find starting point for scanning text
            for(String s : stringArrayOfWords){
                p(s);
            }
            int indexOfFirstMatchedKeyword=0;
            for(String eachWord : stringArrayOfWords){
                if(Qualifications.contains(eachWord.toLowerCase())){
                    break;
                }
                indexOfFirstMatchedKeyword++;
            }

//            find ending point for scanning text
            List<String> listOfExcludedCorpus = new ArrayList<>(PersonalDetails.size()+Qualifications.size());
            listOfExcludedCorpus.addAll(PersonalDetails);
            listOfExcludedCorpus.addAll(Skills);
            int endPoint=indexOfFirstMatchedKeyword;
            for(int ix =indexOfFirstMatchedKeyword;ix<stringArrayOfWords.length;ix++){
                if(listOfExcludedCorpus.contains(stringArrayOfWords[ix].toLowerCase())){
                    break;
                }
                endPoint = ix;
            }
            p("Starting point "+indexOfFirstMatchedKeyword);
            p("Ending point "+endPoint);
            p("Size of array "+stringArrayOfWords.length);

            BufferedWriter wr = new BufferedWriter(new FileWriter("logs/quals.txt",false));
            for (int ix =indexOfFirstMatchedKeyword ;ix<=endPoint; ix++){
                wr.write(stringArrayOfWords[ix]);
                wr.write(" ");
            }
            wr.flush();
            wr.close();




            return null;



        }
        catch (IOException ex){
            ex.printStackTrace();
            return null;
        }
    }
    private static List<String> findPersonalDetails() {
        try{
            String convertedPdfFile = new String(Files.readAllBytes(Paths.get("/home/ziyadbhombal/Documents/temp.txt")));
            String[] stringArrayOfWords = getTokens(convertedPdfFile,false);
//            find starting point for scanning text
            for(String s : stringArrayOfWords){
                p(s);
            }
            int indexOfFirstMatchedKeyword=0;
            for(String eachWord : stringArrayOfWords){
                if(PersonalDetails.contains(eachWord.toLowerCase())){
                    break;
                }
                indexOfFirstMatchedKeyword++;
            }

//            find ending point for scanning text
            List<String> listOfExcludedCorpus = new ArrayList<>(PersonalDetails.size()+Qualifications.size());
            listOfExcludedCorpus.addAll(Qualifications);
            listOfExcludedCorpus.addAll(Skills);
            int endPoint=indexOfFirstMatchedKeyword;
            for(int ix =indexOfFirstMatchedKeyword;ix<stringArrayOfWords.length;ix++){
                if(listOfExcludedCorpus.contains(stringArrayOfWords[ix].toLowerCase())){
                    break;
                }
                endPoint = ix;
            }
            p("Starting point "+indexOfFirstMatchedKeyword);
            p("Ending point "+endPoint);
            p("Size of array "+stringArrayOfWords.length);

            BufferedWriter wr = new BufferedWriter(new FileWriter("logs/details.txt",false));
            for (int ix =indexOfFirstMatchedKeyword ;ix<=endPoint; ix++){
                wr.write(stringArrayOfWords[ix]);
                wr.write(" ");
            }
            wr.flush();
            wr.close();

            return null;
        }
        catch (IOException ex){
            ex.printStackTrace();
            return null;
        }
    }



}
