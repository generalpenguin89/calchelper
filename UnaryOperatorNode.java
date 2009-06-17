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

import java.util.ArrayList;

public class UnaryOperatorNode extends OperatorNode
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

   /*
    * Checks the validity of the operator inside the node.
    */
   protected boolean isValid()
   {
      return true;
   }
}
