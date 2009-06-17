/**
 * VariableNode.java
 *
 * Class that represents a variable in an expression.
 *
 * @author Patrick MacArthur
 *  
 * Based on code written for CS416 Programming Assignment #9
 */

public class VariableNode extends OperandNode
{
   String _name;

   public VariableNode( String name )
   {
      _name = name;
   }

   /**
    * Returns a string representation of the value.
    */
   public String getStringValue()
   {
      return _name;
   }
}
