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

package calchelper.tree;

import java.text.*;

import java.util.regex.Pattern;
import java.util.regex.MatchResult;
import java.util.Scanner;
import java.util.Stack;

public class TreeFactory
{
   private static final String OPERATORS = "()+-*/%^\\";
   private static final Pattern expressionPattern = Pattern.compile( "([0-9]*)([A-Za-z]?)(\\[A-Za-z]+)?" );

   private Stack<String> _opStack;
   private Stack<AbstractNode> _randStack;

   private String _infix;

   /**
    * Creates a tree builder for the specified infix expression.
    */
   public TreeFactory( String infix )
   {
      _infix = infix;
   }

   private void pushDouble( double num )
   {
      System.err.println( "Pushed double: " + num );
      _randStack.push( new ConstantNode( num ) );
   }

   private void pushVariable( String var )
   {
      System.err.println( "Pushed variable: " + var );
      _randStack.push( new VariableNode( var ) );
   }

   private char opposite( char ch )
   {
      switch ( ch )
      {
         case ')': return '(';
         case '}': return '{';
         case ']': return '[';
         case '(': return ')';
         case '{': return '}';
         case '[': return ']';
         default: return ch;
      }
   }

   private void parseOperator( String token, ParsePosition pos )
      throws ExpressionException
   {
      char ch = token.charAt( pos.getIndex() );
      
      // We have an operator
      if ( ch == '\\' )
      {
         System.err.println( "Found LaTeX operator.  Not yet implemented." );
      }
      else if ( ch == '(' || ch == '{' || ch == '[' )
      {
         _opStack.push( String.valueOf( ch ) );
      }
      else if ( ch == ')' || ch == '}' || ch == ']' )
      {
         while( ! ( _opStack.isEmpty() ||
               _opStack.peek().equals( String.valueOf( opposite( ch ) ) ) ) )
         {
            _randStack.push( buildOpNode( _opStack.pop(), _randStack ) );
         }

         if ( _opStack.isEmpty() )
         {
            throw new ExpressionException( "Missing operator.", _infix );
         }

         _opStack.pop(); // throw away the left parenthesis
      }
      else
      {
         String op = String.valueOf( ch );
         System.err.println( "operator found in " + token );
         while ( ! _opStack.isEmpty() &&
               precedence( _opStack.peek() ) >= precedence( op ) )
         {
            _randStack.push( buildOpNode( _opStack.pop(), _randStack ) );
         }
         _opStack.push( op );
      }
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
      _opStack = new Stack<String>();
      _randStack = new Stack<AbstractNode>();

      Scanner scanner = new Scanner( _infix );
      while ( scanner.hasNext() )
      {
         String token = scanner.next();

         ParsePosition pos = new ParsePosition( 0 );

         boolean needsMultiply = false;
         while ( pos.getIndex() < token.length() )
         {
            // First try to find a number
            Number num = NumberFormat.getNumberInstance().parse( token, pos );
            if ( num != null )
            {
               System.err.println( "Next index: " + pos.getIndex() );
               System.err.println( "Token len: " + token.length() );
               pushDouble( num.doubleValue() );
               needsMultiply = true;
               continue;
            }

            // Subtoken
            char ch = token.charAt( pos.getIndex() );
            String st = String.valueOf( ch );

            if ( ! OPERATORS.contains( st ) )
            {
               pushVariable( st );
               if ( needsMultiply )
               {
                  _randStack.push( buildOpNode( "*", _randStack ) );
               }
               else
               {
                  needsMultiply = true;
               }
               pos.setIndex( pos.getIndex() + 1 );
            }
            else
            {
               needsMultiply = false;
               parseOperator( token, pos );
               pos.setIndex( pos.getIndex() + 1 );
            }
         }
      }

      while ( ! _opStack.isEmpty() )
      {
         _randStack.push( buildOpNode( _opStack.pop(), _randStack ) );
      }

      if ( _randStack.size() != 1 )
      {
         throw new ExpressionException(
            "Invalid expression; too many operands or too few operators.",
            _infix );
      }

      ExpressionTree tree = new ExpressionTree( _randStack.pop() );
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

      System.err.println( "Created opnode: " + newNode );

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
         case '^':
            return 3;
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
      System.out.println( expr );
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
      unitTest( "4+5" );
      unitTest( "4+a" );
      unitTest( "4a+5a" );
      unitTest( "4a*5a" );
      unitTest( "(4a*5a)" );
   }
}
