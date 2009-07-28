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
   /*@Test public void test2() throws ExpressionException
   {
      AbstractNode result = intTest( "2 * x ^ 1" );
      AbstractNode expected = new TreeFactory( "x^2" ).buildTree().getRoot();
      assertEquals( expected, result );  
   }
   
   //sine
   @Test public void test3() throws ExpressionException
   {
      AbstractNode result = intTest( "2 * x ^ 1" );
      AbstractNode expected = new TreeFactory( "x^2" ).buildTree().getRoot();
      assertEquals( expected, result );  
   }
   
   //cos
   @Test public void test4() throws ExpressionException
   {
      AbstractNode result = intTest( "2 * x ^ 1" );
      AbstractNode expected = new TreeFactory( "x^2" ).buildTree().getRoot();
      assertEquals( expected, result );  
   }
   
   //ln
   @Test public void test5() throws ExpressionException
   {
      AbstractNode result = intTest( "2 * x ^ 1" );
      AbstractNode expected = new TreeFactory( "x^2" ).buildTree().getRoot();
      assertEquals( expected, result );  
   }*/
}