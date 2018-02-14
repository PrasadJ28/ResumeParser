package ZiyadBhombal;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.*;
import mdlaf.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Hello world!
 *
 */
@SuppressWarnings("ALL")
public class App
{
    static File pdfFile;
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

                JMenuItem import_file = new JMenuItem("Import file");
                JMenuItem export_file = new JMenuItem("Export file");

                JMenuItem exit = new JMenuItem("Exit");

                file.add(import_file);
                file.add(export_file);
                import_file.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JFileChooser chooser = new JFileChooser();
                        chooser.setMinimumSize(new Dimension(300,300));
                        FileFilter filter = new FileNameExtensionFilter("PDF and DOCX","pdf","docx");
                        chooser.setFileFilter(filter);
                        chooser.showDialog(frame,"Import");
                        pdfFile = chooser.getSelectedFile();
                        String path = pdfFile.getAbsolutePath();
                        System.out.println("Path:"+path);
                        PdfReader reader;
                        try{
                            reader = new PdfReader(path);
                            String s = PdfTextExtractor.getTextFromPage(reader,1);
                            System.out.println(s);
                        }
                        catch (Exception ex){
                            ex.printStackTrace();
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
}
