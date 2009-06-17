/**
 * TreeFactory.java
 *
 *
 * The TreeFactory is in charge of building a tree given an infix
 * expression.
 *
 * @author Patrick MacArthur
 *
 * Based on code written for CS416 Programming Assignment #9
 */

import java.util.Scanner;
import java.util.Stack;

public class TreeFactory
{
   private static final String OPERATORS = "()+-*/%";

   private String _infix;

   /**
    * Creates a tree builder for the specified infix expression.
    */
   public TreeFactory( String infix )
   {
      _infix = infix;
   }

   /**
    * buildTree()
    *
    * Builds the tree for the expression contained in the TreeFactory object.
    *
    * @throws ExpressionException if the expression is invalid.
    */
   public ExpressionTree buildTree() throws ExpressionException
   {
      Stack<String> opStack = new Stack<String>();
      Stack<AbstractNode> randStack = new Stack<AbstractNode>();

      Scanner scanner = new Scanner( _infix );
      while ( scanner.hasNext() )
      {
         String token = scanner.next();

         if ( ! OPERATORS.contains( token ) )
         {
            try
            {
               randStack.push( new ConstantNode( Double.parseDouble( token ) ) );
            }
            catch ( NumberFormatException e )
            {
               randStack.push( new VariableNode( token ) );
            }
         }
         else if ( token.equals( "(" ) )
         {
            opStack.push( token );
         }
         else if ( token.equals( ")" ) )
         {
            while( ! ( opStack.isEmpty() || opStack.peek().equals( "(" ) ) )
            {
               randStack.push( buildOpNode( opStack.pop(), randStack ) );
            }

            if ( opStack.isEmpty() )
            {
               throw new ExpressionException( "Missing operator.", _infix );
            }

            opStack.pop(); // throw away the left parenthesis
         }
         else
         {
            while ( ! opStack.isEmpty() &&
                  precedence( opStack.peek() ) >= precedence( token ) )
            {
               randStack.push( buildOpNode( opStack.pop(), randStack ) );
            }
            opStack.push( token );
         }
      }
      while ( ! opStack.isEmpty() )
      {
         randStack.push( buildOpNode( opStack.pop(), randStack ) );
      }

      if ( randStack.size() != 1 )
      {
         throw new ExpressionException(
            "Invalid expression; too many operands or too few operators.",
            _infix );
      }

      ExpressionTree tree = new ExpressionTree( randStack.pop() );
      return tree;
   }

   /*
    * buildOpNode()
    *
    * Builds the operator node from an operator and the top two items off of
    * the operand stack.
    *
    * op: The operator.
    * stack: The operand stack.
    *
    * Throws ExpressionException if the expression is invalid.
    */
   private OperatorNode buildOpNode( String op, 
         Stack<AbstractNode> stack ) throws ExpressionException
   {
      AbstractNode leftNode = null;
      AbstractNode rightNode = null;

      if ( op.equals( "(" ) )
      {
         throw new ExpressionException( "Missing )", _infix );
      }


      if ( stack.size() >= 2 )
      {
         rightNode = stack.pop();
         leftNode = stack.pop();
      }
      else
      {
         throw new ExpressionException( "Not enough operands.", _infix );
      }

      OperatorNode newNode = NodeFactory.createBinaryOperatorNode( 
            op, leftNode, rightNode );

      if ( newNode != null )
      {
         return newNode;
      }
      else
      {
         return null;
      }
   }

   /*
    * precedence()
    *
    * Returns an integer indicating the precedence of the operator.
    *
    * Necessary since some operators share a precedence level.
    */
   private static int precedence( String op )
   {
      switch ( op.charAt( 0 ) )
      {
         case '(': // fall-through
         case ')':
            return 0;
         case '+': // fall-through
         case '-':
            return 1;
         case '*': // fall-through
         case '/': // fall-through
         case '%':
            return 2;
         default:
            return -1;
      }
   }

   /**
    * Unit test.
    */
   public static void unitTest( String expr ) throws ExpressionException
   {
      TreeFactory builder = new TreeFactory ( expr );
      ExpressionTree tree = builder.buildTree();
      tree.simplify();
      System.out.println( tree );
   }

   /**
    * Unit test for TreeFactory.
    */
   public static void main( String[] argrs ) throws ExpressionException
   {
      unitTest( "4 + 5" );
      unitTest( "( 4 + 5 )" );
      unitTest( "( 2 + 4 * 5 )" );
      unitTest( "2 + ( 4 * 5 )" );
      unitTest( "( 2 + 4 ) * 5" );
      unitTest( "( 4 / ( 5 + 5 ) )" );
   }
}
