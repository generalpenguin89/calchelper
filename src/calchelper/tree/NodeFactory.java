/*
 * NodeFactory
 * 
 * Copyright 2009 Ben Decato, Patrick MacArthur, William Rideout, and
 * Jake Schwartz
 * 
 * This file is part of CalcHelper.
 * 
 * CalcHelper is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * CalcHelper is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with CalcHelper.  If not, see <http://www.gnu.org/licenses/>.
 */

package calchelper.tree;

/**
 * A class to create nodes irrespective of their concrete type.
 *
 * The class provides static methods to create operator nodes of different
 * types.
 * 
 * @author Patrick MacArthur
 * 
 */
class NodeFactory
{
   /**
    * Creates a binary operator node based on the string given.
    *
    * @param type The type of operator.
    * @param left The left operand.
    * @param right The right operand.
    *
    * @return The operator node, or null if the operator is invalid.
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
      else if ( op.equals( "^" ) )
      {
         return new BinaryOperatorNode.Power( left, right );
      }
      else
      {
         return null;
      }
   }
   
   /**
    * Creates a trigonometric function node based on the given nodes.
    * 
    * @param op The trig function.
    * @param coefTerm The coefficient term of the trig function.
    * @param arg The argument of the trig function.
    * @param exponent The power of the trig function.
    * @return The resulting trig function node.
    */
   public static AbstractNode createTrigNode( String op, AbstractNode coefTerm,
                                              AbstractNode arg,
                                              AbstractNode exponent )
   {
      if ( op.equals( "sin" ) )
      {
         return new TrigOperatorNode.Sine( coefTerm, arg, exponent );
      }
      else if ( op.equals( "cos" ) )
      {
         return new TrigOperatorNode.Cosine( coefTerm, arg, exponent );
      }
      else if ( op.equals( "tan" ) )
      {
         return new TrigOperatorNode.Tangent( coefTerm, arg, exponent  );
      }
      else if ( op.equals( "cot" ) )
      {
         return new TrigOperatorNode.Cotangent( coefTerm, arg, exponent  );
      }
      else if ( op.equals( "sec" ))
      {
         return new TrigOperatorNode.Secant( coefTerm, arg, exponent );
      }
      else if ( op.equals( "csc" ))
      {
         return new TrigOperatorNode.Cosecant( coefTerm, arg, exponent );
      }
      else if ( op.equals( "ln" ) )
      {
         return new TrigOperatorNode.NatLog( coefTerm, arg, exponent );
      }
      else
      {
         throw new UnsupportedOperationException( "Not a supported node type: " + op );
      }
   }
   
   /**
    * Convenience method to create a binary node with two constants.
    * 
    * @param op The operation being done.
    * @param left The left constant.
    * @param right The right constant.
    * @return The operator node resulting from the operation.
    */
   public static AbstractNode createNode( String op, double left, 
                                          double right )
   {
   	return createNode( op, createConstantNode( left ),
   								  createConstantNode( right ) );
   }
   
   /**
    * Creates any type of operator node.
    * 
    * @param op The operation being done.
    * @param left The left node.
    * @param right The right node.
    * @return The operator node resulting from the operation.
    */
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
   
   /**
    * Creates a constant node with the specified value.
    * 
    * @param constant The value of the constant to create.
    * @return The constant node.
    */
   public static AbstractNode createConstantNode( double constant )
   {
   	return new Polynomial( constant );
   }
   
   /**
    * Creates a variable node with the specified value.
    * 
    * @param variable The value of the variable to create.
    * @return The variable node.
    */
   public static AbstractNode createVariableNode( String variable )
   {
   	return new Polynomial( variable );
   }
}
