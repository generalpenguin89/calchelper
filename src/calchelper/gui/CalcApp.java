/*
 * CalcApp.java
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

import javax.swing.JFrame;

/**
 * 
 * 
 * @author Ben Decato
 * @author Patrick MacArthur
 */
public class CalcApp
{
   // ----------------------- version stuff ---------------------------
   public final static String VERSION = "svn-trunk";
   public final static String APP_NAME = "CalcHelper";
   
   // ------------------ main -----------------------------------------
   public static void main( String[] args )
   {
      JFrame frame = new JFrame( APP_NAME + " " + VERSION );
      frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
      CalcGUI _calcGUI = new CalcGUI( frame );
      frame.add( _calcGUI );
      frame.pack();
      frame.setVisible( true );
   }
}
