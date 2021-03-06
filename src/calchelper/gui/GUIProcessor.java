/*
 * GUIProcessor.java
 * 
 * This file is part of CalcHelper.
 * 
 * CalcHelper is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * CalcHelper is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with CalcHelper.  If not, see <http://www.gnu.org/licenses/>.
 */

package calchelper.gui;

import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 * Provides major implementation for most menu functions including 
 * cut/copy/paste, show and print tree, and help commands/program info.
 * 
 * @author Ben Decato
 * @author Patrick MacArthur
 */
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
	   StringBuilder copyleftNotice = new StringBuilder();
	   copyleftNotice.append( 
"This program is free software: you can redistribute it and/or modify\n" );
      copyleftNotice.append(
"it under the terms of the GNU General Public License as published by\n" );
      copyleftNotice.append(
"the Free Software Foundation, either version 3 of the License, or\n" );
      copyleftNotice.append(
"(at your option) any later version.\n\n" );
      copyleftNotice.append(
"This program is distributed in the hope that it will be useful,\n" );
      copyleftNotice.append(
"but WITHOUT ANY WARRANTY; without even the implied warranty of\n" );
      copyleftNotice.append(
"MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\n" );
      copyleftNotice.append(
"GNU General Public License for more details.\n\n" );
      copyleftNotice.append(
"You should have received a copy of the GNU General Public License\n" );
      copyleftNotice.append(
"along with this program.  If not, see <http://www.gnu.org/licenses/>.\n\n" );
      
      
      ArrayList<String> developerList = new ArrayList<String>();
      developerList.add( "Patrick MacArthur" );
      developerList.add( "Ben Decato" );
      developerList.add( "Jake Schwartz" );
      developerList.add( "Will Rideout" );
      
      StringBuilder developers = new StringBuilder();
      developers.append( "CalcHelper Developers:" );
      for( String developer : developerList )
      {
         developers.append( "\n     " ).append( developer );
      }

	   JOptionPane.showMessageDialog( _theGUI,                      //parent
	            copyleftNotice.toString() + developers.toString(),  //message
	            "About " + CalcApp.APP_NAME + " " + CalcApp.VERSION, //title
	            JOptionPane.INFORMATION_MESSAGE );                  //type
	}
}
