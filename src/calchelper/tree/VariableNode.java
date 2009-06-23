/**
 * VariableNode.java
 *
 * Class that represents a variable in an expression.
 *
 * @author Patrick MacArthur
 *  
 * Based on code written for CS416 Programming Assignment #9
 */

package calchelper.tree;

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
      return getName();
   }
   
   /**
    * Returns the name of the variable.
    */
   public String getName()
   {
      return _name;
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
      else if ( ! ( obj instanceof VariableNode ) )
      {
         return false;
      }
      else
      {
         VariableNode node = ( VariableNode  ) obj;
         if ( node._name.equals( this._name ) )
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
      return _name.hashCode();
   }
   
   
   //-----------------------------------------
   /** Integrate - Should only be called if there is a Variable alone
     * By Jake Schwartz
     */
   public void integrate( )
   {
      //Make constant node to represent exponent
      ConstantNode exp = new ConstantNode( 1 );
      
      //Make power operator
      BinaryOperatorNode.Power pow = new BinaryOperatorNode.Power( this, exp );
      
      pow.integrate();
      
      //rorateLeft( "*" )
      //rotateRight( "^" )
   }
   //-----------------------------------------
}
