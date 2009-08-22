/*
 * TrigOperatorNode.java
 */

package calchelper.tree;

import java.util.ArrayList;
//import java.util.Map;
import java.util.Collection;
import java.util.Set;

/**
 * A node type to handle trigonometric functions with a coefficient term in 
 * front.  For example, it can hold 2x * tan( x^2 ) in one node.
 * 
 * @author Patrick MacArthur
 * @author Jake Schwartz
 * @author Ben Decato
 */
abstract class TrigOperatorNode extends OperatorNode
{  
   /**
    * Implementation notes:
    *
    * _children.get( 0 ) : left node / coefficient
    * _children.get( 1 ) : right node / argument
    */

   protected void init( String funct, AbstractNode left,
            AbstractNode right, AbstractNode farRight )
   {
      _type = funct;
      _children = new ArrayList<AbstractNode>();
      _children.add( left );
      _children.add( right );
      _children.add( farRight );    
   }
   
   /**
    * Gets the coefficient term, e.g., the typically polynomial term that
    * this function is multiplied by.
    * 
    * @return the coefficient term
    */
   public AbstractNode getCoefficientTerm()
   {
      return getNode( 0 );
   }
   
   /**
    * Gets the argument term, e.g., the term to which this function is
    * applied.
    * 
    * @return the argument term
    */
   public AbstractNode getArgument()
   {
      return getNode( 1 );
   }

   /**
    * Returns the number in parenthesis:
    * 
    * sin^(2)x
    * 
    * @return the exponent of the trigonometric expression
    */
   public AbstractNode getPower()
   {
      return getNode( 2 );
   }
   
   /**
    * Returns the additive inverse of the node.
    */
   public AbstractNode inverse()
   {
      TrigOperatorNode copy = ( TrigOperatorNode ) NodeFactory.createNode( 
               _type, getCoefficientTerm(), getArgument() );
      copy.setCoefficientTerm( NodeFactory.createNode( "*", 
               NodeFactory.createConstantNode( -1.0 ), copy.getCoefficientTerm() ) );
      copy.getCoefficientTerm().simplify();
      return copy;
   }
   
   /**
    * Node to represent sine nodes.
    *    
    * @author Patrick MacArthur
    * @author Jake Schwartz
    * @author William Rideout
    */
   public static class Sine extends TrigOperatorNode
   {
      public Sine( AbstractNode coefficient,
               AbstractNode argument, AbstractNode power )
      {
         init( "sin", coefficient, argument, power );
      }
      
      public AbstractNode derive()
      {
         AbstractNode cos = null;
            
         AbstractNode coef = getCoefficientTerm();
         AbstractNode arg = getArgument();
         
         //multiplication segment
         AbstractNode mult = NodeFactory.createNode("*", coef, arg.derive());
         
         //the cosine node... derivative of sine
         cos = NodeFactory.createNode("cos", mult, arg);
            
         cos.simplify();
         return cos;
      }

      public AbstractNode integrate()
      {
         if( isDerivative() )
         {
            // Make a node to represent the new coefficient
            Polynomial poly = (Polynomial)getArgument();
            if( poly.termCount() == 1)
            {
               //Get the key
               Set<Double> keys = poly.getMap().keySet();
               Object keyArray[] = keys.toArray();

               //Get the value
               Collection<Double> values = poly.getMap().values();
               Object valuesArray[] = values.toArray();

               AbstractNode coef = NodeFactory.createConstantNode( (Double)valuesArray[0] / (Double)keyArray[0] );
               AbstractNode cos = NodeFactory.createNode( "cos", coef, getArgument() );
               AbstractNode invCos = cos.inverse();
               return invCos;
            }
            throw new UnsupportedOperationException( this.getStringValue() + "cannot be integrated" );
         }
         throw new UnsupportedOperationException( this.getStringValue() + "cannot be integrated" );
      }
   }
   
   /**
    * Node to represent cosine function.
    * 
    * @author Patrick MacArthur
    * @author Jake Schwartz
    * @author William Rideout
    */
   public static class Cosine extends TrigOperatorNode
   {
      public Cosine( AbstractNode coefficient,
               AbstractNode argument, AbstractNode power )
      {
         init( "cos", coefficient, argument, power );
      }
      
      public AbstractNode derive()
      {
         AbstractNode sin = null;
         
         //save the coef and arg
         AbstractNode coef = getCoefficientTerm();
         AbstractNode arg = getArgument();
         
         AbstractNode mult = NodeFactory.createNode("*",coef, arg.derive());
         
         //the sine node... we might want this to be labled with an '-'
         sin = NodeFactory.createNode("sin", mult, arg).inverse();
         
         sin.simplify();
         return sin;
      }

