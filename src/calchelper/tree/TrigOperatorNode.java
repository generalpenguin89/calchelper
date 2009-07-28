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
   /**
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
    * Returns the additive inverse of the node.
    */
   public AbstractNode inverse()
   {
      TrigOperatorNode copy = ( TrigOperatorNode ) NodeFactory.createNode( 
               _type, getCoefficientTerm(), getArgument() );
      copy.setCoefficientTerm( NodeFactory.createNode( "*", 
               NodeFactory.createConstantNode( -1.0 ), this ) );
      copy.getCoefficientTerm().simplify();
      return copy;
   }
   
   /**
    * Node to represent sine nodes.
    *    
    * @author Patrick MacArthur
    */
   public static class Sine extends TrigOperatorNode
   {
      public Sine( AbstractNode coefficient, AbstractNode argument )
      {
         init( "sin", coefficient, argument );
      }
      
      public AbstractNode derive()
      {
         // FIXME stub
         return null;
      }

      public AbstractNode integrate()
      {
         // FIXME stub
         return null;
      }
   }
   
   /**
    * Node to represent cosine function.
    * 
    * @author Patrick MacArthur
    *
    */
   public static class Cosine extends TrigOperatorNode
   {
      public Cosine( AbstractNode coefficient, AbstractNode argument )
      {
         init( "cos", coefficient, argument );
      }
      
      public AbstractNode derive()
      {
         // FIXME stub
         return null;
      }

      public AbstractNode integrate()
      {
         // FIXME stub
         return null;
      }
   }
   
   /**
    * Sets the coefficient node.
    */
   public void setCoefficientTerm( AbstractNode node )
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
