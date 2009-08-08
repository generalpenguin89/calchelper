/*
 * Polynomial
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
   private HashMap<Double, Double> _map;
   
   // The variable that this polynomial is over
   private String _variable;
   
   // Is this polynomial valid?
   private boolean _isValid;
   
   /**
    * Generates an empty Polynomial.
    */
   protected Polynomial()
   {
      setMap( new HashMap<Double, Double>() );
      _variable = null;
      _isValid = true;
   }
   
   /**
    * Constructs a polynomial from the given node.
    *
    * @param node The node to convert to a polynomial.
    */
   public Polynomial( AbstractNode node )
   {
      // TODO: Add proper error-checking
      
      if ( node instanceof Polynomial )
      {
         setMap( ( ( Polynomial ) node ).getMap() );
         _variable = ( ( Polynomial ) node )._variable;
         _isValid = ( ( Polynomial ) node )._isValid;
      }
      else
      {
         setMap( new HashMap<Double, Double>() );
         _isValid = true;
         
         if ( node.hasValue() )
         {
            // Base case 1
            getMap().put( 0.0, node.getValue() );
         }
         else if ( node.isSimpleVariable() )
         {
            // Base case 2
            getMap().put( 1.0, 1.0 );
            _variable = node.toString();
         }
         else if ( node instanceof BinaryOperatorNode )
         {
            BinaryOperatorNode binNode = ( BinaryOperatorNode ) node;
            
            if ( node instanceof BinaryOperatorNode.Addition )
            {
               for ( int x = 0; x < binNode.nodeCount(); ++x )
               {
                  merge( new Polynomial( binNode.getNode( x ) ), 1 );
               }
            }
            else if ( node instanceof BinaryOperatorNode.Subtraction )
            {
               merge( new Polynomial( binNode.getLeft() ), 1 );
               merge( new Polynomial( binNode.getRight() ), -1 );
            }
            else if ( node instanceof BinaryOperatorNode.Multiplication )
            {
               Polynomial left = new Polynomial( binNode.getLeft() );
               Polynomial right = new Polynomial( binNode.getRight() );
               
               // Give up if they're not both valid or the variables don't
               // match
               if ( left.isValid() && right.isValid() &&
                        left.isSameVariable( right ) )
               {
                  this._variable = left.getVariable();
                  if ( this._variable == null ) this._variable = right.getVariable();
                  for ( Map.Entry<Double, Double> entry : left.getMap().entrySet() )
                  {
                     for ( Map.Entry<Double, Double> other : 
                              right.getMap().entrySet() )
                     {
                        merge( entry.getKey() + other.getKey(), 
                                 entry.getValue() * other.getValue() );
                     }
                  }
               }
               else
               {
                  _isValid = false;
               }
            }
            else if ( node instanceof BinaryOperatorNode.Division )
            {
               Polynomial left = new Polynomial( binNode.getLeft() );
               Polynomial right = new Polynomial( binNode.getRight() );
               
               // Give up if they're not both valid or the variables don't
               // match.  Also, denominator must have only 1 term.
               if ( left.isValid() && right.isValid() &&
                        left.isSameVariable( right ) && 
                        right.termCount() == 1 )
               {
                  this._variable = left.getVariable();
                  if ( this._variable == null ) this._variable = right.getVariable();
                  for ( Map.Entry<Double, Double> entry : left.getMap().entrySet() )
                  {
                     for ( Map.Entry<Double, Double> other :
                              right.getMap().entrySet() )
                     {
                        merge( entry.getKey() - other.getKey(), 
                                 entry.getValue() / other.getValue() );
                     }
                  }
               }
               else
               {
                  _isValid = false;
               }
            }
            else if ( node instanceof BinaryOperatorNode.Power )
            {
               Polynomial left = new Polynomial( binNode.getLeft() );
               this._variable = left.getVariable();
                              
               // Give up if they're not both valid or the variables don't
               // match.  Also, right side must be a constant.
               if ( left.isValid() && binNode.getRight().hasValue() )
               {
                  double right = binNode.getRight().getValue();
                  
                  for ( Map.Entry<Double, Double> entry : left.getMap().entrySet() )
                  {
                      merge( entry.getKey() * right,
                             Math.pow( entry.getValue(), right ) );
                  }
               }
               else
               {
                  _isValid = false;
               }
            }
            else
            {
               _isValid = false;
            }
         }
         else
         {
            _isValid = false;
         }
         
         if ( _isValid )
         {
            simplify();
         }
      }
   }

   /**
    * Builds a polynomial from the given constant.
    */
   public Polynomial( double constant )
   {
      setMap( new HashMap<Double, Double>() );
      getMap().put( 0.0, constant );
      _isValid = true;
   }
   
   /**
    * Builds a polynomial from the given variable.
    */
   public Polynomial( String variable )
   {
      setMap( new HashMap<Double, Double>() );
      getMap().put( 1.0, 1.0 );
      _isValid = true;
      _variable = variable;
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
      
      for ( Map.Entry<Double, Double> entry : poly.getMap().entrySet() )
      {
         merge( entry.getKey(), entry.getValue() * constant );
      }
   }
   
   /**
    * Returns true if this polynomial is valid.
    */
   public boolean isValid()
   {
      return _isValid;
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
      if ( isValid() )
      {
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
      else
      {
         return "<Invalid Polynomial>";
      }
   }
   
   /**
    * Gets a string representation designed to fit into the tree.
    */
   public String toString()
   {
      return this.getStringValue();
   }
   
   public static void intTest( String infix ) throws ExpressionException
   {
      TreeFactory factory;
      ExpressionTree tree;
      AbstractNode treeRoot;
      Polynomial poly;
      
      System.out.println( "Int test with expression: " + infix );
      factory = new TreeFactory( infix );
      tree = factory.buildTree();
      treeRoot = tree.getRoot();
      poly = new Polynomial( treeRoot );
      poly.integrate();
      System.out.println( "Polynomial map: " + poly.getMap() );
      System.out.println();
   }
   
   public static void main( String[] args ) throws ExpressionException
   {
      // Integration tests
      System.out.println( "------ Group 5: Integration tests -----" );
      intTest( "( 2 * x )" );
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
         // Put a new entry in (this represents each monomial being integrated)
         double exp = entry.getKey();
         double co = entry.getValue();
         derivative.getMap().put( exp - 1d, co * exp );
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
         Polynomial poly = new Polynomial( ( AbstractNode ) obj );
         
         // If both are invalid, then they're equal as far as I'm concerned
         if ( ! isValid() && ! poly.isValid() )
         {
            return true;
         } 
         else if ( isSameVariable( poly ) )
         {
            return getMap().equals( poly.getMap() );
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
      if ( ! isValid() )
      {
         return 0;
      }
      else
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
      if ( isValid() )
      {
         return getMap().size();
      }
      else
      {
         return 0;
      }
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
   protected void setMap( HashMap<Double, Double> map )
   {
      this._map = map;
   }
   
   /**
    * Gets the HashMap for this Polynomial.
    *  
    * @return the map
    */
   protected HashMap<Double, Double> getMap()
   {
      return _map;
   }
   
   /**
    * Returns the additive inverse.
    */
   public AbstractNode inverse()
   {
      Polynomial copy = new Polynomial( this );
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
      Polynomial poly = new Polynomial( this );
      poly.merge( other, 1.0 );
      return poly;
   }
   
   /**
    * Simplifies a polynomial.
    */
   public void simplify()
   {
      HashMap<Double, Double> copy = new HashMap<Double, Double>();
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