package calchelper.tree;

import static org.junit.Assert.assertEquals;
import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit-based test harness for Polynomial.
 *
 * Written by Patrick McArthur
 * Edited by Ben Decato and William Rideout 
 */

public class DifferentiationTest
{
   // Expected results for each test
   HashMap<Double,Double> expected;
   
   public static AbstractNode getTreeRoot( String infix ) throws ExpressionException
   {
      TreeFactory factory;
      ExpressionTree tree;
      
      factory = new TreeFactory( infix );
      tree = factory.buildTree();
      return tree.getRoot();
   }

   @Before public void setUp()
   {
      expected = new HashMap<Double,Double>();
   }
   
   @Test public void productTest() throws ExpressionException
   {
      // Declare expected results
      AbstractNode expected = getTreeRoot( "2" );
      
      // Get actual results 
      AbstractNode actual = getTreeRoot( "2 * x" ).derive();
      
      assertEquals( expected, actual );
   }
   
   @Test public void quotientTest() throws ExpressionException
   {
      // Declare expected results
      AbstractNode expected = getTreeRoot( "-( 2 / ( x ^ 2 ) )" );
      
      // Get actual results 
      //we need to create a new division node, so that it isn't simplified
      //this means that we can use a simpler example.
      AbstractNode left = getTreeRoot( "5 * x" );
      AbstractNode right = getTreeRoot( "4 + x" );
      
      AbstractNode actual = NodeFactory.createNode( "/", left, right ).derive();
      assertEquals( expected, actual );
   }
   
   //======================== TRIG FUNCTION TESTS =============================
   @Test public void sineTest() throws ExpressionException
   {
      // Declare expected results
      AbstractNode expected = getTreeRoot( "2 * //cos{ 2 * x }" );
      
      // Get actual results 
      AbstractNode actual = getTreeRoot( "//sin{ 2 * x }" );
      assertEquals( expected, actual );
   }
   
   @Test public void cosineTest() throws ExpressionException
   {  
      // Declare expected results
      AbstractNode expected = getTreeRoot( "-1 * ( 2 * //cos{ 2 * x } )" );
      
      // Get actual results 
      AbstractNode actual = getTreeRoot( "//cos{ 2 * x }" );
      assertEquals( expected, actual );
   }
}