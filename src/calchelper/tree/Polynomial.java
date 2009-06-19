package calchelper.tree;

import java.util.HashMap;
import java.util.Map;

class Polynomial
{
   HashMap<Double, Double> // toil and trouble
                           map; 
   
   public Polynomial( AbstractNode node ) throws ExpressionException
   {
      Monomial mon;

      map = new HashMap<Double, Double>();

      if ( node.hasValue() || node instanceof VariableNode )
      {
         mon = new Monomial( node );
         if ( ! mon.isValid() )
         {
            throw new ExpressionException( "Invalid monomial.", "" );
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
      else
      {
         mon = new Monomial( node );
         if ( ! mon.isValid() )
         {
            throw new ExpressionException( "Invalid monomial.", "" );
         }
         map.put( mon.power, mon.coefficient );
      }
   }

   public void merge( Polynomial poly, double constant )
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

   public static void unitTest( String infix ) throws ExpressionException
   {
      TreeFactory factory;
      ExpressionTree tree;
      AbstractNode treeRoot;
      Polynomial poly;

      factory = new TreeFactory( infix );
      tree = factory.buildTree();
      treeRoot = tree._root;
      poly = new Polynomial( treeRoot );
      System.out.println( tree );
      System.out.println( poly.map );
   }

   public static void main( String[] args ) throws ExpressionException
   {
      unitTest( "5 * x ^ 2" );
      unitTest( "( 5 * x ^ 3 ) ^ 2" );
      unitTest( "x + 2 * x" );
      unitTest( "3 * x + 2 * x" );
      unitTest( "x * x" );
      unitTest( "x * 2 * x" );
      unitTest( "x + 2 * x ^ 2" );
   }
}
