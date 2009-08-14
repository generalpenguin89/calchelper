

package calchelper.tree;

import java.util.ArrayList;
/**
 * BinaryOperatorNode.java
 *
 * Represents an n-ary operator in an expression.
 *
 * Currently used for + and * since they are commutative and associative--order
 * shouldn't matter at all
 *
 * @author Patrick MacArthur
 * @deprecated This class has not been used at all.  Use Polynomial for things
 * representing polynomials.  Use many BinaryOperatorNode objects for
 * everything else. 
 */
@Deprecated
class NaryOperatorNode extends OperatorNode
{
   /**
    * Generates a binary operator node.
    */
   public NaryOperatorNode( String op )
   {
      _type = op;
      _children = new ArrayList<AbstractNode>();
   }

   /**
    * Adds an operand to the operand list.
    */
   public void addOperand( AbstractNode operand )
   {
      if ( operand instanceof NaryOperatorNode )
      {
      NaryOperatorNode node = ( NaryOperatorNode ) operand;
      if ( node != null && node.getType().equals( this.getType() ) )
      {
         // Add the node's children to this node
         for ( AbstractNode child : node._children )
         {
            _children.add( child );
         }
      }
      }
      else
      {
         _children.add( operand );
      }
   }

   /**
    * Checks the validity of the operator inside the node.
    */
   protected boolean isValid()
   {
      if ( _type.length() > 0 )
      {
         return false;
      }

      switch ( _type.charAt( 0 ) )
      {
         case '+': // fall through
         case '*':
            return true;
         default:
            return false;
      }
   }
}

