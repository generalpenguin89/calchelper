/**
 * GUIProcessor.java
 * 
 * 
 */

import javax.swing.*;

public class GUIProcessor
{
  //-------------instance variables--------------------
  private CalcGUI _theGUI;
  
  public GUIProcessor( CalcGUI theGUI )
  {
    _theGUI = theGUI;
  }
  public void quit()
  {
    JOptionPane areYouSure = new JOptionPane("Confirmation:");
    int response = areYouSure.showConfirmDialog( _theGUI,
                            "Are you sure you want to exit?");
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