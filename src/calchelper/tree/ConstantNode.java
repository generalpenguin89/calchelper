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

   /**
    * Indicates whether some other object is "equal to" this one. 
    */
   public boolean equals( Object obj )
   {
      if ( obj == null )
      {
         return false;
      }
      else if ( ! ( obj instanceof ConstantNode ) )
      {
         return false;
      }
      else
      {
         ConstantNode node = ( ConstantNode ) obj;
         if ( node._value == this._value )
         {
            return true;
         }
         else
         {
            return false;
         }
      }
   }

   public int hashCode()
   {
      return ( int ) _value;
   }
}
