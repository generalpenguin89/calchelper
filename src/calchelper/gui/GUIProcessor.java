/**
 * GUIProcessor.java
 * 
 * 
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
	
	public void quit()
	{
		int response = JOptionPane.showConfirmDialog( _theGUI,
					"Are you sure you want to exit?" );
		if ( response == 0 )
		{
			System.exit( 0 );
		}
	}
	
	public void cut( JTextField field )
	{
	  field.cut();
	}
	
	public void copy(JTextField field)
	{
	   field.copy();
	}
	
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
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      catch ( IOException e )
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
	}
	
	public void showLaTeX()
	{
	}
	
	public void showInfo()
	{
	   JOptionPane.showMessageDialog( _theGUI, "Copyrighted under the GNU opensource" +
	   		" \nlicense agreement. This product may be used or edited by anyone " +
	   		"\nas long as proper citation and coding conventions are followed." );
	}
}