      public AbstractNode integrate()
      {
         if( isDerivative() )
         {
            // Make a node to represent the new coefficient
            Polynomial poly = (Polynomial)getArgument();
            if( poly.termCount() == 1)
            {               
               //Get the key
               Set<Double> keys = poly.getMap().keySet();
               Object keyArray[] = keys.toArray();

               //Get the value
               Collection<Double> values = poly.getMap().values();
               Object valuesArray[] = values.toArray();

               AbstractNode coef = NodeFactory.createConstantNode( (Double)valuesArray[0] / (Double)keyArray[0] );
               AbstractNode sin = NodeFactory.createNode( "sin", coef, getArgument() );
               return sin;
            }
            throw new UnsupportedOperationException( this.getStringValue() + "cannot be integrated" );
         }
         throw new UnsupportedOperationException( this.getStringValue() + "cannot be integrated" );
      }
   }
   
   /**
    * Node to represent tangent function.
    * 
    * @author Ben Decato
    *
    */
   public static class Tangent extends TrigOperatorNode
   {
      public Tangent( AbstractNode coefficient,
               AbstractNode argument, AbstractNode power )
      {
         init( "tan", coefficient, argument, power );
      }
      
      public AbstractNode derive()
      {
         AbstractNode sec = null;
         //save the coefficient and argument
         AbstractNode coef = getCoefficientTerm();
         AbstractNode arg = getArgument();
         
         AbstractNode mult = NodeFactory.createNode( "*",coef, arg.derive() );
         
         //the tangent node
         sec = NodeFactory.createTrigNode( "sec", mult, arg,
                  NodeFactory.createConstantNode(2.0) );
         
         sec.simplify();
         return sec;
      }

      public AbstractNode integrate()
      {
         // FIXME Unfinished
         if( isDerivative() )
         {
            // Make a node to represent the new coefficient
            //AbstractNode coef = NodeFactory.createConstantNode(  )
            //AbstractNode sin = NodeFactory.createNode( "sin", coef, getArgument() );
            throw new UnsupportedOperationException( this.getStringValue() + "cannot be integrated" );
         }
         throw new UnsupportedOperationException( this.getStringValue() + "cannot be integrated" );
      }
   }
   
   /**
    * Node to represent cotangent function.
    * 
    * @author Ben Decato
    *
    */
   public static class Cotangent extends TrigOperatorNode
   {
      public Cotangent( AbstractNode coefficient,
               AbstractNode argument, AbstractNode power )
      {
         init( "cot", coefficient, argument, power );
      }
      
      public AbstractNode derive()
      {
         AbstractNode cot = null;
         //save the coefficient and argument
         AbstractNode coef = getCoefficientTerm().inverse();
         AbstractNode arg = getArgument();
         
         AbstractNode mult = NodeFactory.createNode( "*",coef, arg.derive() );
         
         //the tangent node
         cot = NodeFactory.createTrigNode( "csc", mult, arg, NodeFactory.createConstantNode( 2 ) );
         
         cot.simplify();
         return cot;
      }

      public AbstractNode integrate()
      {
         // FIXME Unfinished
         if( isDerivative() )
         {
            // Make a node to represent the new coefficient
            //AbstractNode coef = NodeFactory.createConstantNode(  )
            //AbstractNode sin = NodeFactory.createNode( "sin", coef, getArgument() );
            throw new UnsupportedOperationException( this.getStringValue() + "cannot be integrated" );
         }
         throw new UnsupportedOperationException( this.getStringValue() + "cannot be integrated" );
      }
   }
   
   /**
    * Node to represent secant function.
    * 
    * @author Ben Decato
    *
    */
   public static class Secant extends TrigOperatorNode
   {
      public Secant( AbstractNode coefficient,
               AbstractNode argument, AbstractNode power )
      {
         init( "sec", coefficient, argument, power );
      }
      
      public AbstractNode derive()
      {
         AbstractNode sectan = null;
         //save the coefficient and argument
         AbstractNode coef = getCoefficientTerm();
         AbstractNode arg = getArgument();
         AbstractNode one = NodeFactory.createConstantNode( 1 );
         
         AbstractNode mult = NodeFactory.createNode( "*",coef, arg.derive() );
         AbstractNode sec = NodeFactory.createNode("sec", mult, arg );
         AbstractNode tan = NodeFactory.createNode( "tan", one, arg );
         sectan = NodeFactory.createNode( "*", sec, tan );
         
         sectan.simplify();
         return sectan;
      }
      public AbstractNode integrate()
      {
         if( isDerivative() && getPower().toString().equals( "2.0" ) )
         {
            Polynomial poly = (Polynomial)getArgument();
            // Make a node to represent the new coefficient
            if( poly.termCount() == 1)
            {               
               //Get the key
               Set<Double> keys = poly.getMap().keySet();
               Object keyArray[] = keys.toArray();

               //Get the value
               Collection<Double> values = poly.getMap().values();
               Object valuesArray[] = values.toArray();
               
               AbstractNode coef = NodeFactory.createConstantNode( (Double)valuesArray[0] /
                        (Double)keyArray[0] );
               AbstractNode tan = NodeFactory.createNode( "tan", coef, getArgument() );
               return tan;
            }
            throw new UnsupportedOperationException( this.getStringValue() + "cannot be integrated" );
         }
         throw new UnsupportedOperationException( this.getStringValue() + "cannot be integrated" );
      }
   }
   /**
    * Node to represent cosecant function.
    * 
    * @author Ben Decato
    *
    */
   public static class Cosecant extends TrigOperatorNode
   {
      public Cosecant( AbstractNode coefficient, AbstractNode argument, AbstractNode power )
      {
         init( "csc", coefficient, argument, power );
      }
            
