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

   // This exercises the Polynomial class
   public static Polynomial intTest( String infix ) throws ExpressionException
   {
      TreeFactory factory;
      ExpressionTree tree;
      AbstractNode treeRoot;
      Polynomial poly;

      factory = new TreeFactory( infix );
      tree = factory.buildTree();
      treeRoot = tree.getRoot();
      poly = new Polynomial( treeRoot );
      poly.derive();
      return poly;
   }

   @Before public void setUp()
   {
      expected = new HashMap<Double,Double>();
   }
   
   @Test public void test1() throws ExpressionException
   {
      Polynomial result = intTest( "2 * x ^ 1" );
      expected.put( 0.0, 2.0 );
      assertEquals( expected, result.getMap() );
   }
}