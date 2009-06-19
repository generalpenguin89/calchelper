
class Monomial
{
   private boolean valid;
   double coefficient;
   String variable;
   double power;

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

      System.out.println( coefficient + " x " + power + " valid: " + valid );
   }

   public BinaryOperatorNode getTreeNode()
   {
      return NodeFactory.createBinaryOperatorNode( "*",
                new ConstantNode( coefficient ),
                NodeFactory.createBinaryOperatorNode( "^",
                   new VariableNode( variable ),
                   new ConstantNode( power ) ) );
   }

   public boolean isValid()
   {
      return valid;
   }
}
