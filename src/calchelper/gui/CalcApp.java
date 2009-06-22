/**
 * CalcApp.java -- Main application class for a simple text editor
 * with new, open, save, word count, font, and size options.
 * 
 * Possible future editions will include margin changes, print
 * functionality, and anything else I can come up with.
 * 
 * by Ben Decato
 * University of New Hampshire CS Student
 */
package calchelper.gui; 
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.border.*;

public class CalcApp extends JFrame
{
   //---------------------- instance variables ----------------------
   private CalcGUI _calcGUI;
   public String _fileName = "";
   public static CalcApp theApp;
   
   //--------------------------- constructor -----------------------
   public CalcApp( String title, String[] args )     
   {
      super( title );
      this.setDefaultLookAndFeelDecorated( true );
      this.setBackground( Color.WHITE );
      this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
      
      _calcGUI = new CalcGUI( this );
      this.add( _calcGUI );
      
      this.setSize( new Dimension( 300, 200 ));
      this.setVisible( true );
   }

   //------------------ main ------------------------------------------   
   public static void main( String [ ] args ) 
   {
     theApp = new CalcApp( "CalcHelper ALPHA", args );
   }
}
