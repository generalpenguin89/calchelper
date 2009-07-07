/**
 * GUIProcessor.java
 * 
 * 
 */

package calchelper.gui;

import javax.swing.JOptionPane;

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
	
	public void cut()
	{
	}
	
	public void copy()
	{
	}
	
	public void paste()
	{
	}
	
	public void showLaTeX()
	{
	}
	
	public void showInfo()
	{
	}
}
