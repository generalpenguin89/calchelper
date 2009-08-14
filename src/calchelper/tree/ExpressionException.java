/*
 * ExpressionException.java
 * 
 * Based on code written for UNH CS416 Programming Assignment #9, Spring 2009.
 */

package calchelper.tree;

/**
 *
 * An exception that can be thrown by the various expression tree classes.
 *
 * @author Patrick MacArthur
 */
public class ExpressionException extends java.lang.Exception
{
	private static final long serialVersionUID = 1L;
	
	private String _infix; // the infix expression

   /**
    * Creates a new exception object.
    *
    * @param message A user-readable message describing the reason for the
    * exception.
    * @param infix The infix expression that resulted in the exception.
    */
   public ExpressionException( String message, String infix )
   {
      super( message );
      _infix = infix;
   }

   /**
    * Returns a string representing the exception that occurred.
    */
   public String toString()
   {
      return getMessage() + "\nExpression: " + _infix;
   }
}
