package calchelper.tree;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

/**
 * JUnit-based test harness for Polynomial.
 *
 * Written by Patrick McArthur
 * Editted by Jacob Schwartz
 */

public class IntegrationTest
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
   
   //Polynomial test
   @Test public void polynomialTest1() throws ExpressionException
   {
      // Declare expected results
      AbstractNode expected = getTreeRoot( "x^2" );
      
      // Get actual results 
      AbstractNode actual = getTreeRoot( "2 * x ^ 1" ).integrate();
      
      assertEquals( expected, actual );
   }
   
   //Polynomial with x^-1
   @Test public void natLogTest() throws ExpressionException
   {
      // Declare expected results
      AbstractNode expected = getTreeRoot( "x^2 + \\ln{x}" );
      
      // Get actual results 
      AbstractNode actual = getTreeRoot( "( 2 * x ^ 1 ) + ( x^ ( -1 ) )" ).integrate();
      
      assertEquals( expected, actual );
   }
   
   //sine
   /*@Test public void test3() throws ExpressionException
   {
      // Declare expected results
      AbstractNode expected = getTreeRoot( "-1 * \\cos{x}" );
      
      // Get actual results 
      AbstractNode actual = getTreeRoot( "\\sin{x}" ).integrate();
      
      assertEquals( expected, actual ); 
   }
   
   //cos
   @Test public void test4() throws ExpressionException
   {
      // Declare expected results
      AbstractNode expected = getTreeRoot( "\\sin{x}" );
      
      // Get actual results 
      AbstractNode actual = getTreeRoot( "\\cos{x}" ).integrate();
      
      assertEquals( expected, actual );
   }
   
   //ln
   @Test public void test5() throws ExpressionException
   {
      // Declare expected results
      AbstractNode expected = getTreeRoot( "x^2" );
      
      // Get actual results 
      AbstractNode actual = getTreeRoot( "2 * x ^ 1" ).integrate();
      
      assertEquals( expected, actual ); 
   }*/
}