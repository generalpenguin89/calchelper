/*
 * ExpressionTree.java
 *
 * Based on code written for UNH CS416 Programming Assignment #9, Spring 2009.
 */

package calchelper.tree;

/**
 * A tree that represents a mathematical expression.
 * 
 * @author Patrick MacArthur
 *
 */
public class ExpressionTree
{
   // instance variables
   private AbstractNode _root;

   /**
    * Generates an ExpressionTree rooted at the given AbstractNode.
    * 
    * @param root The root of the tree.
    */
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
    *
    * @return the root node of the expression tree.
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
      return getRoot().getStringValue();
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
    * Returns the derivative tree of the tree.
    * 
    * @return An expression tree containing the derivative of this expression.
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
    * Returns the integral tree of the tree.
    * 
    * @return An expression tree containing the integral of this expression.
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
    * Returns true if the object represents the same tree as this one.
    * 
    * @return True if the object represents the same tree as this object.
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
