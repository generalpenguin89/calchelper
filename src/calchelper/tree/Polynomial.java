/*
 * Polynomial
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Represents a polynomial part of an expression.
 *
 * @author Patrick MacArthur
 */

class Polynomial extends AbstractNode implements Cloneable
{
   /* This maps coefficients to powers.
    *
    * For example, the expression 2x^3 would be represented by the map {3:2}.
    */
   private Map<Double, Double> _map;
   
   // The variable that this polynomial is over
   private String _variable;
   
   /**
    * Generates an empty Polynomial.
    */
   protected Polynomial()
   {
      setMap( new HashMap<Double, Double>() );
      _variable = null;
   }
   
   /**
    * Creates a copy of the given polynomial.
    * 
    * @param poly The polynomial to copy
    */
   protected Polynomial( Polynomial poly )
   {
      setMap( poly._map );
      _variable = poly._variable;
   }

   /**
    * Builds a polynomial from the given constant.
    */
   public Polynomial( double constant )
   {
      setMap( new HashMap<Double, Double>() );
      getMap().put( 0.0, constant );
   }
   
   /**
    * Builds a polynomial from the given variable.
    */
   public Polynomial( String variable )
   {
      setMap( new HashMap<Double, Double>() );
      getMap().put( 1.0, 1.0 );
      _variable = variable;
   }
   
   public static Polynomial createPolynomial( AbstractNode node )
   {
      if ( node instanceof Polynomial )
      {
         return new Polynomial( ( Polynomial ) node );
      }
      else
      {
         Polynomial poly = new Polynomial();
         
         if ( node.hasValue() )
         {
            // Base case 1
            poly.getMap().put( 0.0, node.getValue() );
         }
         else if ( node.isSimpleVariable() )
         {
            // Base case 2
            poly.getMap().put( 1.0, 1.0 );
            poly._variable = node.toString();
         }
         else if ( node instanceof BinaryOperatorNode )
         {
            BinaryOperatorNode binNode = ( BinaryOperatorNode ) node;
            
            if ( node instanceof BinaryOperatorNode.Addition )
            {
               for ( int x = 0; x < binNode.nodeCount(); ++x )
               {
                  poly.merge( createPolynomial( binNode.getNode( x ) ), 1 );
               }
            }
            else if ( node instanceof BinaryOperatorNode.Subtraction )
            {
               poly.merge( createPolynomial( binNode.getLeft() ), 1 );
               poly.merge( createPolynomial( binNode.getRight() ), -1 );
            }
            else if ( node instanceof BinaryOperatorNode.Multiplication )
            {
               Polynomial left = createPolynomial( binNode.getLeft() );
               Polynomial right = createPolynomial( binNode.getRight() );
               
               // Give up if they're not both valid or the variables don't
               // match
               if ( left != null && right != null 
                        && left.isSameVariable( right ) )
               {
                  poly._variable = left.getVariable();
                  if ( poly._variable == null ) poly._variable = right.getVariable();
                  for ( Map.Entry<Double, Double> entry : left.getMap().entrySet() )
                  {
                     for ( Map.Entry<Double, Double> other : 
                              right.getMap().entrySet() )
                     {
                        poly.merge( entry.getKey() + other.getKey(), 
                                 entry.getValue() * other.getValue() );
                     }
                  }
               }
               else
               {
                  return null;
               }
            }
            else if ( node instanceof BinaryOperatorNode.Division )
            {
               Polynomial left = createPolynomial( binNode.getLeft() );
               Polynomial right = createPolynomial( binNode.getRight() );
               
               // Give up if they're not both valid or the variables don't
               // match.  Also, denominator must have only 1 term.
               if ( left != null && right != null &&
                        left.isSameVariable( right ) && 
                        right.termCount() == 1 )
               {
                  poly._variable = left.getVariable();
                  if ( poly._variable == null ) poly._variable = right.getVariable();
                  for ( Map.Entry<Double, Double> entry : left.getMap().entrySet() )
                  {
                     for ( Map.Entry<Double, Double> other :
                              right.getMap().entrySet() )
                     {
                        poly.merge( entry.getKey() - other.getKey(), 
                                 entry.getValue() / other.getValue() );
                     }
                  }
               }
               else
               {
                  return null;
               }
            }
            else if ( node instanceof BinaryOperatorNode.Power )
            {
               Polynomial left = createPolynomial( binNode.getLeft() );
               poly._variable = left.getVariable();
                              
               // Give up if they're not both valid or the variables don't
               // match.  Also, right side must be a constant.
               if ( left != null && binNode.getRight().hasValue() )
               {
                  double right = binNode.getRight().getValue();
                  
                  for ( Map.Entry<Double, Double> entry : left.getMap().entrySet() )
                  {
                      poly.merge( entry.getKey() * right,
                             Math.pow( entry.getValue(), right ) );
                  }
               }
               else
               {
                  return null;
               }
            }
            else
            {
               return null;
            }
         }
         else
         {
            return null;
         }
         
         poly.simplify();
         return poly;
      }
   }
   
