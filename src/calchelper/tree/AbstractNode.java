/**
 * AbstractNode
 *
 * A base class for all expression tree node types.
 *
 * @author Patrick MacArthur, for CS416 Programming Assignment #9
 */

package calchelper.tree;

import java.util.Iterator;

abstract class AbstractNode implements Iterable<AbstractNode>
{
   /**
    * Returns a string representing the node of the tree.  Assumes 0 depth.
    */
   public String toString()
   {
      return toString( 0 );
   }

   /**
    * Returns a string representing the node of the tree at the specified
    * depth.
    *
    * @param depth The depth of the tree.
    *
    */
   protected String toString( int depth )
   {
      String str = "";
      for ( int i = 0; i < depth; ++i )
      { 
         str += "   ";
      }

      return str;
   }

   /**
    * Simplifies the expression below this node.
    */
   public void simplify()
   {
   }

   /**
    * Determines if the node has an a value.
    */
   public boolean hasValue()
   {
      return false;
   }

   /**
    * Returns the value of the node.
    */
   public double getValue()
   {
      return 0;
   }

   /**
    * An iterator that iterates only over the current node.
    *
    * We need AbstractNode to implement Iterable though so that we can iterate
    * over any AbstractNode that we find.  Subclasses of AbstractNode that can
    * have children will need to return an Iterator that runs through its
    * children.
    */
   protected class BasicIterator implements Iterator<AbstractNode>
   {
      private boolean _hasDoneSelf; // have we returned the current node?
      private AbstractNode _node;   // the node that we're iterating over

      /**
       * Default constructor.
       */
      public BasicIterator( AbstractNode node )
      {
         _hasDoneSelf = false;
         _node = node;
      }

      /**
       * Determines whether or not there is a node following the last-selected
       * node.
       *
       * @returns True if we haven't returned ourself yet; otherwise, false.
       */
      public boolean hasNext()
      {
         return ( ! _hasDoneSelf );
      }

      /**
       * Returns the next node in the sequence.
       *
       * @returns The current object if we haven't returned it yet.  Otherwise,
       * null.
       */
      public AbstractNode next()
      {
         if ( _hasDoneSelf )
         {
            return null;
         }
         else
         {
            _hasDoneSelf = true;
            return _node;
         }
      }

      /**
       * Not supported.
       */
      public void remove()
      {
      }
   }

   /**
    * Returns an empty iterator.
    */
   public Iterator<AbstractNode> iterator()
   {
      return new BasicIterator( this );
   }
   
   /**
    * Empty method to be overriden by subclasses
    */
   public void integrate( )
   {
      
   }
   
   /**
    * Another empty method to be overridden
    */
   public void derive( )
   {
     
   }
}

