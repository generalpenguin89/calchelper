/**
 * OperandNode.java
 *
 * Abstract class that represents an operand of an expression.  Is always the
 * leaf of a tree.
 *
 * @author Patrick MacArthur
 *
 * Based on code written for CS416 Programming Assignment #9
 */

public abstract class OperandNode extends AbstractNode
{
   /**
    * Determines whether or not this operand node has a value.
    */
   public boolean hasValue()
   {
      return false;
   }

   /**
    * Returns a double representation of the value.
    */
   public double getValue()
   {
      return 0;
   }

   /**
    * Returns a string representation of the value.
    */
   public String getStringValue()
   {
      return "";
   }

   /**
    * Returns a string representing the node at the specified depth.
    */
   protected String toString( int depth )
   {
      String str = super.toString( depth );

      str += getStringValue();
      str += "\n";
      return str;
   }
}

