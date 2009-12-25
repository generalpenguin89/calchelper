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
      poly = Polynomial.createPolynomial( treeRoot );
      assertTrue( poly != null );
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
      Polynomial copy = Polynomial.createPolynomial( original );
      
      // Assertion
      assertEquals( original, copy );
   }
   
   @Test public void cloneTest() throws ExpressionException,
      CloneNotSupportedException
   {
      // Declare expected results
      Polynomial original = new Polynomial( "x" );
      
      // Get results
      Polynomial copy = ( Polynomial ) original.clone();
      
      // Assertion
      assertTrue( copy != original );
      assertTrue( copy.getClass() == original.getClass() );
      assertTrue( copy.getMap() != original.getMap() );
      assertEquals( original, copy );
   }
   
   @Test public void defaultConstructorTest() throws ExpressionException
   {
      Polynomial poly = new Polynomial();
      
      assertEquals( 0, poly.getMap().size() );
   }
   
   @Test public void addTestDifferent() throws ExpressionException
   {
      expected.put( 1.0, 5.0 );
      expected.put( 2.0, 3.0 );
      
      Polynomial poly1 = unitTest( "5x" );
      Polynomial poly2 = unitTest( "3 * x^2" );
      Polynomial result = poly1.add( poly2 );
      
      assertEquals( expected, result.getMap() );
   }
   
   @Test public void addTestSame() throws ExpressionException
   {
      expected.put( 1.0, 12.0 );
      
      Polynomial poly1 = unitTest( "5x" );
      Polynomial poly2 = unitTest( "7x" );
      Polynomial result = poly1.add( poly2 );
      
      assertEquals( expected, result.getMap() );
   }
   
   @Test public void squareTest() throws ExpressionException
   {
      expected.put( 2.0, 25.0 );
      expected.put( 1.0, 30.0 );
      expected.put( 0.0, 9.0 );
      
      Polynomial actual = unitTest( "(5x + 3)*(5x + 3)" );
      
      assertEquals( expected, actual.getMap() );
   }
   
   @Test public void equalsIgnoreTest() throws ExpressionException
   {
      Polynomial poly1 = unitTest( "x + x^2" );
      Polynomial poly2 = unitTest( "3x + 5x^2" );
      
      assertTrue( poly1.equalsIgnoreCoefficients( poly2 ) );
      assertTrue( poly2.equalsIgnoreCoefficients( poly1 ) );
   }
   
   @Test public void equalsIgnoreTestFalse() throws ExpressionException
   {
      Polynomial poly1 = unitTest( "x + x^2 + 5x^3" );
      Polynomial poly2 = unitTest( "3x + 5x^2" );
      
      assertFalse( poly1.equalsIgnoreCoefficients( poly2 ) );
      assertFalse( poly2.equalsIgnoreCoefficients( poly1 ) );
   }
}
