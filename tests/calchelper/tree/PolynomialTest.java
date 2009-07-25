/*
 * Polynomial JUnit Test Suite
 */

package calchelper.tree;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

/**
 * JUnit-based test harness for Polynomial.
 *
 * @author Patrick MacArthur
 */

public class PolynomialTest
{
   // Expected results for each test
   HashMap<Double,Double> expected;

   // This exercises the Polynomial class
   public static Polynomial unitTest( String infix )
         throws ExpressionException
   {
      TreeFactory factory;
      ExpressionTree tree;
      AbstractNode treeRoot;
      Polynomial poly;

      factory = new TreeFactory( infix );
      tree = factory.buildTree();
      treeRoot = tree.getRoot();
      poly = new Polynomial( treeRoot );
      assertTrue( poly.isValid() );
      return poly;
   }

   @Before public void setUp()
   {
      expected = new HashMap<Double,Double>();
   }

   @Test public void test1_1() throws ExpressionException
   {
      Polynomial result = unitTest( "5 * x ^ 2" );
      expected.put( 2.0, 5.0 );
      assertEquals( expected, result.getMap() );
   }

   @Test public void test1_2() throws ExpressionException
   {
      Polynomial result = unitTest( "( 5 * x ^ 3 ) ^ 2" );
      expected.put( 6.0, 25.0 );
      assertEquals( expected, result.getMap() );
   }

   @Test public void test1_3() throws ExpressionException
   {
      Polynomial result = unitTest( "x + 2 * x" );
      expected.put( 1.0, 3.0 );
      assertEquals( expected, result.getMap() );
   }

   @Test public void test1_4() throws ExpressionException
   {
      Polynomial result = unitTest( "3 * x + 2 * x" );
      expected.put( 1.0, 5.0 );
      assertEquals( expected, result.getMap() );
   }

   @Test public void test1_5() throws ExpressionException
   {
      Polynomial result = unitTest( "x * x" );
      expected.put( 2.0, 1.0 );
      assertEquals( expected, result.getMap() );
   }

   @Test public void test1_6() throws ExpressionException
   {
      Polynomial result = unitTest( "x * 2 * x" );
      expected.put( 2.0, 2.0 );
      assertEquals( expected, result.getMap() );
   }

   @Test public void test1_7() throws ExpressionException
   {
      Polynomial result = unitTest( "x + 2 * x ^ 2" );
      expected.put( 1.0, 1.0 );
      expected.put( 2.0, 2.0 );
      assertEquals( expected, result.getMap() );
   }

   @Test public void test2_1() throws ExpressionException
   {
      Polynomial result = unitTest( "4 * ( x + 3 )" );
      expected.put( 1.0, 4.0 );
      expected.put( 0.0, 12.0 );
      assertEquals( expected, result.getMap() );
   }

   @Test public void test2_2() throws ExpressionException
   {
      Polynomial result = unitTest( "( x + 3 ) * 4" );
      expected.put( 1.0, 4.0 );
      expected.put( 0.0, 12.0 );
      assertEquals( expected, result.getMap() );
   }

   @Test public void test2_3() throws ExpressionException
   {
      Polynomial result = unitTest( "x * ( x + 3 )" );
      expected.put( 1.0, 3.0 );
      expected.put( 2.0, 1.0 );
      assertEquals( expected, result.getMap() );
   }

   @Test public void test2_4() throws ExpressionException
   {
      Polynomial result = unitTest( "( x + 3 ) * x" );
      expected.put( 1.0, 3.0 );
      expected.put( 2.0, 1.0 );
      assertEquals( expected, result.getMap() );
   }

   @Test public void test3_1() throws ExpressionException
   {
      Polynomial result = unitTest( "4 * ( x - 3 )" );
      expected.put( 1.0, 4.0 );
      expected.put( 0.0, -12.0 );
      assertEquals( expected, result.getMap() );
   }

   @Test public void test3_2() throws ExpressionException
   {
      Polynomial result = unitTest( "x * ( x - 3 )" );
      expected.put( 1.0, -3.0 );
      expected.put( 2.0, 1.0 );
      assertEquals( expected, result.getMap() );
   }

   @Test public void test4_1() throws ExpressionException
   {
      // Declare expected results
      expected.put( 3.0,  1.0 );
      expected.put( 2.0,  3.0 );
      expected.put( 1.0,  4.0 );
      expected.put( 0.0, 12.0 );

      // Get results
      Polynomial result = unitTest( "( 4 + x ^ 2 ) * ( x + 3 )" );

      // Assertion
      assertEquals( expected, result.getMap() );
   }

   @Test public void test4_2() throws ExpressionException
   {
      // Declare expected results
      expected.put( 3.0, -1.0 );
      expected.put( 2.0, -3.0 );
      expected.put( 1.0,  4.0 );
      expected.put( 0.0, 12.0 );

      // Get results
      Polynomial result = unitTest( "( 4 - x ^ 2 ) * ( x + 3 )" );

      // Assertion
      assertEquals( expected, result.getMap() );
   }

   @Test public void test4_3() throws ExpressionException
   {
      // Declare expected results
      expected.put( 3.0,  1.0 );
      expected.put( 2.0,  3.0 );
      expected.put( 1.0,  4.0 );
      expected.put( 0.0, 12.0 );

      // Get results
      Polynomial result = unitTest( "( x + 3 ) * ( 4 + x ^ 2 )" );

      // Assertion
      assertEquals( expected, result.getMap() );
   }

   @Test public void test4_4() throws ExpressionException
   {
      // Declare expected results
      expected.put( 3.0,  -1.0 );
      expected.put( 2.0,   3.0 );
      expected.put( 1.0,   4.0 );
      expected.put( 0.0, -12.0 );

      // Get results
      Polynomial result = unitTest( "( x - 3 ) * ( 4 - x ^ 2 )" );

      // Assertion
      assertEquals( expected, result.getMap() );
   }

   @Test public void test4_5() throws ExpressionException
   {
      // Declare expected results
      expected.put( 3.0,  1.0 );
      expected.put( 2.0, -3.0 );
      expected.put( 1.0, -4.0 );
      expected.put( 0.0, 12.0 );

      // Get results
      Polynomial result = unitTest( "( 3 - x ) * ( 4 - x ^ 2 )" );

      // Assertion
      assertEquals( expected, result.getMap() );
   }
   
   @Test public void test5_1() throws ExpressionException
   {
      // Declare expected results
      expected.put( 3.0,  11.0 );
      expected.put( 2.0,  38.0 );
      expected.put( 1.0,  92.0 );
      expected.put( 0.0,  35.0 );

      // Get results
      Polynomial result = unitTest( "( x ^ 2 + 3 * x + 7 ) * ( 11 * x + 5 )" );

      // Assertion
      assertEquals( expected, result.getMap() );
   }
   
   @Test public void copyTest() throws ExpressionException
   {
      // Declare expected results
      Polynomial original = new Polynomial( "x" );
      
      // Get results
      Polynomial copy = new Polynomial( original );
      
      // Assertion
      assertEquals( original, copy );
   }
}
