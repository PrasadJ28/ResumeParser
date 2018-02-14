package ZiyadBhombal;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import mdlaf.*;
import javax.swing.*;
import javax.swing.text.Document;
import java.awt.*;
import java.io.FileNotFoundException;

/**
 * Hello world!
 *
 */
public class App 
{
    static PdfWriter writer;
    public static void main( String[] args )
    {
        try{
            UIManager.setLookAndFeel(new MaterialLookAndFeel());
        }
        catch (UnsupportedLookAndFeelException e){
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Test Window");
        frame.setMinimumSize(new Dimension(600,400));

        JMenuBar mBar = new JMenuBar();

        JMenu file = new JMenu("File");
        JMenu close = new JMenu("Close");

        JMenuItem import_file = new JMenuItem("Import file");
        JMenuItem export_file = new JMenuItem("Export file");

        JMenuItem exit = new JMenuItem("Exit");

        file.add(import_file);
        file.add(export_file);

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
}
