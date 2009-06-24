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

class Monomial
{
   private boolean valid; // is the monomial valid?
   double coefficient;    // the constant factor
   String variable;       // the variable factor
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
         coefficient = node.getValue();
      }
      else if ( node instanceof VariableNode )
      {
         VariableNode varNode = ( VariableNode ) node;
         String varName = varNode.getName();
         if ( variable == null || varName.equals( variable ) )
         {
            power++;
         }
         else
         {
            valid = false;
         }
      }
      else if ( node instanceof BinaryOperatorNode.Division )
      {
         BinaryOperatorNode.Division divNode =
                  ( BinaryOperatorNode.Division ) node;
         for ( int x = 0; x < divNode.nodeCount(); ++x )
         {
            Monomial mon = new Monomial( divNode.getNode( x ) );
            if ( mon.isValid() )
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
         BinaryOperatorNode.Multiplication multNode =
                  ( BinaryOperatorNode.Multiplication ) node;
         for ( int x = 0; x < multNode.nodeCount(); ++x )
         {
            Monomial mon = new Monomial( multNode.getNode( x ) );
            if ( mon.isValid() )
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
         BinaryOperatorNode.Power powNode = ( BinaryOperatorNode.Power ) node;
         Monomial mon = new Monomial( powNode.getLeft() );
         if ( mon.isValid() && powNode.getRight().hasValue() )
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
         valid = false;
      }

      ////Want really verbose meaningless output?  Uncomment this line
      //System.out.println( coefficient + " x " + power + " valid: " + valid );
   }

   /**
    * Returns the tree node associated with this monomial.
    */
   public BinaryOperatorNode getTreeNode()
   {
      return NodeFactory.createBinaryOperatorNode( "*",
                new ConstantNode( coefficient ),
                NodeFactory.createBinaryOperatorNode( "^",
                   new VariableNode( variable ),
                   new ConstantNode( power ) ) );
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
