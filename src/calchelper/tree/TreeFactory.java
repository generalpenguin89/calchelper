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


import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Stack;

public class TreeFactory
{
   private static final String OPERATORS = "{}[]()+-*/%^\\";

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
      //System.err.println( "Pushed double: " + num );
      _randStack.push( NodeFactory.createConstantNode( num ) );
   }

   private void pushVariable( String var )
   {
      //System.err.println( "Pushed variable: " + var );
      _randStack.push( NodeFactory.createVariableNode( var ) );
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
   
   /**
    * Parses a LaTeX operator from a token.
    * 
    * @param token The token to parse.
    * @throws ExpressionException
    */
   private void parseLatexFunction( String token ) throws ExpressionException
   {
      String function;
      
      if ( token.indexOf( "{" ) != -1 )
      {
         ParsePosition powPos = new ParsePosition( token.indexOf( "^" ) );
         ParsePosition argPos = new ParsePosition( token.indexOf( "{" ) );
         function = token.substring( 0, argPos.getIndex() );
         if ( token.indexOf( "}", argPos.getIndex() ) <= -1 )
         {
            throw new ExpressionException( "unterminated {", token );
         }
         
         // Parse the argument of the function
         parseToken( token.substring( argPos.getIndex(), 
                  token.indexOf( "}", argPos.getIndex() ) + 1 ) );
         
         if ( powPos.getIndex() != -1 )
         {
            parseToken( token.substring( 
                     powPos.getIndex() + 1, argPos.getIndex() ) );
         }
         else
         {
            parseToken( "1.0" );
         }
      }
      else if ( token.indexOf( "[" ) != -1 )
      {
         throw new UnsupportedOperationException(
                  "Root function is currently not supported." );
      }
      else
      {
         function = token;
      }
      System.out.println( _randStack );
      _randStack.push( buildTrigOpNode( function, _randStack ) );
   }

   private void parseOperator( String token, ParsePosition pos )
      throws ExpressionException
   {
      char ch = token.charAt( pos.getIndex() );
      
      if ( ch == '(' || ch == '{' || ch == '[' )
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
         //System.err.println( "operator " + op + " found in " + token );
         while ( ! _opStack.isEmpty() &&
               precedence( _opStack.peek() ) >= precedence( op ) )
         {
            _randStack.push( buildOpNode( _opStack.pop(), _randStack ) );
         }
         _opStack.push( op );
      }
   }

   /**
    * parseToken()
    * 
    * Parses a token in the input string.
    * 
    * @throws ExpressionException if the expression is invalid.
    */
   private void parseToken( String token ) throws ExpressionException
   {
      ParsePosition pos = new ParsePosition( 0 );

      boolean needsMultiply = false;
      while ( pos.getIndex() < token.length() )
      {
         // First try to find a number
         Number num = NumberFormat.getNumberInstance().parse( token, pos );
         if ( num != null )
         {
            //System.err.println( "Next index: " + pos.getIndex() );
            //System.err.println( "Token len: " + token.length() );
            pushDouble( num.doubleValue() );
            needsMultiply = true;
            continue;
         }

         // Subtoken
         char ch = token.charAt( pos.getIndex() );
         String st = String.valueOf( ch );

         if ( ch == ' ' )
         {
            // do nothing
         }
         else if ( ! OPERATORS.contains( st ) )
         {
            pushVariable( st );
            if ( needsMultiply )
            {
               _opStack.push( "*" );
            }
            else
            {
               needsMultiply = true;
            }
         }
         else
         {
         // We have an operator
            if ( ch == '\\' )
            {
               if ( needsMultiply )
               {
                  needsMultiply = false;
               }
               else
               {
                  if ( !_opStack.isEmpty() && _opStack.peek().equals( "*" ) )
                  {
                     // we don't need this multiplication anymore since we
                     // are capable of handling the multiplication.
                     _opStack.pop();
                  }
                  else
                  {
                     pushDouble( 1.0 );
                  }
               }
               parseLatexFunction( token.substring( pos.getIndex() + 1 ) );
               pos.setIndex( token.indexOf( "}", pos.getIndex() + 1 ) );
            }
            else
            {
               needsMultiply = false;
               parseOperator( token, pos );
            }
         }
         pos.setIndex( pos.getIndex() + 1 );
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

      parseToken( _infix );

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

   /**
    * buildOpNode()
    *
    * Builds the operator node from an operator and the top two items off of
    * the operand stack.
    *
    * @param op The operator.
    * @param stack The operand stack.
    *
    * Throws ExpressionException if the expression is invalid.
    */
   private AbstractNode buildOpNode( String op, 
         Stack<AbstractNode> stack ) throws ExpressionException
   {
      AbstractNode leftNode = null;
      AbstractNode rightNode = null;

      if ( op.equals( "(" ) || op.equals( "{" ) || op.equals( "[" ) )
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

      return NodeFactory.createNode( op, leftNode, rightNode );
   }
   
   private AbstractNode buildTrigOpNode( String op, 
            Stack<AbstractNode> stack ) throws ExpressionException
      {
         AbstractNode leftNode = null;
         AbstractNode rightNode = null;
         AbstractNode power = null;

         if ( op.equals( "(" ) || op.equals( "{" ) || op.equals( "[" ) )
         {
            throw new ExpressionException( "Missing )", _infix );
         }

         if ( stack.size() >= 3 )
         {
            power = stack.pop();
            rightNode = stack.pop();
            leftNode = stack.pop();
         }
         else
         {
            throw new ExpressionException( "Not enough operands.", _infix );
         }

         return NodeFactory.createTrigNode( 
                  op, leftNode, rightNode, power );
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
}
