package calchelper.tree;

import java.util.HashMap;
import java.util.Map;

class Polynomial
{
   HashMap<Double, Double> // toil and trouble
                           map; 
   
   public Polynomial( AbstractNode node )
   {
      Monomial mon;

      map = new HashMap<Double, Double>();

      if ( node.hasValue() || node instanceof VariableNode )
      {
         mon = new Monomial( node );
         if ( ! mon.isValid() )
         {
            //throw new ExpressionException( "Invalid monomial.", "" );
            return;
         }
         map.put( mon.power, mon.coefficient );
      }
      else if ( node instanceof BinaryOperatorNode.Addition )
      {
         BinaryOperatorNode.Addition addNode = 
               ( BinaryOperatorNode.Addition ) node;
         for ( int x = 0; x < addNode.nodeCount(); ++x )
         {
            merge( new Polynomial( addNode.getNode( x ) ), 1 );
         }
      }
      else if ( node instanceof BinaryOperatorNode.Subtraction )
      {
         BinaryOperatorNode.Subtraction sNode = 
               ( BinaryOperatorNode.Subtraction ) node;
         merge( new Polynomial( sNode.getLeft()  ),  1 );
         merge( new Polynomial( sNode.getRight() ), -1 );
      }
      else if ( node instanceof BinaryOperatorNode.Multiplication )
      {
         mon = new Monomial( node );
         if ( mon.isValid() )
         {
            map.put( mon.power, mon.coefficient );
         }
         else
         {
            BinaryOperatorNode.Multiplication mNode = 
               ( BinaryOperatorNode.Multiplication ) node;
            Monomial leftMon = new Monomial( mNode.getLeft() );
            Monomial rightMon = new Monomial( mNode.getRight() );

            // We need to distribute a node over a monomial
            if ( leftMon.isValid() && ! rightMon.isValid() )
            {
               distribute( mNode.getRight(), leftMon );
            }
            else if ( ! leftMon.isValid() && rightMon.isValid() )
            {
               distribute( mNode.getLeft(),  rightMon );
            }
            else // none are valid
            {
               distribute( mNode.getLeft(), mNode.getRight() );
            }
         }
      }
      else
      {
         mon = new Monomial( node );
         if ( ! mon.isValid() )
         {
            //throw new ExpressionException( "Invalid monomial.", "" );
            return;
         }
         map.put( mon.power, mon.coefficient );
      }
   }

   private void merge( Polynomial poly, double constant )
   {
      for ( Map.Entry<Double, Double> entry : poly.map.entrySet() )
      {
         if ( map.containsKey( entry.getKey() ) )
         {
            map.put( entry.getKey(),
                  map.get( entry.getKey() ) * entry.getValue() * constant );
         }
         else
         {
            map.put( entry.getKey(), entry.getValue() * constant );
         }
      }
   }

   private double getMultiplicationConstant( AbstractNode node )
   {
      if ( node instanceof BinaryOperatorNode.Addition )
      {
         return 1;
      }
      else if ( node instanceof BinaryOperatorNode.Subtraction )
      {
         return -1;
      }
      else
      {
         return 0;
      }
   }

   private void distribute( AbstractNode node, Monomial mon )
   {
      double constant = getMultiplicationConstant( node );
      
      if ( constant == 0 ) return;

      BinaryOperatorNode binOpNode = ( BinaryOperatorNode ) node;

      // constant only applies to right node
      mergeMultiplication( binOpNode.getLeft(), mon.getTreeNode(), 1 );
      mergeMultiplication( binOpNode.getRight(), mon.getTreeNode(), constant );
   }

   private void distribute( AbstractNode left, AbstractNode right )
   {
      double lC = getMultiplicationConstant( left  );
      double rC = getMultiplicationConstant( right );
      
      // if either constant is 0, can't distribute
      if ( lC * rC == 0 ) return;

      BinaryOperatorNode binLeft  = ( BinaryOperatorNode ) left;
      BinaryOperatorNode binRight = ( BinaryOperatorNode ) right;

      //First
      mergeMultiplication( binLeft.getLeft(),  binRight.getLeft(),  1 );
      //Outer
      mergeMultiplication( binLeft.getLeft(),  binRight.getRight(), rC );
      //Inner
      mergeMultiplication( binLeft.getRight(), binRight.getLeft(),  lC );
      //Last
      mergeMultiplication( binLeft.getRight(), binRight.getRight(), lC * rC );
   }

   private void mergeMultiplication( AbstractNode first,
                                     AbstractNode second,
                                     double constant )
   {
      merge( new Polynomial( NodeFactory.createBinaryOperatorNode( "*", first,
                                                                   second ) ),
             constant );
   }

   public static void unitTest( String infix ) throws ExpressionException
   {
      TreeFactory factory;
      ExpressionTree tree;
      AbstractNode treeRoot;
      Polynomial poly;

      System.out.println( "Unit test with expression: " + infix );
      factory = new TreeFactory( infix );
      tree = factory.buildTree();
      treeRoot = tree._root;
      poly = new Polynomial( treeRoot );
      System.out.println( "Polynomial map: " + poly.map );
      System.out.println();
   }

   public static void main( String[] args ) throws ExpressionException
   {
      // Basic unit tests
      System.out.println( "------ Group 1: Basic tests -----" );
      unitTest( "5 * x ^ 2" );
      unitTest( "( 5 * x ^ 3 ) ^ 2" );
      unitTest( "x + 2 * x" );
      unitTest( "3 * x + 2 * x" );
      unitTest( "x * x" );
      unitTest( "x * 2 * x" );
      unitTest( "x + 2 * x ^ 2" );

      // Single distribution unit tests
      System.out.println( "------ Group 2: Single distribution tests -----" );
      unitTest( "4 * ( x + 3 )" );
      unitTest( "( x + 3 ) * 4" );
      unitTest( "x * ( x + 3 )" );
      unitTest( "( x + 3 ) * x" );

      // Sign tests
      System.out.println( "------ Group 3: Sign tests -----" );
      unitTest( "4 * ( x + 3 )" );
      unitTest( "4 * ( x - 3 )" );
      unitTest( "( x + 3 ) * x" );
      unitTest( "( x - 3 ) * x" );

      // Foil tests
      System.out.println( "------ Group 4: FOIL tests -----" );
      unitTest( "( 4 + x ^ 2 ) * ( x + 3 )" );
      unitTest( "( 4 - x ^ 2 ) * ( x + 3 )" );
      unitTest( "( 3 + x ) * ( 4 + x ^ 2 )" );
      unitTest( "( 3 + x ) * ( 4 - x ^ 2 )" );
      unitTest( "( 3 - x ) * ( 4 - x ^ 2 )" );
   }
}
