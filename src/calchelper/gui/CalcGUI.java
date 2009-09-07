/*
 * CalcGUI.java
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

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import calchelper.tree.ExpressionException;
import calchelper.tree.ExpressionTree;
import calchelper.tree.TreeFactory;

/**
 * Graphical User Interface designed for testing and using our 
 * derivative/integral solver.
 * 
 * @author Ben Decato
 * @author Patrick MacArthur
 */
public class CalcGUI extends JPanel
{
  /**
    * 
    */
   private static final long serialVersionUID = 1L;
//---------------- instance variables ---------------------------
  private JFrame       _parent;
  private ExpressionTree   _tree;
  private TreeFactory      _treeFactory;
  private JTextField      _textField;
  private  GUIProcessor    _guiProcessor;
  //------------------- constructor -------------------------------
  /**
   * Container parent of the control panel is passed as an argument
   * along with the application object.
   */
  public CalcGUI( JFrame parent ) 
  {
    super ( new BorderLayout() );
    _guiProcessor = new GUIProcessor( this );
    // for using the jframe for whatever reason
    set_parent( parent );
    
    makeMenu();
    
    //create Buttons in the North
    JPanel    buttonPanel = new JPanel();
    buttonPanel.add( makeButtonMenu() );

    JPanel center = new JPanel( new BorderLayout() );
    JLabel enter = new JLabel( "         "
    + "Enter an equation: " );
    center.add( enter, BorderLayout.CENTER );
    JPanel centerOfCenter = new JPanel( new FlowLayout() );
    center.add( centerOfCenter, BorderLayout.SOUTH );
    _textField = new JTextField(20);
    centerOfCenter.add( _textField );
    JButton browse = new JButton("Browse .." );
    browse.addActionListener( new ButtonListener( 3, this ) );
    //centerOfCenter.add( browse );
    
    // adds everything to the GUI
    this.add( buttonPanel, BorderLayout.SOUTH );
    this.add( center, BorderLayout.CENTER );

  }

  //-------------------- makeMenu() ------------------------------------
  private Component makeMenu( )
  {
    JMenuBar bar = new JMenuBar();
    
    JMenu file = new JMenu("File");
      JMenuItem exit = new JMenuItem("Exit");
      exit.addActionListener( new JMenuItemListener( 'q', this ) );
      file.add( exit );
    JMenu edit = new JMenu("Edit");
      JMenuItem cut = new JMenuItem( "Cut" );
      cut.addActionListener( new JMenuItemListener( 'x', this ) );
      JMenuItem copy = new JMenuItem( "Copy" );
      copy.addActionListener( new JMenuItemListener( 'c', this ) );
      JMenuItem paste = new JMenuItem( "Paste" );
      paste.addActionListener( new JMenuItemListener( 'v', this ) );
      edit.add( cut );
      edit.add( copy );
      edit.add( paste );
   // JMenu tools = new JMenu("Tools");
   //   JMenuItem print = new JMenuItem( "Print Tree" );
   //   print.addActionListener( new JMenuItemListener( 'p', this ) );
     // JMenuItem show = new JMenuItem( "Show Tree" );
     // show.addActionListener( new JMenuItemListener( 's', this ) );
   //   tools.add( print );
     // tools.add( show );
    JMenu help = new JMenu("Help");
      JMenuItem latex = new JMenuItem( "Show LaTeX commands" );
      latex.addActionListener( new JMenuItemListener( 'l', this ) );
      JMenuItem info = new JMenuItem( "Show Product Info" );
      info.addActionListener( new JMenuItemListener( 'i', this ) );
      help.add( latex );
      help.add( info );
    
    bar.add(file);
    bar.add(edit);
   // bar.add(tools);
    bar.add(help);
    _parent.setJMenuBar( bar );
    return bar;
  }
   //------------------- printTree -------------------------------------
   private void printTree()
   {
     String eq = _textField.getText();
     _treeFactory = new TreeFactory( eq );
     try
     {
      _tree = _treeFactory.buildTree();
      String tree = _tree.toString();
      System.out.println( tree );
     }
     catch ( ExpressionException e )
     {
        String message = "Invalid equation.";
        JOptionPane.showMessageDialog( _parent, message,
                 CalcApp.APP_NAME + " " + CalcApp.VERSION, 2 );
     }
   }
   //------------------- makeButtonMenu --------------------------------
   private Component makeButtonMenu()
   {
      // JPanel defaults to FlowLayout
      String[] labels = { "Derive", "Integrate" };

      JPanel bMenu = new JPanel( new FlowLayout( )); 
      JButton button;
      for ( int i = 0; i < labels.length; i++ )
      {
         button = new JButton( labels[ i ] );
         button.setFont( getFont().deriveFont( 11.0f ));
         bMenu.add( button );
         button.addActionListener( new ButtonListener( i, this ));
      }      
      return bMenu;
   }

