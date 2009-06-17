/**
 * ConstantNode.java
 *
 * Class that represents a constant in an expression.
 *
 * @author Patrick MacArthur
 *
 * Based on code written for CS416 Programming Assignment #9
 */

public class ConstantNode extends OperandNode
{
   double _value;

   /**
    * Generates a node.
    */
   public ConstantNode( double val )
   {
      _value = val;
   }

   /**
    * Determines whether or not this operand node has a value.
    */
   public boolean hasValue()
   {
      return true;
   }

   /**
    * Returns a double representation of the value.
    */
   public double getValue()
   {
      return _value;
   }

   /**
    * Returns a string representation of the value.
    */
   public String getStringValue()
   {
      return Double.valueOf( _value ).toString();
   }
}
