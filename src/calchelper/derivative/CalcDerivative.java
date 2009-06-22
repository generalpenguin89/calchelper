/**
 * CalcDerivative.java
 * 
 * @author: William Rideout
 * 6/19/09
 */

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
