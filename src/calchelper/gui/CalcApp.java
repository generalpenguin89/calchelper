/*
 * CalcApp.java
 * 
 * Copyright 2009 Ben Decato, Patrick MacArthur, William Rideout, and
 * Jake Schwartz
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

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 * Main application class for a simple text editor
 * with new, open, save, word count, font, and size options.
 * 
 * Possible future editions will include margin changes, print
 * functionality, and anything else I can come up with.
 * 
 * @author Ben Decato
 * @author Patrick MacArthur
 */
public class CalcApp extends JFrame
{
	private static final long serialVersionUID = 1L;
	// ---------------------- instance variables ----------------------
	private CalcGUI _calcGUI;
	public String _fileName = "";
	public static CalcApp theApp;
	
	//-----------------------version stuff ---------------------------
   public final static String VERSION = "svn-trunk";
   public final static String APP_NAME = "CalcHelper";
	
	// --------------------------- constructor -----------------------
	public CalcApp( String title, String[] args )
	{
		super( title );
		JFrame.setDefaultLookAndFeelDecorated( true );
		this.setBackground( Color.WHITE );
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		_calcGUI = new CalcGUI( this );
		this.add( _calcGUI );
		this.setSize( new Dimension( 300, 170 ) );
		this.setVisible( true );
	}
	
	// ------------------ main ------------------------------------------
	public static void main( String[] args )
	{
		theApp = new CalcApp( APP_NAME + " " + VERSION, args );
	}
}