   /**
    * Clones a Polynomial object.
    * 
    * @return A clone of this object.
    */
   public Object clone() throws CloneNotSupportedException
   {
      Polynomial clone = ( Polynomial ) super.clone();

      // Copy map
      clone.setMap( new HashMap<Double, Double>() );
      clone.getMap().putAll( getMap() );
      
      // Variable name is fine since String is immutable
      // boolean is a primitive type so it's fine
      return clone;
   }
   
   /**
    * Merges specified term into the existing polynomial.
    * 
    * @param power The power of the term to merge into the polynomial.
    * @param newCoefficient The coefficient of the term to merge into the polynomial.
    * @param variable The variable of the term to merge into the coefficient.
    */
   private void merge( double power, double newCoefficient )
   {
      //FIXME: Documentation comment is wrong
      if ( getMap().containsKey( power ) )
      {
         getMap().put( power, getCoefficient( power ) + newCoefficient );
      }
      else
      {
         getMap().put( power, newCoefficient );
      }
   }
   
   /**
    * Gets the coefficient for the specified power.
    * 
    * @param power The power to find the coefficient for.
    * @return The power for the specified coefficient.
    */
   public double getCoefficient( double power )
   {
      return getMap().get( power );
   }
   
   /**
    * Merges a polynomial with this one by multiplying coefficients.
    * 
    * @param poly
    *           The polynomial to merge.
    * @param constant
    *           A constant to multiply by. This is used so that addition and
    *           subtraction can be handled with a single method.
    */
   private void merge( Polynomial poly, double constant )
   {
      if ( this.getVariable() == null )
      {
         this._variable = poly.getVariable();
      }
      // TODO: Add error-checking
      
      if ( poly != null )
      {
         for ( Map.Entry<Double, Double> entry : poly.getMap().entrySet() )
         {
            merge( entry.getKey(), entry.getValue() * constant );
         }
      }
   }
   
   /**
    * Joins an ArrayList together to form a String.
    * 
    * @param strList A list of strings to join together.
    * @param delimiter A delimiter to insert between each string.
    * @return The string created from the ArrayList
    */
   protected static String join( ArrayList<String> strList, String delimiter )
   {
      StringBuffer buffer = new StringBuffer();
      Iterator<String> iter = strList.iterator();
      while ( iter.hasNext() )
      {
         buffer.append( iter.next() );
         if ( iter.hasNext() )
         {
            buffer.append( delimiter );
         }
      }
      return buffer.toString();
   }
   
   /**
    * Gets a string representation of the polynomial.
    */
   public String getStringValue()
   {
         if ( termCount() == 0 )
         {
            return "0";
         }
         
         ArrayList<String> strList = new ArrayList<String>();
         for ( Map.Entry<Double, Double> entry : getMap().entrySet() )
         {
            if ( entry.getKey() == 0.0 )
            {
               strList.add( String.valueOf( entry.getValue() ) );
            }
            else
            {
               StringBuilder builder = new StringBuilder();
               if ( entry.getValue() != 1.0 )
               {
                  builder.append( entry.getValue() );
               }
               builder.append( getVariable() );
               if ( entry.getKey() != 1.0 )
               {
                  builder.append( "^" + entry.getKey() );
               }
               strList.add( builder.toString() );
            }
         }
         return join( strList, " + " );
   }
   
   /**
    * Gets a string representation designed to fit into the tree.
    */
   public String toString()
   {
      return this.getStringValue();
   }

   // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   /**
    * Integrate - By Jake Schwartz
    */
   public AbstractNode integrate()
   {
      // Make a new polynomial
      Polynomial integral = new Polynomial();
      integral._variable = _variable;
      
      // Test to see if the polynomial has a x^-1
      if( _map.containsKey( -1.0 ) )
      {
         // Make a natural log function
         AbstractNode natural = NodeFactory.createNode( "ln", new Polynomial( getMap().get( -1.0 )),
                  new Polynomial( _variable ) );
         
         // Remove the entry with the key of -1
         _map.remove( -1d );
         
         // For each entry in the map integrate that piece
         for ( Map.Entry<Double, Double> entry : getMap().entrySet() )
         {
            // Put a new entry in (this represents each monomial being integrated)
            integral.getMap().put( entry.getKey() + 1d, entry.getValue()
                     * ( 1d / ( entry.getKey() + 1 ) ) );
         }
         
         //Make addition node and add its children
         AbstractNode plus = NodeFactory.createNode( "+", integral, natural );

         //Return the addition node
         return plus;
      }
      else
      {
         // For each entry in the map integrate that piece
         for ( Map.Entry<Double, Double> entry : getMap().entrySet() )
         {
            // Put a new entry in (this represents each monomial being integrated)
            integral.getMap().put( entry.getKey() + 1d, entry.getValue()
                  * ( 1d / ( entry.getKey() + 1 ) ) );
         }
      
         return integral;
      }
   }
   /**
    * Derive - 
    * By Ben Decato
    */
   // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   
   public AbstractNode derive()
   {
      // Make a new polynomial
      Polynomial derivative = new Polynomial();
      derivative._variable = _variable;
      
      // For each entry in the copy
      for ( Map.Entry<Double, Double> entry : getMap().entrySet() )
      {
         if ( entry.getKey() != 0 )
         {
            // Put a new entry in (this represents each monomial being
            // integrated)
            double exp = entry.getKey();
            double co = entry.getValue();
            derivative.getMap().put( exp - 1d, co * exp );
         }
      }
      
      return derivative;
   }
   
