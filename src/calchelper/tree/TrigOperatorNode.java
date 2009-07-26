/**
 * 
 */
package calchelper.tree;

/**
 * A node type to handle trigonometric functions with a coefficient term in 
 * front.  For example, it can hold 2x * tan( x^2 ) in one node.
 * 
 * @author Patrick MacArthur
 *
 */
abstract class TrigOperatorNode extends OperatorNode
{
   
   /**
    * 
    */
   public TrigOperatorNode()
   {
      // TODO Auto-generated constructor stub
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
}
