/*
 * Monomial
 */

package calchelper.tree;

/**
 * Represents a Monomial with a given coefficient, variable, and power.
 *
 * In reality, this is a power function since we currently don't support
 * multidimensional calculus.
 *
 * @author Patrick MacArthur
 */

@Deprecated
class Monomial
{
   private boolean valid; // is the monomial valid?
   double coefficient;    // the constant factor
   VariableNode variable; // the variable factor
   double power;          // power that the variable factor is raised to

   /**
    * Builds a monomial from a node that is assumed to contain only
    * multiplication, constants, variables, and power.
    */
   public Monomial( AbstractNode node )
   {
      // Set some defaults
      valid = true;
      coefficient = 1;
      variable = null;
      power = 0;

      if ( node.hasValue() )
      {
         // Base case 1
         coefficient = node.getValue();
      }
      else if ( node instanceof VariableNode )
      {
         // Base case 2
         variable = ( VariableNode ) node;
         power++;
      }
      else if ( node instanceof BinaryOperatorNode.Division )
      {
         // Recursive case 1
         BinaryOperatorNode.Division divNode =
                  ( BinaryOperatorNode.Division ) node;
         for ( int x = 0; x < divNode.nodeCount(); ++x )
         {
            Monomial mon = new Monomial( divNode.getNode( x ) );
            if ( mon.isValid() && isVariableSame( mon ) )
            {
               coefficient /= mon.coefficient;
               power -= mon.power;
            }
            else
            {
               valid = false;
            }
         }
      }
      else if ( node instanceof BinaryOperatorNode.Multiplication )
      {
         // Recursive case 2
         BinaryOperatorNode.Multiplication multNode =
                  ( BinaryOperatorNode.Multiplication ) node;
         for ( int x = 0; x < multNode.nodeCount(); ++x )
         {
            Monomial mon = new Monomial( multNode.getNode( x ) );
            if ( mon.isValid() && isVariableSame( mon ) )
            {
               coefficient *= mon.coefficient;
               power += mon.power;
            }
            else
            {
               valid = false;
            }
         }
      }
      else if ( node instanceof BinaryOperatorNode.Power )
      {
         // Recursive case 3
         BinaryOperatorNode.Power powNode = ( BinaryOperatorNode.Power ) node;
         Monomial mon = new Monomial( powNode.getLeft() );
         if ( mon.isValid() && powNode.getRight().hasValue() &&
              isVariableSame( mon ) )
         {
            coefficient *= Math.pow( mon.coefficient,
                     powNode.getRight().getValue() );
            power = mon.power * powNode.getRight().getValue();
         }
         else
         {
            valid = false;
         }
      }
      else
      {
         // Base case 3
         valid = false;
      }

      //Want really verbose meaningless output?  Uncomment this line
      /*if ( variable != null )
      {
         System.out.println( coefficient + " " + variable.getStringValue() +
                             "^" + power + " valid: " + valid );
      }
      else
      {
         System.out.println( coefficient + " ?^" + power + " valid: " + valid );
      }*/
   }

   public boolean isVariableSame( Monomial mon )
   {
      if ( this.variable == null )
      {
         this.variable = mon.variable;
         return true;
      }
      else if ( this.variable != null && mon.variable == null )
      {
         return true;
      }

      return ( variable.equals( mon.variable ) );
   }

   /**
    * Returns the tree node associated with this monomial.
    */
   public AbstractNode getTreeNode()
   {
      if ( variable != null )
      {
         return NodeFactory.createBinaryOperatorNode( "*",
         			NodeFactory.createConstantNode( coefficient ), 
         			NodeFactory.createBinaryOperatorNode( "^", 
         						variable,
         						NodeFactory.createConstantNode( power ) ) );
      }
      else
      {
         return NodeFactory.createConstantNode( coefficient );
      }
   }

   /**
    * Returns an indication of whether or not this monomial is a valid monomial
    * (e.g., the node it was generated from is valid).
    */
   public boolean isValid()
   {
      return valid;
   }
}
