package calchelper.tree;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import calchelper.tree.NodeFactory;

public class TreeFactoryTest {

   @Before
   public void setUp() throws Exception {
   }
   
   @Test public void testSimpleAddition() throws Exception
   {
   	ExpressionTree expected = new ExpressionTree( 
   			NodeFactory.createNode("+", 4, 5) );
   	
   	ExpressionTree result = buildTreeFromString( "4 + 5 ");
   	
   	assertEquals( expected, result );
   }
   
   @Test public void testParenthesizedAddition() throws Exception
   {
   	ExpressionTree expected = new ExpressionTree( 
   			NodeFactory.createNode("+", 4, 5) );
   	
   	ExpressionTree result = buildTreeFromString( "( 4 + 5 )" );
   	
   	assertEquals( expected, result );
   }
   
   @Test public void testOrderOfOperations() throws Exception
   {
   	ExpressionTree expected = buildTreeFromString( "2 + ( 4 * 5 )" );
   	
   	ExpressionTree result = buildTreeFromString( "( 2 + 4 * 5 )" );
   	
   	assertEquals( expected, result );
   }
   
   @Test public void testAdditionInsideMultiplication() throws Exception
   {
   	ExpressionTree expected = new ExpressionTree(
   			NodeFactory.createNode("*", 
   					NodeFactory.createNode( "+", 2, 4 ),
   					NodeFactory.createConstantNode( 5 ) ) );
   	
   	ExpressionTree result = buildTreeFromString( "( 2 + 4 ) * 5" );
   	
   	assertEquals( expected, result );
   }
   
   @Test public void testAdditionInsideDivision() throws Exception
   {
   	ExpressionTree expected = new ExpressionTree(
   			NodeFactory.createNode("/",
   					NodeFactory.createConstantNode( 4 ),
   					NodeFactory.createNode( "+", 5, 5 ) ) );
   	
   	ExpressionTree result = buildTreeFromString( "( 4 / ( 5 + 5 ) )" );
   	
   	assertEquals( expected, result );
   }
   
   @Test public void testConstantAdditionNoWhitespace() throws Exception
   {
   	ExpressionTree expected = new ExpressionTree(
   	         NodeFactory.createNode( "+",
   	                  NodeFactory.createConstantNode( 4 ),
   	                  NodeFactory.createConstantNode( 5 ) ) );
   	
   	ExpressionTree result = buildTreeFromString( "4+5" );
   	
   	assertEquals( expected, result );
   }
   
   @Test public void testVariableAdditionNoWhitespace() throws Exception
   {
      ExpressionTree expected = new ExpressionTree(
               NodeFactory.createNode( "+",
                        NodeFactory.createConstantNode( 4 ),
                        NodeFactory.createVariableNode( "a" ) ) );
   	
   	ExpressionTree result = buildTreeFromString( "4+a" );
   	
   	assertEquals( expected, result );
   }
   
   @Test public void testAddCoefficients() throws Exception
   {
      ExpressionTree expected = new ExpressionTree(
               NodeFactory.createNode( "*",
                        NodeFactory.createConstantNode( 9 ),
                        NodeFactory.createVariableNode( "a" ) ) );
   	
   	ExpressionTree result = buildTreeFromString( "4a+5a" );
   	
   	assertEquals( expected, result );
   }
   
   @Test public void testMultiplyPolynomialTerms() throws Exception
   {
      ExpressionTree expected = new ExpressionTree(
               NodeFactory.createNode( "*",
                        NodeFactory.createConstantNode( 20 ),
                        NodeFactory.createNode( "^",
                                 NodeFactory.createVariableNode( "a" ),
                                 NodeFactory.createConstantNode( 2 ) ) ) );
   	   	
   	ExpressionTree result = buildTreeFromString( "4a*5a" );
   	
   	assertEquals( expected, result );
   }
   
   @Test public void precedenceTest1() throws Exception
   {
      ExpressionTree expected = buildTreeFromString( "4 + ( 5 * a )" );
      
      ExpressionTree result = buildTreeFromString( "4 + 5 * a " );
      
      assertEquals( expected, result );
   }
   
   @Test public void precedenceTest2() throws Exception
   {
      ExpressionTree expected = buildTreeFromString( "4 * ( 5 ^ a )" );
      
      ExpressionTree result = buildTreeFromString( "4 * 5 ^ a" );
      
      assertEquals( expected, result );
   }
   
   @Test public void coefficientPowerTest1() throws Exception
   {
      // Declare expected results
      ExpressionTree expected = buildTreeFromString( "3 * x ^ 2" );
      
      // get actual results
      ExpressionTree actual = buildTreeFromString( "3x^2" );
      
      assertEquals( expected, actual );
   }
   
   
   @Test public void coefficientPowerTest2() throws Exception
   {
      // Declare expected results
      ExpressionTree expected = buildTreeFromString( "( 3 * x ) ^ 2" );
      
      // get actual results
      ExpressionTree actual = buildTreeFromString( "(3x)^2" );
      
      assertEquals( expected, actual );
   }
   
   @Test public void sinTest() throws Exception
   {
      // Declare expected results
      ExpressionTree expected = new ExpressionTree( 
               NodeFactory.createNode( "sin",
                        NodeFactory.createConstantNode( 1.0 ),
                        NodeFactory.createVariableNode( "x" ) ) );
      
      ExpressionTree actual = buildTreeFromString( "\\sin{x}" );
      
      assertEquals( expected, actual );
   }

   /**
    * Helper method for tests.
    */
   static ExpressionTree buildTreeFromString( String expr ) 
         throws ExpressionException
   {
      TreeFactory builder = new TreeFactory();
      ExpressionTree tree = builder.buildTree( expr );
      return tree;
   }
}
