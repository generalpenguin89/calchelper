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
   			NodeFactory.createBinaryOperatorNode("+", 4, 5) );
   	
   	ExpressionTree result = unitTest( "4 + 5 ");
   	
   	assertEquals( expected, result );
   }

   /**
    * Unit test.
    */
   public static ExpressionTree unitTest( String expr ) throws ExpressionException
   {
      TreeFactory builder = new TreeFactory ( expr );
      ExpressionTree tree = builder.buildTree();
      return tree;
   }

   /**
    * Unit test for TreeFactory.
    */
   /*public static void main( String[] argrs ) throws ExpressionException
   {
      unitTest( "4 + 5" );
      unitTest( "( 4 + 5 )" );
      unitTest( "( 2 + 4 * 5 )" );
      unitTest( "2 + ( 4 * 5 )" );
      unitTest( "( 2 + 4 ) * 5" );
      unitTest( "( 4 / ( 5 + 5 ) )" );
      unitTest( "4+5" );
      unitTest( "4+a" );
      unitTest( "4a+5a" );
      unitTest( "4a*5a" );
      unitTest( "(4a*5a)" );
   }*/
}
