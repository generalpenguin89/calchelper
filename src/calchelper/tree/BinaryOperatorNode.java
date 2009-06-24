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

public abstract class BinaryOperatorNode extends OperatorNode
{
   /*
    * Implementation notes:
    *
    * _children.get( 0 ) : left node
    * _children.get( 1 ) : right node
    */
   
   
   private void init( String op, AbstractNode left, AbstractNode right )
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
    * Returns the node count.
    */
   public int nodeCount()
   {
      return 2;
   }
   
   /**
    * Rotates the operation ( c op x ) op y to c op ( x op y )
    */
   private void rotateRight( String op )
   {
      BinaryOperatorNode leftChild = ( BinaryOperatorNode ) getLeft();
      if ( leftChild.getLeft().hasValue() )
      {
         setLeft( leftChild.getLeft() );
         setRight( NodeFactory.createBinaryOperatorNode( op,
                                                        leftChild.getRight(), getRight() ) );
         getRight().simplify();
      }
   }
   
   /**
    * Rotates the operation x op ( c op y ) to c op ( x op y )
    */
   private void rotateLeft( String op )
   {
      BinaryOperatorNode rightChild = ( BinaryOperatorNode ) getRight();
      if ( rightChild.getLeft().hasValue() )
      {
         AbstractNode curLeft = getLeft();
         setLeft( rightChild.getLeft() );
         setRight( NodeFactory.createBinaryOperatorNode( op,
                                                        curLeft, rightChild.getRight() ) );
         getRight().simplify();
      }
   }
   
   /**
    * Useless, deprecated method.
    */
   private void isolateConstant( String type )
   {
   }
   
   public static class Addition extends BinaryOperatorNode
   {
      /**
       * Instantiates an addition node.
       */
      public Addition( AbstractNode left, AbstractNode right )
      {
         super.init( "+", left, right );
      }
      
      public double getValue()
      {
         return getLeft().getValue() + getRight().getValue();
      }
      
      public void simplify()
      {
         super.simplify();
         
         // Deals with ( c * x ) * y
         if ( getLeft() instanceof Addition )
         {
            super.rotateRight( "+" );
         }
         
         // Deals with x * ( c * y )
         if ( getRight() instanceof Addition )
         {
            super.rotateLeft( "+" );
         }
      }
      
      //---------------------------------------
      /** Integrate - 
        * By Jake Schwartz
        */
      public void integrate( )
      {
         //Integrate Left side
         this.getLeft().integrate();
         
         //Integreate Right side
         this.getRight().integrate();
      }
      //---------------------------------------
      
      //---------------------- derive( ) ---------------------------------------
      /**
       * finds the derivative of this node
       * by William Rideout
       */
      public void derive( )
      {
        //derive left side
        this.getLeft( ).derive( );
        
        //derive right side
        this.getRight( ).derive( );
      }
   }
   
   public static class Multiplication extends BinaryOperatorNode
   {
      /**
       * Instantiates a multiplication node.
       */
      public Multiplication( AbstractNode left, AbstractNode right )
      {
         super.init( "*", left, right );
      }
      
      /**
       * Returns the value of the node.
       */
      public double getValue()
      {
         return getLeft().getValue() * getRight().getValue();
      }
      
      
      public void simplify()
      {
         super.simplify();
         
         /*
          * Most of this method is charged with getting the nodes into the
          * configuration coefficient * ( stuff ).  This should make the tree
          * easier to deal with.
          */
         
         // FIXME: Add distributive property
         
         System.out.println( "Simplifying multiplication . . . " );
         
         // Deals with ( c * x ) * y
         if ( getLeft() instanceof Multiplication )
         {
            super.rotateRight( "*" );
         }
         
         // Deals with x * ( c * y )
         if ( getRight() instanceof Multiplication )
         {
            super.rotateLeft( "*" );
         }
         
         // Deals with x * a, where a is constant
         if ( getRight().hasValue() && ! getLeft().hasValue() )
         {
            // Swap left and right nodes
            AbstractNode temp = getLeft();
            setLeft( getRight() );
            setRight( temp );
         }
         
         // Deals with a * (b * x), where a, b are constant
         if ( getRight() instanceof Multiplication )
         {
            Multiplication rightChild = ( Multiplication ) getRight();
            if ( getLeft().hasValue() && rightChild.getLeft().hasValue() )
            {
               setLeft( 
                       new ConstantNode(
                                        getLeft().getValue() * rightChild.getLeft().getValue() ) );
               setRight( rightChild.getRight() );
            }
         }
         
         /*if ( getLeft() instanceof VariableNode )
          {
          if ( getRight() instanceof VariableNode )
          {
          if ( ( ( Variable ) getLeft() ).getStringValue().equals(
          ( ( Variable ) getRight() ).getStringValue() ) )
          {
          }
          }
          }*/
      }
      
