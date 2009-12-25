/*
 * ExpressionException.java
 * 
 * This class based on code written for UNH CS416 Programming Assignment #9,
 * Spring 2009.
 *  
 * This file is part of CalcHelper.
 * 
 * CalcHelper is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * CalcHelper is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with CalcHelper.  If not, see <http://www.gnu.org/licenses/>.
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
