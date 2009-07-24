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

@Deprecated
public class VariableNode extends AbstractNode
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
   public AbstractNode integrate( )
   {
      //Make constant node to represent exponent
      AbstractNode exp = NodeFactory.createConstantNode( 1 );
      
      //Make power operator
      BinaryOperatorNode.Power pow = new BinaryOperatorNode.Power( this, exp );
      
      pow.integrate();
      
      //rorateLeft( "*" )
      //rotateRight( "^" )
      
      return this;
   }
   //-----------------------------------------
   
   //------------------------- derive( ) ---------------------------------------
   /**
    * creates a new ConstantNode with a valule of 1, since that is the derivative 
    * for lone variables.
    * by William Rideout
    */
   public void /*ConstantNode*/ derive( )
   {
     AbstractNode node = NodeFactory.createConstantNode( 1 );
     
     //might want to return this later
     //return node;
   }
   
   /**
    * Determines whether the node is a simple variable (e.g., "x" 
    * standing alone) or not
    */
   public boolean isSimpleVariable()
   {
      return true;
   }
}
