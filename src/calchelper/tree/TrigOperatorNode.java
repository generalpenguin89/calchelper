/**
 * 
 */
package calchelper.tree;

import java.util.ArrayList;
//import java.util.Map;

/**
 * A node type to handle trigonometric functions with a coefficient term in 
 * front.  For example, it can hold 2x * tan( x^2 ) in one node.
 * 
 * @author Patrick MacArthur
 *
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
         // FIXME Unfinished
         if( isDerivative() )
         {
            // Make a node to represent the new coefficient
            //AbstractNode coef = NodeFactory.createConstantNode(  )
            //AbstractNode cos = NodeFactory.createNode( "cos", coef, getArgument() );
            //AbstractNode invCos = cos.inverse();
            return null;
         }
         return null;
      }
   }
   
   /**
    * Node to represent cosine function.
    * 
    * @author Patrick MacArthur
    *
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
         // FIXME Unfinished
         if( isDerivative() )
         {
            // Make a node to represent the new coefficient
            //AbstractNode coef = NodeFactory.createConstantNode(  )
            //AbstractNode sin = NodeFactory.createNode( "sin", coef, getArgument() );
            return null;
         }
         return null;
      }
   }
   
   /**
    * Node to represent tangent function.
    * 
    * @author William Rideout
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
         //TODO: complete this class
         //save the coefficient and argument
         AbstractNode coef = getCoefficientTerm();
         AbstractNode arg = getArgument();
         
         AbstractNode mult = NodeFactory.createNode( "*",coef, arg.derive() );
         
         //the tangent node
         sec = NodeFactory.createNode( "sec", mult, arg );
         
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
            return null;
         }
         return null;
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
         //TODO: complete this class
         //save the coefficient and argument
         /**AbstractNode coef = getCoefficientTerm();
         AbstractNode arg = getArgument();
         
         AbstractNode mult = NodeFactory.createNode( "*",coef, arg.derive() );
         
         //the tangent node
         tan = NodeFactory.createNode( "sin", mult, arg );
         
         tan.simplify();**/
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
            return null;
         }
         return null;
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
         AbstractNode sec = null;
         //TODO: complete this class
         //save the coefficient and argument
         /**AbstractNode coef = getCoefficientTerm();
         AbstractNode arg = getArgument();
         
         AbstractNode mult = NodeFactory.createNode( "*",coef, arg.derive() );
         
         //the tangent node
         tan = NodeFactory.createNode( "sin", mult, arg );
         
         tan.simplify();**/
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
         }
         return null;
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
         AbstractNode csc = null;
         //TODO: complete this class
         //save the coefficient and argument
         /**AbstractNode coef = getCoefficientTerm();
            AbstractNode arg = getArgument();
               
            AbstractNode mult = NodeFactory.createNode( "*",coef, arg.derive() );
               
            //the tangent node
            tan = NodeFactory.createNode( "sin", mult, arg );
               
         tan.simplify();**/
         return csc;
      }
   
      public AbstractNode integrate()
      {
         // FIXME Unfinished
         if( isDerivative() )
         {
            //AbstractNode placeholder = getArgument();
            // Make a node to represent the new coefficient
            //AbstractNode coef = NodeFactory.createConstantNode(  )
            //AbstractNode sin = NodeFactory.createNode( "sin", coef, getArgument() );
         }
         return null;
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
         // FIXME not finished
         
         //Create blank polynomial
         Polynomial poly = new Polynomial();
         
         //Check to see if argument is simple variable
         if( getArgument().isSimpleVariable() && getCoefficientTerm().hasValue() )
         {            
            // Add the entry to the polynomial
            poly.getMap().put( -1.0, getCoefficientTerm().getValue() );
         }
         
         return poly;
      }

      public AbstractNode integrate()
      {
         // FIXME stub
         return null;
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
      //FIXME: This one might be tough with all the different kinds of nodes...
      
      // Get the derivative of the argument
      //AbstractNode argDeriv = getArgument().derive();
      
      // For each entry in the map
      /*for ( Map.Entry<Double, Double> entry : argDeriv.getMap().entrySet() )
      {
         if( !( getArgument().getMap().containsKey( entry.getKey() ) ) )
            return false;
      }*/
      
      return true;
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
      sb.append( "{" );
      sb.append( getArgument().getStringValue() ); // already have { ... }
      sb.append( "}" );
      return sb.toString();
   }
   
}
