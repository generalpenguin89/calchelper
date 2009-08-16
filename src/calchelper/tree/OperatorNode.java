/*
 * OperatorNode.java
 *
 * Based on code written for UNH CS416 Programming Assignment #9, Spring 2009.
 */

package calchelper.tree;

import java.util.ArrayList;

/**
 * An abstract class representing an operator in an expression.
 *
 * @author Patrick MacArthur
 *
 */
abstract class OperatorNode extends AbstractNode implements Cloneable
{
   protected String _type;

   protected ArrayList<AbstractNode> _children;

   /**
    * Returns the type of the node.
    * 
    * @return A string containing the type of this node.
    */
   public String getType()
   {
      return _type;
   }
   
   /**
    * Returns the string value of this node.  Does not recurse to child nodes.
    * 
    * @return A string containing the type of this node.
    */
   public String getStringValue()
   {
      return _type;
   }

   /**
    * Returns the nth node.
    * 
    * @param index The index of the child node within this node. 
    * @return The child node contained at the specific index of this node.
    */
   public AbstractNode getNode( int index )
   {
      return _children.get( index );
   }

   /**
    * Returns the node count.
    */
   public int nodeCount()
   {
      return 0;
   }

   /**
    * Returns a string representing the node at the specified depth.
    */
   protected String toString( int depth )
   {
      String str = super.toString( depth );

      for ( AbstractNode child : _children )
      {
         str += child.toString( depth + 1 );
      }

      return str;
   }

   /**
    * Checks the validity of the operator inside the node.
    * 
    * @return True if this node is valid.
    */
   protected boolean isValid()
   {
      return false;
   }

   /**
    * Simplifies the expression below this node.
    */
   public void simplify()
   {
      // There's no real reason for this, but adding it just in case
      super.simplify();

      // Recurse to each child node
      for ( int x = 0; x < _children.size(); ++x )
      {
         if ( _children.get( x ).hasValue() )
         {
            _children.set( x, NodeFactory.createConstantNode( 
            			_children.get( x ).getValue() ) );
         }
         else
         {
            Polynomial poly = new Polynomial( _children.get( x ) );
            if ( poly.isValid() )
            {
               _children.set( x, poly );
            }
            else
            {
               _children.get( x ).simplify();
            }
         }
      }
   }

   /**
    * Determines whether or not the node has a value.
    */
   public boolean hasValue()
   {
      // Recurse to each child node
      for ( AbstractNode child : _children )
      {
         if ( ! child.hasValue() )
         {
            return false;
         }
      }

      return true;
   }

   /**
    * Returns true if the object represents the same subtree as this object.
    */
   public boolean equals( Object obj )
   {
   	if ( ! ( obj instanceof OperatorNode ) )
   	{
   		System.err.println( "not opnode" );
   		return false;
   	}
   	OperatorNode opNode = ( OperatorNode ) obj;
   	
   	if ( this.nodeCount() != opNode.nodeCount() )
   	{
   		System.err.println( "nodecount not same" );
   		return false;
   	}
   	
   	for ( int x = 0; x < this.nodeCount(); ++x )
   	{
   		if ( ! this._children.get( x ).equals( opNode._children.get( x ) ) )
   		{
   			System.err.print( "operand " + x + " not equal: " );
   			System.err.println( this._children.get( x ) + 
   					" " + opNode._children.get( x ) );
   			return false;
   		}
   	}
   	
   	// Must be equal
   	return true;
   }
   
   /**
    * Clones the object.
    * 
    */
   public Object clone() throws CloneNotSupportedException
   {
      OperatorNode clone = ( OperatorNode ) super.clone();
      clone._children = new ArrayList<AbstractNode>();
      clone._children.addAll( this._children );
      return clone;
   }
   
   
   /**
    * 
    */
   protected String getChildStringValue( AbstractNode child )
   {
      if ( child.precedence() < this.precedence() )
      {
         StringBuilder sb = new StringBuilder();
         sb.append( "(" );
         sb.append( child.getStringValue() );
         sb.append( ")" );
         return sb.toString();
      }
      else
      {
         return child.getStringValue();
      }
   }
}
