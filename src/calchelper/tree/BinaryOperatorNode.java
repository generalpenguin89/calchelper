/*
 * BinaryOperatorNode.java
 * 
 * Based on code written for UNH CS416 Programming Assignment #9, Spring 2009.
 */

package calchelper.tree;

import java.util.ArrayList;

/**
 * Represents a binary operator in an expression.  This class has subclasses 
 * for each type of operator.
 *
 * @author Patrick MacArthur
 */

abstract class BinaryOperatorNode extends OperatorNode
{
   /*
    * Implementation notes:
    *
    * _children.get( 0 ) : left node
    * _children.get( 1 ) : right node
    */
   
   /**
    * Initializes the list of child nodes.  Meant to be called from subclass
    * constructors.
    * 
    * @param op The type of operator being created.
    * @param left The node to the left of the operator in an infix expression.
    * @param right The node to the right of the operator in an infix 
    * expression.
    */
   protected void init( String op, AbstractNode left, AbstractNode right )
   {
      _type = op;
      _children = new ArrayList<AbstractNode>();
      _children.add( left );
      _children.add( right );
   }
   
   /**
    * Sets the left node.
    * 
    * @param node The node to the left of the operator in an infix expression.
    */
   public void setLeft( AbstractNode node )
   {
      _children.set( 0, node );
   }
   
   /**
    * Sets the right node.
    * 
    * @param node The node to the right of the operator in an infix 
    * expression.
    */
   public void setRight( AbstractNode node )
   {
      _children.set( 1, node );
   }
   
   /**
    * Returns the left node.
    * 
    * @return The node to the left of the operator in an infix expression.
    */
   public AbstractNode getLeft()
   {
      return _children.get( 0 );
   }
   
   /**
    * Returns the right node.
    * 
    * @return The node to the right of the operator in an infix expression.
    */
   public AbstractNode getRight()
   {
      return _children.get( 1 );
   }
   
   /**
    * Returns the additive inverse of the node.  Essentially -1 * the current
    * node.
    * 
    * @return The additive inverse of the current node.
    */
   public AbstractNode inverse()
   {
      AbstractNode node = NodeFactory.createNode( "*", 
               NodeFactory.createConstantNode( -1.0 ), this );
      node.simplify();
      return node;
   }
   
   /**
    * Returns the number of child nodes.
    * 
    * @return The number of child nodes.
    */
   public int nodeCount()
   {
      return 2;
   }
   
   /**
    * Returns a human-readable String representation of the 
    * BinaryOperatorNode.
    * 
    * @return A human-readable string representation of the node.
    */
   public String getStringValue()
   {
      StringBuilder sb = new StringBuilder();
      sb.append( getChildStringValue( getLeft() ) );
      sb.append( " " );
      sb.append( getType() );
      sb.append( " " );
      sb.append( getChildStringValue( getRight() ) );
      return sb.toString();
   }
   
   /**
    * A node that represents the addition of its two child nodes..
    * 
    * @author Patrick MacArthur
    * @author Jake Schwartz
    * @author William Rideout
    */
   static class Addition extends BinaryOperatorNode
   {
      /**
       * Instantiates an addition node.
       * 
       * @param left The node to the left of the operator in an infix 
       * expression.
       * @param right The node to the right of the operator in an infix 
       * expression.
       */
      public Addition( AbstractNode left, AbstractNode right )
      {
         init( "+", left, right );
      }
      
      /**
       * Returns the result of adding the two child nodes.  Undefined if 
       * {@link OperatorNode#hasValue() hasValue()} returns false.
       */
      public double getValue()
      {
         return getLeft().getValue() + getRight().getValue();
      }
      
      //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      /**
       * Returns the integral of this node.
       * 
       * @return The integral of this node.
       */
      public AbstractNode integrate( )
      {
         //FIXME: Alters this object.
         //Integrate Left side
         this.getLeft().integrate();
         
         //Integrate Right side
         this.getRight().integrate();
         
         return this;
      }
      //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      
      //---------------------- derive( ) ---------------------------------------
      /**
       * finds the derivative of this node
       * by William Rideout
       */
      public AbstractNode derive( )
      {
        //derive left side
        AbstractNode left = this.getLeft( ).derive( );
        
        //derive right side
        AbstractNode right = this.getRight( ).derive( );
        
        //make and return a new AbstractNode
        AbstractNode newAdd = NodeFactory.createNode("+", left, right);
        return newAdd;
      }

      /**
       * Returns the precedence level of the node.
       */
      protected int precedence()
      {
         return 10;
      }
   }
   
   /**
    * A node that represents the multiplication of its two child nodes.
    * 
    * @author Patrick MacArthur
    * @author Jake Schwartz
    * @author William Rideout
    */   
   static class Multiplication extends BinaryOperatorNode
   {
      /**
       * Instantiates a multiplication node.
       * 
       * @param left The node to the left of the operator in an infix 
       * expression.
       * @param right The node to the right of the operator in an infix 
       * expression.
       */
      public Multiplication( AbstractNode left, AbstractNode right )
      {
         init( "*", left, right );
      }
      