   /**
    * @param _parent the _parent to set
    */
   public void set_parent( JFrame _parent )
   {
      this._parent = _parent;
   }

   /**
    * @return the _parent
    */
   public Container get_parent()
   {
      return _parent;
   }

   // ButtonListener inner class
   /**
    * ButtonListener handles all button events and passes them along
    * to methods of the Processor class.
    */
   public class ButtonListener implements ActionListener
   {
      int _buttonId;
      CalcGUI _parent;
      public ButtonListener( int buttonId, CalcGUI parent )
      {
         _parent = parent;
         _buttonId = buttonId;
      }
      public void actionPerformed( ActionEvent ev )
      {
          switch ( _buttonId )
          {
             case 0:
                String toDerive = "";
                try
                {
                  toDerive = _textField.getText();
                  _treeFactory = new TreeFactory( toDerive );
                  _tree = _treeFactory.buildTree();
                  ExpressionTree rezult = _tree.derive();
                  if ( rezult != null )
                  {
                     JOptionPane.showMessageDialog( _parent, rezult.toString(),
                              CalcApp.APP_NAME + " " + CalcApp.VERSION, 1);
                  }
                }
                catch ( ExpressionException e )
                {
                   String message = "Invalid equation: " + e.getMessage();
                   JOptionPane.showMessageDialog( _parent, message,
                            CalcApp.APP_NAME + " " + CalcApp.VERSION, 2 );
                }
                catch ( UnsupportedOperationException e )
                {
                   String message = "Unsupported operation: " + e.getMessage();
                   JOptionPane.showMessageDialog( _parent, message,
                            CalcApp.APP_NAME + " " + CalcApp.VERSION, 2 );
                }
                  
                break;
             case 1:
                String toIntegrate = "";
                try
                {
                  toIntegrate = _textField.getText();
                  _treeFactory = new TreeFactory( toIntegrate );
                  _tree = _treeFactory.buildTree();
                  ExpressionTree result = _tree.integrate();
                  if ( result != null )
                  {
                     JOptionPane.showMessageDialog( _parent, result.toString(),
                              CalcApp.APP_NAME + " " + CalcApp.VERSION, 1);
                  }
                }
                catch ( ExpressionException e )
                {
                   String message = "Invalid equation: " + e.getMessage();
                   JOptionPane.showMessageDialog( _parent, message,
                            CalcApp.APP_NAME + " " + CalcApp.VERSION, 2 );
                }
                catch ( UnsupportedOperationException e )
                {
                   String message = "Unsupported operation: " + e.getMessage();
                   JOptionPane.showMessageDialog( _parent, message,
                            CalcApp.APP_NAME + " " + CalcApp.VERSION, 2 );
                }
                  
                break;
             case 2:
                _parent.printTree();
                break;
             case 3:
                System.out.println("Browsing");
                break;
          }
      }
   }
   // JMenuItemListener inner class
   /**
    * JMenuItemListener handles all JMenuItem events and passes them along
    * to methods of the GUIProcessor class.
    */
   public class JMenuItemListener implements ActionListener
   {
     //---------instance variables-------------
     char _char;
     CalcGUI _theGUI;
     
     //---------constructor--------------------
     public JMenuItemListener( char i, CalcGUI theGUI )
     {
        _theGUI = theGUI;
       _char = i;
     }
     
     //---------actionPerformed-------------------
     public void actionPerformed( ActionEvent ev )
     {
        switch ( _char )
        {
          case 'q':
             _guiProcessor.quit();
            break;
          case 'c':
             _guiProcessor.copy( _textField );
            break;
          case 'x':
             _guiProcessor.cut( _textField );
            break;
          case 'v':
             _guiProcessor.paste( _textField );
            break;
          case 'p':
             _theGUI.printTree();
            break;
          case 's':
            // expressionTree.showTree();
            break;
          case 'l':
             _guiProcessor.showLaTeX();
            break;
          case 'i':
             _guiProcessor.showInfo();
            break;
        }
     }
   }
}