      public AbstractNode derive()
      {
         AbstractNode csccot = null;
         //save the coefficient and argument
         AbstractNode coef = getCoefficientTerm().inverse();
            AbstractNode arg = getArgument();
            AbstractNode one = NodeFactory.createConstantNode( 1 );
            
            AbstractNode mult = NodeFactory.createNode( "*", coef, arg.derive() );
            AbstractNode csc = NodeFactory.createNode("csc", mult, arg );
            AbstractNode cot = NodeFactory.createNode( "cot", one, arg );
            
            csccot = NodeFactory.createNode( "*", csc, cot );
               
         csccot.simplify();
         return csccot;
      }
   
      public AbstractNode integrate()
      {
         if( isDerivative() && getPower().toString().equals( "2.0" ) )
         {
            Polynomial poly = (Polynomial)getArgument();
            // Make a node to represent the new coefficient
            if( poly.termCount() == 1)
            {               
               //Get the key
               Set<Double> keys = poly.getMap().keySet();
               Object keyArray[] = keys.toArray();

               //Get the value
               Collection<Double> values = poly.getMap().values();
               Object valuesArray[] = values.toArray();
               
               AbstractNode coef = NodeFactory.createConstantNode( (Double)valuesArray[0] /
                        (Double)keyArray[0] );
               AbstractNode cot = NodeFactory.createNode( "cot", coef, getArgument() );
               return cot.inverse();
            }
            throw new UnsupportedOperationException( this.getStringValue() + "cannot be integrated" );
         }
         throw new UnsupportedOperationException( this.getStringValue() + "cannot be integrated" );
      }
   }  
   
   /**
    * Node to represent natural log function because my OCD wants me to finish Polynomial
    * 
    * @author Jake Schwartz
    *
    */
   public static class NatLog extends TrigOperatorNode
   {
      public NatLog( AbstractNode coefficient, 
               AbstractNode argument, AbstractNode power )
      {
         init( "ln", coefficient, argument, power );
      }
      
      public AbstractNode derive()
      {
         //FIXME: I did it wrong, being eliminated from this release
         /*//Create blank polynomial
         Polynomial poly = new Polynomial();
         
         //Check to see if argument is simple variable
         if( getArgument().isSimpleVariable() && getCoefficientTerm().hasValue() )
         {            
            // Add the entry to the polynomial
            poly.getMap().put( -1.0, getCoefficientTerm().getValue() );
         }
         
         return poly;*/
         
         throw new UnsupportedOperationException( this.getStringValue() + "cannot be derived" );
      }

      public AbstractNode integrate()
      {
         // FIXME stub
         throw new UnsupportedOperationException( this.getStringValue() + "cannot be integrated" );
      }
   }
   
   /**
    * Sets the coefficient node.
    */
   public void setCoefficientTerm( AbstractNode node )
   {
      _children.set( 0, node );
   }
   
   /**
    * Sets the right node.
    */
   public void setArgument( AbstractNode node )
   {
      _children.set( 1, node );
   }
   
   /**
    * Returns the node count.
    */
   public int nodeCount()
   {
      return 2;
   }
   
   /**
    * Check to see if coefficient is the derivative of the argument
    */
   public boolean isDerivative()
   {      
      // Get the derivative of the argument
      AbstractNode argDeriv = getArgument().derive();

      //Check to see if the same powers are involved
      if( argDeriv.equalsIgnoreCoefficients( getCoefficientTerm() ) )
         return true;
      
      return false;
   }
   
   /**
    * Returns a normal (equation) string representation of the operator node.
    */
   public String getStringValue()
   {
      StringBuilder sb = new StringBuilder();
      sb.append( getChildStringValue( getCoefficientTerm() ) );
      sb.append( " \\" );
      sb.append( getType() );
      sb.append( "^" );
      sb.append( getPower().getStringValue() );
      sb.append( "{" );
      sb.append( getArgument().getStringValue() ); // already have { ... }
      sb.append( "}" );
      return sb.toString();
   }
   
}
