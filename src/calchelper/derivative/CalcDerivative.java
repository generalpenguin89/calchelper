/**
 * CalcDerivative.java
 * 
 * @author: William Rideout
 * 6/19/09
 */

package calchelper.derivative;
import java.util.*;
import java.io.*;
import calchelper.tree.*;

public class CalcDerivative
{
  //========================== Class Variables ================================= 
  
  //========================== Instance Variables ==============================
  String ex;  //the expressionreturn, result of user input
  ExpressionTree tree; //this tree will be created using ex
  TreeFactory tFact; //the builder of the tree
  
  //========================== Constructor =====================================
  public CalcDerivative( String ex )
  {
    //instantiate the factory 
    tFact = new TreeFactory( ex );
    
    try
    {
      //build a new tree based on the expression
      tree = tFact.buildTree( );
      tree.simplify( );
    }
    catch( ExpressionException ee )
    {
      System.err.println( "ERROR: Expression Exception" );
    }
    System.out.println( tree );//test
  }
    
  //========================== Methods =========================================
  //-------------------------- derive( ) ---------------------------------------
  /**
   * Finds the derivative of the tree.  This is a recursive method, that does 
   * two things:
   *      1: FINDS THE LEAVES OF THE TREE
   *      2: TRAVELS BACK UP THE TREE, FINDING THE DERIVATIVE OF EACH NODE.
   * 
   * For example, given the following tree:
   *      +
   *        3x
   *        /
   *          6
   *          x
   * The process of finding the derivative would be as follows:
   *      1: RECURSIVLY MOVE THE THE LEAF NODE(S)... in this case, 6 and 'x'.
   *      2: FIND THE DERIVATIVE OF THE LEAVES... these should be saved along 
   *         with the "originals."
   *      3: COMBINE THE TWO LEAVES AS FITS THE OPERATOR ABOVE THEM... in this 
   *         case it would employ the quotient rule AFTER find the derivative of
   *         3x.  The method should look for "nomials" of some sort to derive 
   *         before combining at EACH LEVEL.
   */
  public void derive( )
  {
    //does nothing as of yet 
  }

  //========================== Main Method =====================================
  public static void main( String[ ] args )
  {
    //CalcDerivative app = new CalcDerivative( args[ 0 ] );
        
    //test code... removable
    System.out.println( "Enter an infix expression." );
    Scanner terminal = new Scanner( System.in );
    String ex = terminal.nextLine( );
    CalcDerivative app = new CalcDerivative( ex );
  }
}
