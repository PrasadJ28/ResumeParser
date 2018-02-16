package ZiyadBhombal;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.LocationTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import mdlaf.*;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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




    public static void main( String[] args ) throws Exception
    {
        EventQueue.invokeAndWait(new Runnable() {
            public void run() {
//                try{
//                    UIManager.setLookAndFeel(new MaterialLookAndFeel());
//                }
//                catch (UnsupportedLookAndFeelException e){
//                    e.printStackTrace();
//                }

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
//                        doStuffOnText(frame);
//                        JOptionPane.showMessageDialog(frame, "Text parsed successfully!");
                        JFileChooser chooser = new JFileChooser();
                        chooser.setMinimumSize(new Dimension(400,600));
                        FileFilter f = new FileNameExtensionFilter("Pdf and Docx","pdf","docx");
                        chooser.setFileFilter(f);
                        chooser.showDialog(frame,"Import");
                        File importedFile = chooser.getSelectedFile();
                        if(importedFile.getName().contains(".pdf")){
                            int result = processPdfDocument(frame,importedFile);
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

    private static int processPdfDocument(JFrame frame,File f) {

        try{PdfReader reader = new PdfReader(f.getAbsolutePath());
            BufferedWriter writer = new BufferedWriter(new FileWriter("/home/ziyadbhombal/Documents/temp.txt",false));
            String line;
            int n = reader.getNumberOfPages();
            System.out.println("Started writing to dump");
            for(int i =1 ; i<=n;i++){
                line = PdfTextExtractor.getTextFromPage(reader,i, new SimpleTextExtractionStrategy());
                String[] arr = line.split("\n");
                for(String s : arr){
                    writer.write(s+" ");
                    writer.write("eof");
                    writer.newLine();
                }
            }
            writer.close();

            System.out.println("Finished writing to dump");
            File file = new File("/home/ziyadbhombal/Documents/temp.txt");
            System.out.println("Size of dump on disk: "+file.getTotalSpace());
            //start analysing text


            BufferedReader bufferedReader = new BufferedReader(new FileReader("/home/ziyadbhombal/Documents/temp.txt"));

        }
        catch (IOException ex){ex.printStackTrace();}


        return RESULT_FAILED;
    }
}