      /**
       * Returns the result of multiplying the two child nodes.  Undefined if 
       * {@link OperatorNode#hasValue() hasValue()} returns false.
       */
      public double getValue()
      {
         return getLeft().getValue() * getRight().getValue();
      }

      /**
       * Returns the precedence level of the node.
       */
      protected int precedence()
      {
         return 15;
      }
      
      //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      /**
       * Returns the integral of this node.
       * 
       * @return The integral of this node.
       */
      public AbstractNode integrate( )
      {	
      	/*Case 1: Function in the form INTEGRAL[ a * unary( b ) dx ]
      	 * where a is the derivative of b sans all coefficients
      	 * 
      	 * Use u-substitution
      	 */
      	
      	
      	//-------- Case 2: When it is not case 1 ---------------
      	/*Subcase 1: Function in the form INTEGRAL[ trig * trig ]
      	 * 
      	 * Use trig substitution
      	 */
      	
      	/* Subcase 2: Function is the production of any two functions
      	 * 
      	 * Use Integration by parts
      	 */
         
         return this;
      }
      //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      
      //---------------------- derive() ---------------------------------------
      /**
       * finds the derivative of this node
       * by William Rideout
       */
      public AbstractNode derive()
      {
        AbstractNode mul = null;
         
        //we need to remember the left and right nodes...
        AbstractNode left = getLeft();
        AbstractNode right = getRight();
           
        //and their derivatives
        AbstractNode leftP = left.derive();
        AbstractNode rightP = right.derive();
           
        //the two multiplication parts of the new node
        AbstractNode multLeft = NodeFactory.createNode("*", leftP, right);
        AbstractNode multRight = NodeFactory.createNode("*", left, rightP);
           
        //the new node, an addition node
        mul = NodeFactory.createNode("+", multLeft, multRight);
                
        mul.simplify();
        return mul;
      }
   }
   
   /**
    * A node that represents the subtraction of the right child from the left
    * child.
    * 
    * @author Patrick MacArthur
    * @author Jake Schwartz
    * @author William Rideout
    */   
   static class Subtraction extends BinaryOperatorNode
   {
      /**
       * Instantiates a subtraction node.
       * 
       * @param left The node to the left of the operator in an infix 
       * expression.
       * @param right The node to the right of the operator in an infix 
       * expression.
       */
      public Subtraction( AbstractNode left, AbstractNode right )
      {
         init( "-", left, right );
      }
      
      /**
       * Returns the result of subtracting the right child node from the left
       * child node.  Undefined if {@link OperatorNode#hasValue() hasValue()}
       * returns false.
       */
      public double getValue()
      {
         return getLeft().getValue() - getRight().getValue();
      }

      /**
       * Returns the precedence level of the node.
       */
      protected int precedence()
      {
         return 10;
      }
      
      //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      /** Integrate - 
        * By Jake Schwartz
        */
      public AbstractNode integrate( )
      {
         //Integrate Left side
         this.getLeft().integrate();
         
         //Integrate Right side
         this.getRight().integrate();
         
         return this;
      }
      //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      
      //---------------------- derive( ) ---------------------------------------
      /**
       * finds the derivative of this node
       * by William Rideout
       */
      public AbstractNode derive( )
      {
        //derive left side
        AbstractNode left = this.getLeft( ).derive( );
        
        //derive right side
        AbstractNode right = this.getRight( ).derive( );
      
        //create and return a new AbstractNode
        AbstractNode newSub = NodeFactory.createNode("-", left, right );
        return newSub;
      }
   }
   
   /**
    * A node that represents the division of the left child by the right 
    * child.
    * 
    * @author Patrick MacArthur
    * @author Jake Schwartz
    * @author William Rideout
    */   
   static class Division extends BinaryOperatorNode
   {
      /**
       * Instantiates a division node.
       * 
       * @param left The node to the left of the operator in an infix 
       * expression.
       * @param right The node to the right of the operator in an infix 
       * expression.
       */
      public Division( AbstractNode left, AbstractNode right )
      {
         init( "/", left, right );
      }
      
      /**
       * Returns the result of dividing the left child node by the right child
       * node.  Undefined if {@link OperatorNode#hasValue() hasValue()}
       * returns false.
       */
      public double getValue()
      {
         return getLeft().getValue() / getRight().getValue();
      }

      /**
       * Returns the precedence level of the node.
       */
      protected int precedence()
      {
         return 15;
      }
      
