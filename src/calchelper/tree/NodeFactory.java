/**
 * NodeFactory
 *
 * A class to create nodes irrespective of their concrete type.
 *
 * The class provides static methods to create operator nodes of different
 * types.
 */

package calchelper.tree;

class NodeFactory
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

   public static BinaryOperatorNode createBinaryOperatorNode( String op, 
                                                        AbstractNode left,
                                                        AbstractNode right)
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
   
   public static AbstractNode createTrigNode( String op, AbstractNode left,
                                              AbstractNode right,
                                              AbstractNode exponent )
   {
      if ( op.equals( "sin" ) )
      {
         return new TrigOperatorNode.Sine( left, right, exponent );
      }
      else if ( op.equals( "cos" ) )
      {
         return new TrigOperatorNode.Cosine( left, right, exponent );
      }
      else if ( op.equals( "cot" ) )
      {
         return new TrigOperatorNode.Cotangent( left, right, exponent  );
      }
      else if ( op.equals( "sec" ))
      {
         return new TrigOperatorNode.Secant( left, right, exponent );
      }
      else if ( op.equals( "csc" ))
      {
         return new TrigOperatorNode.Cosecant( left, right, exponent );
      }
      else if ( op.equals( "ln" ) )
      {
         return new TrigOperatorNode.NatLog( left, right, exponent );
      }
      else
      {
         throw new UnsupportedOperationException( "Not a supported node type: " + op );
      }
   }
   
   public static AbstractNode createNode( String op, double left, 
                                          double right )
   {
   	return createNode( op, createConstantNode( left ),
   								  createConstantNode( right ) );
   }
   
   public static AbstractNode createNode( String op, AbstractNode left, AbstractNode right )
   {
      if ( op.length() == 1 )
      {
         BinaryOperatorNode binNode = createBinaryOperatorNode( op, left, right);
         Polynomial poly = new Polynomial( binNode );
         if ( poly.isValid() )
         {
            return poly;
         }
         else
         {
            return binNode;
         }
      }
      else
      {
        return createTrigNode( op, left, right, createConstantNode( 1.0 ) ); 
      }
   }
   
   public static AbstractNode createConstantNode( double constant )
   {
   	return new Polynomial( constant );
   }
   
   public static AbstractNode createVariableNode( String variable )
   {
   	return new Polynomial( variable );
   }
}
