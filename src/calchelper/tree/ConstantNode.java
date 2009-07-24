/**
 * ConstantNode.java
 *
 * Class that represents a constant in an expression.
 *
 * @author Patrick MacArthur
 *
 * Based on code written for CS416 Programming Assignment #9
 */

package calchelper.tree;

@Deprecated
public class ConstantNode extends AbstractNode
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
   
   //-----------------------------------------
   /** Integrate - This should only be called if there is a constant by itself
     * By Jake Schwartz
     */
   public AbstractNode integrate( )
   {
      //Make variable node
      AbstractNode var = NodeFactory.createVariableNode( "x" );
      
      //Make constant node
      BinaryOperatorNode.Multiplication mult = new BinaryOperatorNode.Multiplication( this, var );
      
      //rotateLeft();
      
      return mult;
   }
   //-----------------------------------------
   
   //------------------------- derive( ) ---------------------------------------
   /**
    * sets the value to zero, since that is the rule of derivation for constants
    * by William Rideout
    */
   public AbstractNode derive( )
   {
      ConstantNode cons = new ConstantNode(0);
      return cons;
   }
}
