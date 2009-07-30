/**
 *  A JUnit test harness for the Polynomial class, dealing primarily with
 * finding derivatives.
 * 
 * @author William Rideout
 */

package calchelper.tree;

import static org.junit.Assert.assertEquals;
import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;


public class DerivativeTest
{
   //======================== Variables =======================================
   HashMap<Double, Double> expect;//These are the expected results
   
   //======================== Methods =========================================
   //------------------------ dervTest(String) --------------------------------
   /**
    * Exercises the derive() abilities of polynomial.
    * All input must be in infix notation.
    */
   public static AbstractNode dervTest(String input) throws ExpressionException
   {
      TreeFactory tFact; //No, this is not Toad's Factory, Dan.
      ExpressionTree tree;
      AbstractNode root;
      Polynomial p;

      tFact = new TreeFactory(input);
      tree = tFact.buildTree();
      root = tree.getRoot();
      p = new Polynomial(root);
      return p.derive();
   }
   
   //------------------------ setup() -----------------------------------------
   @Before public void setUp()
   {
      expect = new HashMap<Double,Double>();
   }
   
   //------------------------ test() ------------------------------------------
   /**
    * Puts the derive method through its paces
    */
   @Test public void test() throws ExpressionException
   {
      AbstractNode nodeA = dervTest("2 * x ^ 1");
      AbstractNode nodeB = new TreeFactory("x^2").buildTree().getRoot();
      assertEquals(nodeB, nodeA);
   }
}
