package calchelper.tree;

import static org.junit.Assert.assertEquals;
import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit-based test harness for Polynomial.
 *
 * Written by Patrick McArthur
 * Edited by Ben Decato
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
   
   @Test public void polynomialTest1() throws ExpressionException
   {
      // Declare expected results
      AbstractNode expected = getTreeRoot( "2" );
      
      // Get actual results 
      AbstractNode actual = getTreeRoot( "2 * x ^ 1" ).derive();
      
      assertEquals( expected, actual );
   }
}