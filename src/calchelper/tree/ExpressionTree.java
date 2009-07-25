/**
 * ExpressionTree.java
 *
 * A tree that represents a mathematical expression.
 *
 * @author Patrick MacArthur, for CS416 Programming Assignment #9
 */

package calchelper.tree;

public class ExpressionTree
{
   // instance variables
   private AbstractNode _root;

   // Constructor
   public ExpressionTree( AbstractNode root )
   {
      _root = root;
   }

   /**
    * Simplifies the tree.
    */
   public void simplify()
   {
      Polynomial poly = new Polynomial( _root );
      if ( poly.isValid() )
      {
         _root = poly;
      }
      
      _root.simplify();
   }
   
   /**
    * Returns the root node of the tree.
    */
   AbstractNode getRoot()
   {
   	return _root;
   }

   /**
    * toString()
    *
    * Converts the tree into a human-readable string.
    *
    * @return A human-readable string.
    */
   public String toString()
   {
      return _root.toString();
   }

   /**
    * differentiate()
    *
    * Returns the derivative tree of the tree.
    * 
    * @deprecated
    */
   @Deprecated
   public ExpressionTree differentiate()
   {
      return derive();
   }
   
   /**
    * derive()
    * 
    * Returns the derivative tree of the tree.
    */
   public ExpressionTree derive()
   {
      if ( _root == null )
      {
         return null;         
      }
      else
      {
         return new ExpressionTree( _root.derive() );
      }     
   }

   /**
    * integrate()
    *
    * Returns the integral tree of the tree.
    */
   public ExpressionTree integrate()
   {
      if ( _root == null )
      {
         return null;         
      }
      else
      {
         return new ExpressionTree( _root.integrate() );
      }
   }
   
   /**
    * equals()
    * 
    * Returns true if the object represents the same tree as this one.
    */
   public boolean equals( Object obj )
   {
   	if ( ! ( obj instanceof ExpressionTree ) )
   	{
   		return false;
   	}
   	ExpressionTree tree = ( ExpressionTree ) obj;
   	
   	return this._root.equals( tree._root );
   }
   
}
