/*
 * AbstractNode.java
 *  
 * This class is based on code written for UNH CS416 Programming Assignment #9,
 * Spring 2009.
 * 
 * This file is part of CalcHelper.
 * 
 * CalcHelper is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * CalcHelper is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with CalcHelper.  If not, see <http://www.gnu.org/licenses/>.
 */
 
package calchelper.tree;

import java.util.HashMap;

/**
 * A base class for all expression tree node types.
 *
 * @author Patrick MacArthur
 */

abstract class AbstractNode implements Cloneable
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
      str += getStringValue();
      str += "\n";

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
    * 
    * @return true if this node has a value
    */
   public boolean hasValue()
   {
      return false;
   }

   /**
    * Returns the value of the node.
    * 
    * @return the variable held by this node
    */
   public double getValue()
   {
      return 0;
   }
   
   /**
    * Returns a string representation of the value.
    * 
    * @return a string representation of the value
    */
   public String getStringValue()
   {
      return "";
   }
   
   /**
    * Determines whether the node is a simple variable (e.g., "x" 
    * standing alone) or not.
    * 
    * @return true if this node is a simple variable
    */
   public boolean isSimpleVariable()
   {
      return false;
   }

   /**
    * Returns the integral of this node.
    * 
    * @return The integral of this node.
    */
   public AbstractNode integrate( )
   {
      return null;
   }
   
   /**
    * Returns the derivative of this node.
    * 
    * @return the derivative of this node
    */
   public AbstractNode derive( )
   {
      return null;
   }
   
   /**
    * Clones the object.
    * 
    * @return a clone of this object
    */
   public Object clone() throws CloneNotSupportedException
   {
      return super.clone();
   }
   
   /**
    * Returns the additive inverse of this node (e.g., -1 * this)
    * 
    * @return the additive inverse of this node
    */
   public AbstractNode inverse()
   {
      return null;
   }
   
   /**
    * Determines the precedence level of the node.
    * 
    * @return the precedence level of this node
    */
   protected int precedence()
   {
      return 0;
   }
   
   /**
    * Gets the HashMap for this Node
    * 
    * @return the HashMap for this node, assuming it has one
    */
   @Deprecated
   protected HashMap<Double, Double> getMap()
   {
      return null;
   }
   
   /**
    * Tests if two expressions are equal, ignoring coefficients. Only really 
    * makes a difference for those nodes that support constant coefficients.
    * 
    * @param obj The other node to compare.
    * 
    * @return true if the two objects are equal, ignoring any constant 
    * coefficients
    */
   public boolean equalsIgnoreCoefficients( Object obj )
   {
      return equals( obj );
   }
}

