/**
 * GUIProcessor.java -- Provides major implementation for
 * most menu functions including cut/copy/paste, show and
 * print tree, and help commands/program info.
 * 
 * Author: Ben Decato
 * Last modified: 8/3/2009
 */

package calchelper.gui;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class GUIProcessor
{
	// -------------instance variables--------------------
	private CalcGUI _theGUI;
	
	public GUIProcessor( CalcGUI theGUI )
	{
		_theGUI = theGUI;
	}
	
	// quit() -- Simple exit method with "Are you sure?" popup
	public void quit()
	{
		int response = JOptionPane.showConfirmDialog( _theGUI,
					"Are you sure you want to exit?" );
		if ( response == 0 )
		{
			System.exit( 0 );
		}
	}
	
	// cut( JTextField ) -- Cut method just calls JTextArea's cut().
	public void cut( JTextField field )
	{
	  field.cut();
	}
	
   // copy( JTextField ) -- Cut method just calls JTextArea's copy().
	public void copy(JTextField field)
	{
	   field.copy();
	}
	
	// paste(JTextField) -- Provides paste functionality for the textfield
	public void paste( JTextField field )
	{
	   Clipboard d = field.getToolkit().getSystemClipboard();
	   String output = "";
      try
      {
        output = ( String ) d.getData( DataFlavor.stringFlavor );
        if ( output != "" )
        {
          if ( field.getSelectedText() != null )
          {
            field.setText( field.getText().
           replace(field.getSelectedText(), output ) );
          }
          else
          {
             String text = field.getText();
             String before = text.substring
             ( 0, field.getCaretPosition() );
             String after = text.substring
             ( field.getCaretPosition(), text.length() );
             field.setText( before + output + after );
          }
        }
      }
      catch ( UnsupportedFlavorException e )
      {
         e.printStackTrace();
      }
      catch ( IOException e )
      {
         e.printStackTrace();
      }
	}
	
	// showLaTeX() -- Syntax guide for CalcHelper via LaTeX command list
	public void showLaTeX()
	{
	   JOptionPane.showMessageDialog( _theGUI,
	                                  "Basic operations:  - * / + \n" +
	                                  "\n Polynomials: PEMDAS priority system using parenthesis" +
	                                  "\n\n Trigonometric symbols:  \\sec{ polynomial operation }" +
	                                  "\n\n Example input: ( -2.0 * \\csc{ 2 * x } ) * ( 1.0 * \\cot{ 2 * x } )", "Syntax Guide", 1);
	}
	
	// showInfo() -- Shows program's licensing and author information.
	public void showInfo()
	{
	   JOptionPane.showMessageDialog( _theGUI, "Visit http://www.gnu.org/licenses/gpl.txt for licensing information." +
	   		                                  "\n   CalcHelper Developers:\n     Patrick MacArthur\n     Ben Decato\n" +
	   		                                  "     Jake Schwartz\n     Will Rideout");
	}
}
