/** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** **
 * CalcGUI.java -- Graphical User Interface designed for testing and   *
 * 
 * 
 * 
 * 
 */
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class CalcGUI extends JPanel
{
  //---------------- instance variables ---------------------------
  private Container       _parent;
  private ExpressionTree   _tree;
  private TreeFactory      _treeFactory;
  private JTextField      _textField;
  private  GUIProcessor    _guiProcessor;
  //------------------- constructor -------------------------------
  /**
   * Container parent of the control panel is passed as an argument
   * along with the application object.
   */
  public CalcGUI( Container parent ) 
  {
    super ( new BorderLayout() );
    _guiProcessor = new GUIProcessor( this );
    // for using the jframe for whatever reason
    _parent  = parent;
    
    //create the menu
    JPanel menuPanel = new JPanel();
    menuPanel.add( makeMenu() );
    
    //create Buttons in the North
    JPanel    buttonPanel = new JPanel();
    buttonPanel.add( makeButtonMenu() );

    JPanel center = new JPanel( new BorderLayout() );
    JLabel enter = new JLabel( "                    "
    + "           Enter an equation: " );
    center.add( enter, BorderLayout.NORTH );
    JPanel centerOfCenter = new JPanel( new FlowLayout() );
    center.add( centerOfCenter, BorderLayout.CENTER );
    _textField = new JTextField(20);
    centerOfCenter.add( _textField );
    JButton browse = new JButton("Browse .." );
    browse.addActionListener( new ButtonListener( 3 ) );
    centerOfCenter.add( browse );
    
    // adds everything to the GUI
    this.add( buttonPanel, BorderLayout.SOUTH );
    this.add( center, BorderLayout.CENTER );
    this.add( menuPanel, BorderLayout.NORTH );

  }

  //-------------------- makeMenu() ------------------------------------
  private Component makeMenu()
  {
    JMenuBar bar = new JMenuBar();

    JMenu file = new JMenu("File");
      JMenuItem exit = new JMenuItem("Exit");
      exit.addActionListener( new JMenuItemListener( 'q' ) );
      file.add( exit );
    JMenu edit = new JMenu("Edit");
      JMenuItem cut = new JMenuItem( "Cut" );
      cut.addActionListener( new JMenuItemListener( 'x' ) );
      JMenuItem copy = new JMenuItem( "Copy" );
      copy.addActionListener( new JMenuItemListener( 'c' ) );
      JMenuItem paste = new JMenuItem( "Paste" );
      paste.addActionListener( new JMenuItemListener( 'v' ) );
      edit.add( cut );
      edit.add( copy );
      edit.add( paste );
    JMenu tools = new JMenu("Tools");
      JMenuItem print = new JMenuItem( "Print Tree" );
      print.addActionListener( new JMenuItemListener( 'p' ) );
      JMenuItem show = new JMenuItem( "Show Tree" );
      show.addActionListener( new JMenuItemListener( 's' ) );
      tools.add( print );
      tools.add( show );
    JMenu help = new JMenu("Help");
      JMenuItem latex = new JMenuItem( "Show LaTeX commands" );
      latex.addActionListener( new JMenuItemListener( 'l' ) );
      JMenuItem info = new JMenuItem( "Show Product Info" );
      info.addActionListener( new JMenuItemListener( 'i' ) );
      help.add( latex );
      help.add( info );
    
    bar.add(file);
    bar.add(edit);
    bar.add(tools);
    bar.add(help);

    return bar;
  }
  
   //------------------- makeButtonMenu --------------------------------
   private Component makeButtonMenu()
   {
      // JPanel defaults to FlowLayout
      String[] labels = { "Derive", "Integrate", "Print" };

      JPanel bMenu = new JPanel( new FlowLayout( )); 
      JButton button;
      for ( int i = 0; i < labels.length; i++ )
      {
         button = new JButton( labels[ i ] );
         button.setFont( getFont().deriveFont( 11.0f ));
         bMenu.add( button );
         button.addActionListener( new ButtonListener( i ));
      }      
      return bMenu;
   }

   // ButtonListener inner class
   /**
    * ButtonListener handles all button events and passes them along
    * to methods of the Processor class.
    */
   public class ButtonListener implements ActionListener
   {
      int _buttonId;
      public ButtonListener( int buttonId )
      {
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
                }
                catch ( ExpressionException e )
                {
                }
                try
                {
                  _tree.differentiate();
                }
                catch( NullPointerException e )
                {
                  System.err.println("Invalid entry.");
                }
                break;
             case 1:
                String toIntegrate = "";
                try
                {
                  toIntegrate = _textField.getText();
                  _treeFactory = new TreeFactory( toIntegrate );
                  _tree = _treeFactory.buildTree();
                }
                catch (NullPointerException e )
                {
                }
                catch ( ExpressionException e )
                {
                }
                try
                {
                  _tree.integrate();
                }
                catch( NullPointerException e )
                {
                  System.err.println("Invalid entry.");
                }
                break;
             case 2:
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
     
     //---------constructor--------------------
     public JMenuItemListener( char i )
     {
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
             _guiProcessor.copy();
            break;
          case 'x':
             _guiProcessor.cut();
            break;
          case 'v':
             _guiProcessor.paste();
            break;
          case 'p':
            // expressionTree.printTree();
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