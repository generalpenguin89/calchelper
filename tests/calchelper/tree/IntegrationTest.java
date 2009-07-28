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

   // This exercises the Polynomial class
   public static AbstractNode intTest( String infix ) throws ExpressionException
   {
      TreeFactory factory;
      ExpressionTree tree;
      AbstractNode treeRoot;
      Polynomial poly;

      factory = new TreeFactory( infix );
      tree = factory.buildTree();
      treeRoot = tree.getRoot();
      poly = new Polynomial( treeRoot );
      return poly.integrate();
   }

   @Before public void setUp()
   {
      expected = new HashMap<Double,Double>();
   }
   
   //Polynomial test
   @Test public void test1() throws ExpressionException
   {
      AbstractNode result = intTest( "2 * x ^ 1" );
      AbstractNode expected = new TreeFactory( "x^2" ).buildTree().getRoot();
      assertEquals( expected, result );  
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