/**
 * NodeFactory
 *
 * A class to create nodes irrespective of their concrete type.
 *
 * The class provides static methods to create operator nodes of different
 * types.
 */
public class NodeFactory
{
   /**
    * Creates a binary operator node based on the string given.
    *
    * @param type The type of operator.
    * @param left The left operand.
    * @param right The right operand.
    *
    * @return The AbstractNode, or null if the operator is invalid.
    */

   public static OperatorNode createBinaryOperatorNode( String op, 
                                                        AbstractNode left,
                                                        AbstractNode right )
   {
      if ( op.equals( "+" ) )
      {
         return new BinaryOperatorNode.Addition( left, right );
      }
      else if ( op.equals( "*" ) )
      {
         return new BinaryOperatorNode.Multiplication( left, right );
      }
      else if ( op.equals( "-" ) )
      {
         return new BinaryOperatorNode.Subtraction( left, right );
      }
      else if ( op.equals( "/" ) )
      {
         return new BinaryOperatorNode.Division( left, right );
      }
      else if ( op.equals( "%" ) )
      {
         return new BinaryOperatorNode.Modulus( left, right );
      }
      else if ( op.equals( "^" ) )
      {
         return new BinaryOperatorNode.Power( left, right );
      }
      else
      {
         return null;
      }
   }
}
