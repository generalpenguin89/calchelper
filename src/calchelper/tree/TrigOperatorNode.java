/**
 * 
 */
package calchelper.tree;

import java.util.ArrayList;

/**
 * A node type to handle trigonometric functions with a coefficient term in 
 * front.  For example, it can hold 2x * tan( x^2 ) in one node.
 * 
 * @author Patrick MacArthur
 *
 */
abstract class TrigOperatorNode extends OperatorNode
{
   /*
    * Implementation notes:
    *
    * _children.get( 0 ) : left node
    * _children.get( 1 ) : right node
    */

   protected void init( String funct, AbstractNode left, AbstractNode right )
   {
      _type = funct;
      _children = new ArrayList<AbstractNode>();
      _children.add( left );
      _children.add( right );
   }
   
   /**
    * Gets the coefficient term, e.g., the typically polynomial term that
    * this function is multiplied by.
    * 
    * @return the coefficient term
    */
   public AbstractNode getCoefficientTerm()
   {
      return getNode( 0 );
   }
   
   /**
    * Gets the argument term, e.g., the term to which this function is
    * applied.
    * 
    * @return the argument term
    */
   public AbstractNode getArgument()
   {
      return getNode( 1 );
   }
   
   /**
    * Sets the left node.
    */
   public void setCoeffiecientTerm( AbstractNode node )
   {
      _children.set( 0, node );
   }
   
   /**
    * Sets the right node.
    */
   public void setArgument( AbstractNode node )
   {
      _children.set( 1, node );
   }
   
   /**
    * Returns the node count.
    */
   public int nodeCount()
   {
      return 2;
   }
}
