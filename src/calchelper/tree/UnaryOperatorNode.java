/*
 * UnaryOperatorNode.java 
 */

package calchelper.tree;

import java.util.ArrayList;

/**
 * Represents a unary operator in an expression.
 *
 * @author Patrick MacArthur
 * 
 * @deprecated No longer used for trig functions.  They are in their own
 * subclass of OperatorNode.
 */
@Deprecated
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
}
