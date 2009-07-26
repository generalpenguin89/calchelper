/**
 * UnaryOperatorNode.java
 *
 * Represents a unary operator in an expression.
 *
 * Currently used for trig functions.
 *
 * @author Patrick MacArthur 
 *
 * Based on code used for CS416 Programming Assignment #9
 */

package calchelper.tree;

import java.util.ArrayList;

class UnaryOperatorNode extends OperatorNode
{
   /*
    * Implementation notes:
    *
    * _children.get( 0 ) : only node
    */

   /**
    * Generates the node.
    */
   public UnaryOperatorNode( String op, AbstractNode child )
   {
      _type = op;
      _children = new ArrayList<AbstractNode>();
      _children.add( child );
   }

   /**
    * Returns the child node.
    */
   public AbstractNode getChild()
   {
      return _children.get( 0 );
   }

   /**
    * Checks the validity of the operator inside the node.
    */
   protected boolean isValid()
   {
      return true;
   }

   abstract static class TrigFunction extends UnaryOperatorNode
   {
      /**
       * Constructs a TrigFunction.
       */
      public TrigFunction( String op, AbstractNode child )
      {
         super( op, child );
      }
   }

   /**
    * Sine
    */
   static class SinNode extends TrigFunction
   {
      /**
       * Constructs a SinNode.
       */
      public SinNode( AbstractNode child )
      {
         super( "sin", child );
      }
   }

   /**
    * Cosine
    */
   static class CosNode extends TrigFunction
   {
      public CosNode( AbstractNode child )
      {
      /**
       * Constructs a CosNode.
       */
         super( "cos", child );
      }
   }

   /**
    * Tangent
    */
   static class TanNode extends TrigFunction
   {
      /**
       * Constructs a TanNode.
       */
      public TanNode( AbstractNode child )
      {
         super( "tan", child );
      }
   }
}
