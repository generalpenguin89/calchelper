package calchelper.tree;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import calchelper.tree.NodeFactory;

public class TreeFactoryTest {

   @Before
   public void setUp() throws Exception {
   }
   
   @Test public void test1_1() throws Exception
   {
   	ExpressionTree expected = new ExpressionTree( 
   			NodeFactory.createNode("+", 4, 5) );
   	
   	ExpressionTree result = unitTest( "4 + 5 ");
   	
   	assertEquals( expected, result );
   }
   
   @Test public void test1_2() throws Exception
   {
   	ExpressionTree expected = new ExpressionTree( 
   			NodeFactory.createNode("+", 4, 5) );
   	
   	ExpressionTree result = unitTest( "( 4 + 5 )" );
   	
   	assertEquals( expected, result );
   }
   
   @Test public void test1_3() throws Exception
   {
   	ExpressionTree expected = unitTest( "2 + ( 4 * 5 )" );
   	
   	ExpressionTree result = unitTest( "( 2 + 4 * 5 )" );
   	
   	assertEquals( expected, result );
   }
   
   @Test public void test1_4() throws Exception
   {
   	ExpressionTree expected = new ExpressionTree(
   			NodeFactory.createNode("*", 
   					NodeFactory.createNode( "+", 2, 4 ),
   					NodeFactory.createConstantNode( 5 ) ) );
   	
   	ExpressionTree result = unitTest( "( 2 + 4 ) * 5" );
   	
   	assertEquals( expected, result );
   }
   
   @Test public void test1_5() throws Exception
   {
   	ExpressionTree expected = new ExpressionTree(
   			NodeFactory.createNode("/",
   					NodeFactory.createConstantNode( 4 ),
   					NodeFactory.createNode( "+", 5, 5 ) ) );
   	
   	ExpressionTree result = unitTest( "( 4 / ( 5 + 5 ) )" );
   	
   	assertEquals( expected, result );
   }
   
   @Test public void test2_1() throws Exception
   {
   	ExpressionTree expected = unitTest( "4 + 5" );
   	
   	ExpressionTree result = unitTest( "4+5" );
   	
   	assertEquals( expected, result );
   }
   
   @Test public void test2_2() throws Exception
   {
   	ExpressionTree expected = unitTest( "4 + a" );
   	
   	ExpressionTree result = unitTest( "4+a" );
   	
   	assertEquals( expected, result );
   }
   
   @Test public void test2_3() throws Exception
   {
   	ExpressionTree expected = unitTest( "4 * a + 5 * a" );
   	
   	ExpressionTree result = unitTest( "4a+5a" );
   	
   	assertEquals( expected, result );
   }
   
   @Test public void test2_4() throws Exception
   {
   	ExpressionTree expected = unitTest( "( 4 * a ) * ( 5 * a )" );
   	
   	ExpressionTree result = unitTest( "4a*5a" );
   	
   	assertEquals( expected, result );
   }
   
   @Test public void test2_5() throws Exception
   {
   	ExpressionTree expected = unitTest( "( 4 * a ) * ( 5 * a )" );
   	
   	ExpressionTree result = unitTest( "(4a*5a)" );
   	
   	assertEquals( expected, result );
   }
   
   @Test public void precedenceTest1() throws Exception
   {
      ExpressionTree expected = unitTest( "4 + ( 5 * a )" );
      
      ExpressionTree result = unitTest( "4 + 5 * a " );
      
      assertEquals( expected, result );
   }
   
   @Test public void precedenceTest2() throws Exception
   {
      ExpressionTree expected = unitTest( "4 * ( 5 ^ a )" );
      
      ExpressionTree result = unitTest( "4 * 5 ^ a" );
      
      assertEquals( expected, result );
   }
   
   @Test public void coefficientPowerTest1() throws Exception
   {
      // Declare expected results
      ExpressionTree expected = unitTest( "3 * x ^ 2" );
      
      // get actual results
      ExpressionTree actual = unitTest( "3x^2" );
      
      assertEquals( expected, actual );
   }
   
   
   @Test public void coefficientPowerTest2() throws Exception
   {
      // Declare expected results
      ExpressionTree expected = unitTest( "( 3 * x ) ^ 2" );
      
      // get actual results
      ExpressionTree actual = unitTest( "(3x)^2" );
      
      assertEquals( expected, actual );
   }
   
   @Test public void sinTest() throws Exception
   {
      // Declare expected results
      ExpressionTree expected = new ExpressionTree( 
               NodeFactory.createNode( "sin",
                        NodeFactory.createConstantNode( 1.0 ),
                        NodeFactory.createVariableNode( "x" ) ) );
      
      ExpressionTree actual = unitTest( "\\sin{x}" );
      
      assertEquals( expected, actual );
   }

   /**
    * Helper method for tests.
    */
   static ExpressionTree unitTest( String expr ) throws ExpressionException
   {
      TreeFactory builder = new TreeFactory ( expr );
      ExpressionTree tree = builder.buildTree();
      return tree;
   }
}