      //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      /** Integrate - 
        * By Jake Schwartz
        */
      public AbstractNode integrate( )
      {
         /* Inverse sin:
          * 1/sqrt(a^2 - u^2) -> arcsin u/a
          */
         //Make sure all the requirement are met
         /*if( this.getRight() instanceof TrigOperatorNode.SquareRoot )
         {
            if( this.getRight().getArgument() instanceof Polynomial )
            {
               AbstractNode poly = this.getRight().getArgument();
               //Check to see if it is a minus...
               if( right.getMap().containsKey( -2.0 ) && right.getMap().containsKey( 0.0 )
                        && ( right.getMap().size() == 2 ) )
               {
                  //Make arcsin node and return it
                  return this;
               }
               
               //...or a plus in the square root
               if( right.getMap().containsKey( 2.0 ) && right.getMap().containsKey( 0.0 )
                        && ( right.getMap().size() == 2 ) )
               {
                  //Make arcsin node and return it
                  return this;
               }
            }
         }*/
         
         //FIXME: Error in code
         /* Inverse tan:
          * 1/(a^2 + u^2) -> 1/a arctan u/a
          */
         //See if the right is a polynomial
         /*if( this.getRight() instanceof Polynomial ) //Add ability to test num and denom for derivatives
         {
            //Then make sure it has a^2 + u^2 and thats it
            Polynomial right = this.getRight();
            if( right.getMap().containsKey( 2.0 ) && right.getMap().containsKey( 0.0 )
                     && ( right.getMap().size() == 2 ) )
            {
               //Make the arc tan node and return it
               return this;
            }
         }*/
         
         return this;
      }
      //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      
      //---------------------- derive( ) ---------------------------------------
      /**
       * finds the derivative of this node
       * by William Rideout
       */
      public AbstractNode derive( )
      {
        //quotient rule
         AbstractNode quo = null;
         
         //we need to save the left and right nodes of this node...
         AbstractNode left = getLeft();
         AbstractNode right = getRight();
         
         //as well as the derivatives of them
         AbstractNode leftP = left.derive();
         AbstractNode rightP = right.derive();
              
         //multiplication nodes of the quotient rule
         AbstractNode multLeft = NodeFactory.createNode("*", leftP, right);
         AbstractNode multRight = NodeFactory.createNode("*", left, rightP);
         
         //the subtraction node of the quotient rule
         AbstractNode sub = NodeFactory.createNode("-", multLeft, multRight);
         
         //the squared node
         AbstractNode sq = NodeFactory.createNode("*", right, right);
         
         //the actual division node
         quo = NodeFactory.createNode("/", sub, sq);
         
         quo.simplify();
         return quo;
      }
   }
   
   /**
    * A node that represents the value of the left child node taken to the
    * power of the right child node.
    * 
    * @author Patrick MacArthur
    * @author Jake Schwartz
    * @author William Rideout
    */   
   static class Power extends BinaryOperatorNode
   {
      /**
       * Instantiates a power (or exponentiation) node.
       * 
       * @param left The node to the left of the operator in an infix 
       * expression.
       * @param right The node to the right of the operator in an infix 
       * expression.
       */
      public Power( AbstractNode left, AbstractNode right )
      {
         init( "^", left, right );
      }
      
      /**
       * Returns the result of taking the value of the left child node to the
       * power of the value of the right child node.  Undefined if 
       * {@link OperatorNode#hasValue() hasValue()} returns false.
       */
      public double getValue()
      {
         return Math.pow( getLeft().getValue(), getRight().getValue() );
      }

      /**
       * Returns the precedence level of the node.
       */
      protected int precedence()
      {
         return 20;
      }
      
      //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      /** Integrate - 
        * By Jake Schwartz
        */
      public AbstractNode integrate( )
      {
         //********CASE 1: Simple exponent x^n**********
         if( getLeft().isSimpleVariable() && getRight().hasValue())
         {
            //Make the exponent one higher
            this.setLeft( NodeFactory.createConstantNode( getRight().getValue() + 1 ) );
            
            //Make coefficient nodes
            AbstractNode coef = NodeFactory.createConstantNode( (double)(1) / getRight().getValue() );
            Multiplication mult = new Multiplication( coef, getLeft() );
            this.setLeft( mult );
         }
         //********************************************
         
         return this;
      }
      //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      
      //---------------------- derive( ) ---------------------------------------
      /**
       * finds the derivative of this node
       * by William Rideout
       */
      public AbstractNode derive( )
      {
         Multiplication times = null;
        //case: x^n ==> nx^(n-1)
        if( getLeft( ).isSimpleVariable() && getRight( ).hasValue() )
        {
          //save the value of the power... minus 1
          AbstractNode pow = NodeFactory.createConstantNode( getRight( ).getValue( ) - 1 );
          
          //create a new constant node.. this is the new coefficient that will be out front
          AbstractNode val = NodeFactory.createConstantNode( getRight( ).getValue( ) );
          
          //put the node together
          times = new Multiplication( getLeft( ), val );
          setLeft( times );
          setRight( pow );
        }
        return times;
      }
   }
}
