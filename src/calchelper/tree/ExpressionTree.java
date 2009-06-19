/**
 * ExpressionTree.java
 *
 * A tree that represents a mathematical expression.
 *
 * @author Patrick MacArthur, for CS416 Programming Assignment #9
 */

package calchelper.tree;

public class ExpressionTree
{
   // instance variables
   /*private*/ AbstractNode _root;

   // Constructor
   public ExpressionTree( AbstractNode root )
   {
      _root = root;
   }

   /**
    * Simplifies the tree.
    */
   public void simplify()
   {
      _root.simplify();
   }

   /**
    * toString()
    *
    * Converts the tree into a human-readable string.
    *
    * @return A human-readable string.
    */
   public String toString()
   {
      return _root.toString();
   }
}