   /**
    * Indicates whether some other object is "equal to" this Polynomial.
    * 
    * @return true if this object is the same as the obj argument, false otherwise
    */
   public boolean equals( Object obj )
   {
      if ( obj instanceof Polynomial )
      {
         Polynomial poly = createPolynomial( ( AbstractNode ) obj );
         
         if ( isSameVariable( poly ) )
         {
            return getMap().equals( poly.getMap() );
         }
      }
      
      return false;
   }
   
   /**
    * Indicates whether some other object is "equal to" this Polynomial, ignoring coefficients.
    * 
    * @return true if this object is the same as the obj argument, false otherwise
    */
   public boolean equalsIgnoreCoefficients( Object obj )
   {
      if ( obj instanceof Polynomial )
      {
         Polynomial poly = createPolynomial( ( AbstractNode ) obj );
         
         if ( isSameVariable( poly ) )
         {
            for ( Entry<Double, Double> entry : getMap().entrySet() )
            {
               if ( ! poly.getMap().containsKey( entry.getKey() ) )
               {
                  return false;
               }
            }
            
            for ( Entry<Double, Double> entry : poly.getMap().entrySet() )
            {
               if ( ! getMap().containsKey( entry.getKey() ) )
               {
                  return false;
               }
            }
            
            return true;
         }
      }
      
      return false;
   }
   
   /**
    * Returns a hash code value for the polynomial.
    * 
    * The hash code of all invalid polynomials is 0, which ensures that two
    * invalid polynomials will have the same hash code. For all valid
    * polynomials, the hash code is determined by multiplying the hash codes of
    * the polynomial map and the variable name.
    * 
    * @return a hash code value for this object
    */
   public int hashCode()
   {
         // Very crude way of determining the hashCode
         if ( _variable != null )
         {
            return getMap().hashCode() * _variable.hashCode();
         }
         else
         {
            return getMap().hashCode();
         }
   }

   /**
    * Gets the variable for this polynomial.
    * 
    * @return the variable that this polynomial is over
    */
   public String getVariable()
   {
      return _variable;
   }
   
   /**
    * Returns the number of terms in this polynomial.
    */
   public int termCount()
   {
      return getMap().size();
   }
   
   /**
    * Returns true if the variable is the same.
    * 
    */
   public boolean isSameVariable( Polynomial other )
   {
      return this.getVariable() == null || other.getVariable() == null ||
             this.getVariable().equals( other.getVariable() );
   }
   
   /**
    * Determines whether or not the polynomial has a value.
    */
   public boolean hasValue()
   {
      if ( termCount() > 1 )
      {
         return false;
      }
      else
      {
         return getMap().containsKey( 0.0 );
      }
   }
   
   /**
    * Returns the polynomial's value if any.
    */
  public double getValue()
  {
     if ( termCount() > 1 )
     {
        return 0.0;
     }
     else
     {
        return getMap().get( 0.0 );
     }
  }
  
  /**
   * Determines whether or not the polynomial is a simple variable, e.g. no
   * coefficients or powers.
   */
  public boolean isSimpleVariable()
  {
     for ( Entry<Double, Double> entry : getMap().entrySet() )
     {
        if ( entry.getKey() != 1.0 || entry.getValue() != 1.0 )
        {
           return false;
        }
     }
     
     return true;
  }

   /**
    * Sets the HashMap for this Polynomial.
    * @param map the map to set
    */
   protected void setMap( Map<Double, Double> map )
   {
      this._map = map;
   }
   
   /**
    * Gets the Map for this Polynomial.
    *  
    * @return the map
    */
   protected Map<Double, Double> getMap()
   {
      return _map;
   }
   
   /**
    * Returns the additive inverse.
    */
   public AbstractNode inverse()
   {
      Polynomial copy = createPolynomial( this );
      for ( Map.Entry<Double, Double> entry : copy.getMap().entrySet() )
      {
         entry.setValue( entry.getValue() * -1.0 );
      }
      return copy;
   }
   
   /**
    * Adds a polynomial to this one.
    */
   public Polynomial add( Polynomial other )
   {
      Polynomial poly = createPolynomial( this );
      poly.merge( other, 1.0 );
      return poly;
   }
   
   /**
    * Simplifies a polynomial.
    */
   public void simplify()
   {
      Map<Double, Double> copy = new HashMap<Double, Double>();
      for ( Map.Entry<Double, Double> entry : this.getMap().entrySet() )
      {
         if ( entry.getValue() != 0.0 )
         {
            copy.put( entry.getKey(), entry.getValue() );
         }
      }
      setMap( copy );
   }

   /**
    * Determines the precedence level of the node.
    */
   protected int precedence()
   {
      if ( this.hasValue() )
      {
         return 100;
      }
      else if ( this.termCount() == 1 )
      {
         return 15;
      }
      else
      {
         return 10;
      }
   }
}