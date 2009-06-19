/**
 * AbstractNode
 *
 * A base class for all expression tree node types.
 *
 * @author Patrick MacArthur, for CS416 Programming Assignment #9
 */

package calchelper.tree;

abstract class AbstractNode
{
   /**
    * Returns a string representing the node of the tree.  Assumes 0 depth.
    */
   public String toString()
   {
      return toString( 0 );
   }

   /**
    * Returns a string representing the node of the tree at the specified
    * depth.
    *
    * @param depth The depth of the tree.
    *
    */
   protected String toString( int depth )
   {
      String str = "";
      for ( int i = 0; i < depth; ++i )
      { 
         str += "   ";
      }

      return str;
   }

   /**
    * Simplifies the expression below this node.
    */
   public void simplify()
   {
   }

   /**
    * Determines if the node has an a value.
    */
   public boolean hasValue()
   {
      return false;
   }

   /**
    * Returns the vale of the node.
    */
   public double getValue()
   {
      return 0;
   }
}

