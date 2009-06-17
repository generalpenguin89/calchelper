/**
 * ETreeApp
 *
 * Provides a tree application for displaying expression trees from infix
 * string expressions.
 *
 * @author Patrick MacArthur, for CS416 Programming Assignment #9
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class ETreeApp
{
   public static final boolean DEBUG = false;

   /**
    * Creates objects necessary to create expression trees.
    */
   public ETreeApp( String filename ) throws FileNotFoundException
   {
      boolean batch = ( filename != null );

      File file = null;
      
      if ( batch )
      {
         file = new File( filename );
      }
      else
      {
         // We don't have a filename yet
         JFileChooser fileDialog = new JFileChooser();
         if ( fileDialog.showOpenDialog( null ) ==
                  JFileChooser.APPROVE_OPTION )
         {
            file = fileDialog.getSelectedFile();
         }
      }

      if ( file != null )
      {
         try
         {
            Scanner scanner = new Scanner( file );
            while ( scanner.hasNext() )
            {
               String line = scanner.nextLine();
               printTree( line, batch );
            }
         }
         catch ( java.io.IOException e )
         {
            System.err.println( "**** Error opening file: " + e.getMessage() );
         }
      }

      if ( batch )
      {
         System.exit( 0 );
      }

      String input = JOptionPane.showInputDialog( null,
                        "Enter an expression." );
      while ( input != null && input.length() != 0 )
      {
         printTree( input, batch );
         input = JOptionPane.showInputDialog( null,
                        "Enter an expression." );
      }
   }

   private static void printTree( String input, boolean batch )
   {
      if ( DEBUG )
      {
         System.out.println( "Input: " + input );
         System.out.println( "Tree:" );
      }

      try
      {
         TreeFactory builder = new TreeFactory( input );
         ExpressionTree tree = builder.buildTree();
         tree.simplify();
         String output = tree.toString();

         if ( batch )
         {
            System.out.println( output );
         }
         else
         {
            JOptionPane.showMessageDialog( null, output );
         }
      }
      catch ( ExpressionException e )
      {
         if ( batch )
         {
            System.err.println( "**** Error: " + e );
            System.err.println();
         }
         else
         {
            JOptionPane.showMessageDialog( null,
                                           "**** Error: " + e,
                                           "ExpressionTree Builder",
                                           JOptionPane.ERROR_MESSAGE );
         }
      }
   }

   public static String getArg( String[] args, int index )
   {
      try
      {
         return args[ index ];
      }
      catch ( IndexOutOfBoundsException e )
      {
         return null;
      }
   }

   public static void main( String[] args ) throws FileNotFoundException
   {
      ETreeApp app = new ETreeApp( getArg( args, 0 ) );
   }
}
