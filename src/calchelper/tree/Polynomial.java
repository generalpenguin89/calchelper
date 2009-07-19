/*
 * Polynomial
 */

package calchelper.tree;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a polynomial part of an expression.
 *
 * @author Patrick MacArthur
 */

class Polynomial extends OperandNode
{
   /* This maps coefficients to powers.
    *
    * For example, the expression 2x^3 would be represented by the map {3:2}.
    */
   HashMap<Double, Double> /* toil and trouble */ map;
   
   // The variable that this polynomial is over
   private String _variable;
   
   // Is this polynomial valid?
   private boolean _isValid;
   
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
         map = ( ( Polynomial ) node ).map;
         _variable = ( ( Polynomial ) node )._variable;
         _isValid = ( ( Polynomial ) node )._isValid;
      }
      else
      {
         map = new HashMap<Double, Double>();
         _isValid = true;
         
         if ( node.hasValue() )
         {
            // Base case 1
            map.put( 0.0, node.getValue() );
         }
         else if ( node instanceof VariableNode )
         {
            // Base case 2
            map.put( 1.0, 1.0 );
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
                        this.isSameVariable( left ) &&
                        left.isSameVariable( right ) )
               {
                  for ( Map.Entry<Double, Double> entry : left.map.entrySet() )
                  {
                     for ( Map.Entry<Double, Double> other : 
                              right.map.entrySet() )
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
                        this.isSameVariable( left ) &&
                        left.isSameVariable( right ) && 
                        right.termCount() == 1 )
               {
                  for ( Map.Entry<Double, Double> entry : left.map.entrySet() )
                  {
                     for ( Map.Entry<Double, Double> other :
                              right.map.entrySet() )
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
                              
               // Give up if they're not both valid or the variables don't
               // match.  Also, right side must be a constant.
               if ( left.isValid() && this.isSameVariable( left ) && 
                        binNode.getRight().hasValue() )
               {
                  double right = binNode.getRight().getValue();
                  
                  for ( Map.Entry<Double, Double> entry : left.map.entrySet() )
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
         
         if ( ! _isValid )
         {
            System.err.println( "WARNING: Invalid Polynomial." );
         }
      }
   }

   /**
    * Builds a polynomial from the given constant.
    */
   public Polynomial( double constant )
   {
      map = new HashMap<Double, Double>();
      map.put( 0.0, constant );
   }
   
   /**
    * Builds a polynomial from the given variable.
    */
   public Polynomial( String variable )
   {
      map = new HashMap<Double, Double>();
      map.put( 1.0, 1.0 );
      
      _variable = variable;
   }
   
   
   /**
    * Merges specified term into the existing polynomial.
    * 
    * @param power The power of the term to merge into the polynomial.
    * @param coefficient The coefficient of the term to merge into the polynomial.
    * @param variable The variable of the term to merge into the coefficient.
    */
   private void merge( double power, double coefficient )
   {
      if ( map.containsKey( power ) )
      {
         map.put( power, ( map.get( power ) ) + coefficient );
      }
      else
      {
         map.put( power, coefficient );
      }
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
      for ( Map.Entry<Double, Double> entry : poly.map.entrySet() )
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
    * Gets a string representation.
    */
   public String getStringValue()
   {
      if ( isValid() )
      {
         return map.toString();
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
      System.out.println( "Polynomial map: " + poly.map );
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
   public void integrate()
   {
      // Copy the hashtable
      HashMap<Double, Double> copy = new HashMap<Double, Double>();
      
      // For each entry in the copy
      for ( Map.Entry<Double, Double> entry : map.entrySet() )
      {
         // Put a new entry in (this represents each monomial being integrated)
         copy.put( entry.getKey() + 1d, entry.getValue()
                  * ( 1d / ( entry.getKey() + 1 ) ) );
      }
      
      // Replace map with copy
      map = copy;
   }
   
   // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   
   public void derive()
   {
      // copy hash table
      HashMap<Double, Double> copy = new HashMap<Double, Double>();
      
      // For each entry in the copy
      for ( Map.Entry<Double, Double> entry : map.entrySet() )
      {
         // Put a new entry in (this represents each monomial being integrated)
         double exp = entry.getKey();
         double co = entry.getValue();
         copy.put( exp - 1d, co * exp );
      }
      
      // Replace map with copy
      map = copy;
   }
   
   /**
    * Indicates whether some other object is "equal to" this Polynomial.
    * 
    * @return true if this object is the same as the obj argument, false otherwise
    */
   public boolean equals( Object obj )
   {
      if ( !( obj instanceof Polynomial ) )
      {
         return false;
      }
      else
      {
         Polynomial poly = ( Polynomial ) obj;
         
         // If both are invalid, then they're equal as far as I'm concerned
         if ( ! isValid() && ! poly.isValid() )
         {
            return true;
         }
         
         if ( isSameVariable( poly ) )
         {
            return map.equals( poly.map );
         }
         else
         {
            return false;
         }
      }
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
            return map.hashCode() * _variable.hashCode();
         }
         else
         {
            return map.hashCode();
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
         return map.size();
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
         return map.containsKey( 0.0 );
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
        return map.get( 0.0 );
     }
  }
}