      //---------------------------------------
      /** Integrate - 
        * By Jake Schwartz
        */
      public void integrate( )
      {
         //If there is a coefficient, integrate the right node
         if( getLeft() instanceof ConstantNode )
            getRight().integrate();
      }
      //-------------------------------------
      
      //---------------------- derive() ---------------------------------------
      /**
       * finds the derivative of this node
       * by William Rideout
       */
      public void derive()
      {
        //example: 2x^2
        if( getLeft() instanceof ConstantNode && getRight() instanceof 
             BinaryOperatorNode.Power )
        {
          getRight( ).derive( );
          simplify( );
        }
        
        //product rule will go here
      }
   }
   
   public static class Subtraction extends BinaryOperatorNode
   {
      /**
       * Instantiates a subtraction node.
       */
      public Subtraction( AbstractNode left, AbstractNode right )
      {
         super.init( "-", left, right );
      }
      
      public double getValue()
      {
         return getLeft().getValue() - getRight().getValue();
      }
      
      public void simplify()
      {
         super.simplify();
      }
      
      //---------------------------------------
      /** Integrate - 
        * By Jake Schwartz
        */
      public void integrate( )
      {
         //Integrate Left side
         this.getLeft().integrate();
         
         //Integreate Right side
         this.getRight().integrate();
      }
      //----------------------------------------
      
      //---------------------- derive( ) ---------------------------------------
      /**
       * finds the derivative of this node
       * by William Rideout
       */
      public void derive( )
      {
        //derive left side
        this.getLeft( ).derive( );
        
        //derive right side
        this.getRight( ).derive( );
      }
   }
   
   public static class Division extends BinaryOperatorNode
   {
      /**
       * Instantiates a division node.
       */
      public Division( AbstractNode left, AbstractNode right )
      {
         super.init( "/", left, right );
      }
      
      public double getValue()
      {
         return getLeft().getValue() / getRight().getValue();
      }
      
      public void simplify()
      {
         super.simplify();
      }
      
      //---------------------- derive( ) ---------------------------------------
      /**
       * finds the derivative of this node
       * by William Rideout
       */
      public void derive( )
      {
        //UNIMPLEMENTED
        //quotient rule will go here
      }
   }
   
   public static class Modulus extends BinaryOperatorNode
   {
      /**
       * Instantiates a modulus node.
       */
      public Modulus( AbstractNode left, AbstractNode right )
      {
         super.init( "%", left, right );
      }
      
      public double getValue()
      {
         return getLeft().getValue() % getRight().getValue();
      }
      
      public void simplify()
      {
         super.simplify();
      }
      
      //-----------------------------------------
      /** Integrate - 
        * By Jake Schwartz
        */
      public void integrate( )
      {
         //TO BE CONTINUED
      }
      //-----------------------------------------
      
      //---------------------- derive( ) ---------------------------------------
      /**
       * finds the derivative of this node
       * by William Rideout
       */
      public void derive( )
      {
        //UNIMPLEMENTED
      }
   }
   
   public static class Power extends BinaryOperatorNode
   {
      /**
       * Instantiates a power (or exponentiation) node.
       */
      public Power( AbstractNode left, AbstractNode right )
      {
         super.init( "^", left, right );
      }
      
      public double getValue()
      {
         return Math.pow( getLeft().getValue(), getRight().getValue() );
      }
      
      public void simplify()
      {
         super.simplify();
      }
        
      //------------------------------------------------
      /** Integrate - 
        * By Jake Schwartz
        */
      public void integrate( )
      {
         //********CASE 1: Simple exponent x^n**********
         if( getLeft() instanceof VariableNode && getRight() instanceof ConstantNode )
         {
            //Make the exponent one higher
            this.setLeft( new ConstantNode( getRight().getValue() + 1 ) );
            
            //Make coeffiecient nodes
            ConstantNode coef = new ConstantNode( (double)(1) / getRight().getValue() );
            Multiplication mult = new Multiplication( coef, getLeft() );
            setLeft( mult );
            super.rotateRight( "*" );
            simplify();
         }
         //********************************************
      }
      //-----------------------------------------------
      
      //---------------------- derive( ) ---------------------------------------
      /**
       * finds the derivative of this node
       * by William Rideout
       */
      public void derive( )
      {
        //case: x^n ==> nx^(n-1)
        if( getLeft( ) instanceof VariableNode && getRight( ) instanceof ConstantNode )
        {
          //save the value of the power... minus 1
          double pow = getRight( ).getValue( ) - 1;
          
          //create a new constant node.. this is the new coefficient that will be out front
          ConstantNode val = new ConstantNode( getRight( ).getValue( ) );
          
          //NODE NEEDS TO BE PUT TOGETHER
        }
      }
   }
}
