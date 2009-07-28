/**
 * BinaryOperatorNode.java
 *
 * Represents a binary operator in an expression.
 *
 * Currently used for /, %, ^.
 *
 * @author Patrick MacArthur 
 *
 * Based on code used for CS416 Programming Assignment #9
 */

package calchelper.tree;

import java.util.ArrayList;

abstract class BinaryOperatorNode extends OperatorNode
{
   /*
    * Implementation notes:
    *
    * _children.get( 0 ) : left node
    * _children.get( 1 ) : right node
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
    */
   public void setLeft( AbstractNode node )
   {
      _children.set( 0, node );
   }
   
   /**
    * Sets the right node.
    */
   public void setRight( AbstractNode node )
   {
      _children.set( 1, node );
   }
   
   /**
    * Returns the left node.
    */
   public AbstractNode getLeft()
   {
      return _children.get( 0 );
   }
   
   /**
    * Returns the right node.
    */
   public AbstractNode getRight()
   {
      return _children.get( 1 );
   }
   
   /**
    * Returns the additive inverse of the node.
    */
   public AbstractNode inverse()
   {
      AbstractNode node = NodeFactory.createNode( "*", 
               NodeFactory.createConstantNode( -1.0 ), this );
      node.simplify();
      return node;
   }
   
   /**
    * Returns the node count.
    */
   public int nodeCount()
   {
      return 2;
   }
   
   static class Addition extends BinaryOperatorNode
   {
      /**
       * Instantiates an addition node.
       */
      public Addition( AbstractNode left, AbstractNode right )
      {
         init( "+", left, right );
      }
      
      public double getValue()
      {
         return getLeft().getValue() + getRight().getValue();
      }
      
      //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      /** Integrate - 
        * By Jake Schwartz
        */
      public AbstractNode integrate( )
      {
         //Integrate Left side
         this.getLeft().integrate();
         
         //Integreate Right side
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
   }
   
   static class Multiplication extends BinaryOperatorNode
   {
      /**
       * Instantiates a multiplication node.
       */
      public Multiplication( AbstractNode left, AbstractNode right )
      {
         init( "*", left, right );
      }
      
      /**
       * Returns the value of the node.
       */
      public double getValue()
      {
         return getLeft().getValue() * getRight().getValue();
      }
      
      //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      /** Integrate - 
        * By Jake Schwartz
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
   
   static class Subtraction extends BinaryOperatorNode
   {
      /**
       * Instantiates a subtraction node.
       */
      public Subtraction( AbstractNode left, AbstractNode right )
      {
         init( "-", left, right );
      }
      
      public double getValue()
      {
         return getLeft().getValue() - getRight().getValue();
      }
      
      //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      /** Integrate - 
        * By Jake Schwartz
        */
      public AbstractNode integrate( )
      {
         //Integrate Left side
         this.getLeft().integrate();
         
         //Integreate Right side
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
   
   static class Division extends BinaryOperatorNode
   {
      /**
       * Instantiates a division node.
       */
      public Division( AbstractNode left, AbstractNode right )
      {
         init( "/", left, right );
      }
      
      public double getValue()
      {
         return getLeft().getValue() / getRight().getValue();
      }
      
      //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      /** Integrate - 
        * By Jake Schwartz
        */
      public AbstractNode integrate( )
      {
         //TO BE CONTINUED
         
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
   
   static class Modulus extends BinaryOperatorNode
   {
      /**
       * Instantiates a modulus node.
       */
      public Modulus( AbstractNode left, AbstractNode right )
      {
         init( "%", left, right );
      }
      
      public double getValue()
      {
         return getLeft().getValue() % getRight().getValue();
      }
      
      //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      /** Integrate - 
        * By Jake Schwartz
        */
      public AbstractNode integrate( )
      {
         //TO BE CONTINUED
         
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
        //FIXME:UNIMPLEMENTED
         return null;
      }
   }
   
   static class Power extends BinaryOperatorNode
   {
      /**
       * Instantiates a power (or exponentiation) node.
       */
      public Power( AbstractNode left, AbstractNode right )
      {
         init( "^", left, right );
      }
      
      public double getValue()
      {
         return Math.pow( getLeft().getValue(), getRight().getValue() );
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
