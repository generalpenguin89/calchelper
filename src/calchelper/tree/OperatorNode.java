/**
 * OperatorNode.java
 *
 * An abstract class representing an operator in an expression.
 *
 * @author Patrick MacArthur
 *
 * Based on code used for CS416 Programming Assignment #9
 */

package calchelper.tree;

import java.util.ArrayList;
import java.util.Iterator;

abstract class OperatorNode extends AbstractNode
{
   protected String _type;

   protected ArrayList<AbstractNode> _children;

   /**
    * Returns the type of the node.
    */
   public String getType()
   {
      return _type;
   }

   /**
    * Returns the nth node.
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
      str += _type;
      str += "\n";

      for ( AbstractNode child : _children )
      {
         str += child.toString( depth + 1 );
      }

      return str;
   }

   /*
    * Checks the validity of the operator inside the node.
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

      System.out.println( "Operator: simplify( parent )" );

      // Recurse to each child node
      for ( int x = 0; x < _children.size(); ++x )
      {
         _children.get( x ).simplify();
         if ( _children.get( x ).hasValue() )
         {
            _children.set( x, new ConstantNode( _children.get( x ).getValue() ) );
         }
         else
         {
            Polynomial poly = new Polynomial( _children.get( x ) );
            if ( poly.isValid() )
            {
               _children.set( x, poly );
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
    * An iterator that iterates over a node and its children.
    */
   protected class TreeIterator implements Iterator<AbstractNode>
   {
      private boolean _hasDoneSelf; // have we returned the current node?
      private AbstractNode _node;   // the node that we're iterating over

      // iterator over our node's children
      private Iterator<AbstractNode> _childIt;

      /**
       * Default constructor.
       */
      public TreeIterator( AbstractNode node )
      {
         _hasDoneSelf = false;
         _node = node;
         _childIt = _children.iterator();
      }

      /**
       * Determines whether or not there is a node following the last-selected
       * node.
       *
       * @returns True if we haven't returned ourself yet or there are child
       * nodes we haven't iterated over; otherwise, false.
       */
      public boolean hasNext()
      {
         if ( _hasDoneSelf )
         {
            return _childIt.hasNext();
         }
         else
         {
            return _hasDoneSelf; // false
         }
      }

      /**
       * Returns the next node in the sequence.
       *
       * @returns The current object if we haven't returned it yet.  Otherwise,
       * iterates over the child nodes.
       */
      public AbstractNode next()
      {
         if ( _hasDoneSelf )
         {
            return _childIt.next();
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
      return new TreeIterator( this );
   }
}

