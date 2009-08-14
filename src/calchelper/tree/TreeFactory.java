
package calchelper.tree;


import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Stack;

/**
 * TreeFactory.java
 *
 *
 * The TreeFactory is in charge of building a tree given an infix
 * expression.
 *
 * @author Patrick MacArthur <pio3@unh.edu>,
 *
 * based on code written for UNH CS416 Programming Assignment #9
 */
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

   /**
    * Pushes the specified double onto the operand stack.
    * 
    * @param num The double to push onto the operand stack.
    */
   private void pushDouble( double num )
   {
      _randStack.push( NodeFactory.createConstantNode( num ) );
   }

   /**
    * Pushes the specified variable onto the operand stack.
    * 
    * @param var The variable to push onto the operand stack.
    */
   private void pushVariable( String var )
   {
      _randStack.push( NodeFactory.createVariableNode( var ) );
   }

   /**
    * Returns the opposite symbol for a grouping symbol.
    * 
    * @param ch The grouping symbol encountered.
    * @return The opposite grouping symbol
    */
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
         if ( powPos.getIndex() >= 0 )
         {
            function = token.substring( 0, 
                  Math.min( powPos.getIndex(), argPos.getIndex() ) );
         }
         else
         {
            function = token.substring( 0, argPos.getIndex() );
         }
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

      pushTrigOpNode( function );
   }

   /**
    * Parses an operator at the specified position within the string.
    * 
    * @param token The entire string being examined.
    * @param pos The position within the string at which the operator occurred.
    * @throws ExpressionException
    */
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
            pushBinOpNode( _opStack.pop() );
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
         while ( ! _opStack.isEmpty() &&
               precedence( _opStack.peek() ) >= precedence( op ) )
         {
            pushBinOpNode( _opStack.pop() );
         }
         _opStack.push( op );
      }
   }

   /**
    * parseToken()
    * 
    * Parses a token in the input string.
    * 
    * @param token The string to parse. 
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
         pushBinOpNode( _opStack.pop() );
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
    * Builds the operator node from an operator and the top two items off of
    * the operand stack, and pushes it onto the operand stack.
    *
    * @param op The operator.
    * @param stack The operand stack.
    *
    * Throws ExpressionException if the expression is invalid.
    */
   private void pushBinOpNode( String op ) throws ExpressionException
   {
      AbstractNode leftNode, rightNode;

      if ( op.equals( "(" ) || op.equals( "{" ) || op.equals( "[" ) )
      {
         throw new ExpressionException( "Unterminated " + op, _infix );
      }

      if ( _randStack.size() < 2 )
      {
         throw new ExpressionException( "Not enough operands.", _infix );
      }
      
      rightNode = _randStack.pop();
      leftNode = _randStack.pop();

      _randStack.push( NodeFactory.createNode( op, leftNode, rightNode ) );
   }
   
   /**
    * Builds the trig operator node from an operator and the top two items off 
    * of the operand stack, and pushes it onto the operand stack.
    *
    * @param op The operator.
    * @param stack The operand stack.
    *
    * Throws ExpressionException if the expression is invalid.
    */
   private void pushTrigOpNode( String op ) throws ExpressionException
      {
         AbstractNode leftNode, rightNode, power;

         if ( op.equals( "(" ) || op.equals( "{" ) || op.equals( "[" ) )
         {
            throw new ExpressionException( "Unterminated " + op, _infix );
         }

         if ( _randStack.size() < 3 )
         {
            throw new ExpressionException( "Not enough operands.", _infix );
         }

         power = _randStack.pop();
         rightNode = _randStack.pop();
         leftNode = _randStack.pop();
         
         _randStack.push( NodeFactory.createTrigNode( 
                  op, leftNode, rightNode, power ) );
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
