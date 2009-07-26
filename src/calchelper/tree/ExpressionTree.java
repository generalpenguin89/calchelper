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
   ExpressionTree( AbstractNode root )
   {
      setRoot( root );
   }

   /**
    * Simplifies the tree.
    */
   public void simplify()
   {
      Polynomial poly = new Polynomial( getRoot() );
      if ( poly.isValid() )
      {
         setRoot( poly );
      }
      
      getRoot().simplify();
   }
   
   /**
    * Returns the root node of the tree.
    */
   AbstractNode getRoot()
   {
   	return _root;
   }
   
   /**
    * Sets the root node of the tree.
    * 
    * @param root The new root of the tree.
    */
   protected void setRoot( AbstractNode root )
   {
      _root = root;
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
      return getRoot().toString();
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
      if ( getRoot() == null )
      {
         return null;         
      }
      else
      {
         return new ExpressionTree( getRoot().derive() );
      }     
   }

   /**
    * integrate()
    *
    * Returns the integral tree of the tree.
    */
   public ExpressionTree integrate()
   {
      if ( getRoot() == null )
      {
         return null;         
      }
      else
      {
         return new ExpressionTree( getRoot().integrate() );
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
   	
   	return this.getRoot().equals( tree.getRoot() );
   }
   
}
