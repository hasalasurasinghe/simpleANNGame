package org.investovator.exeptions;

/**
 * @author rajith
 * @version $Revision$
 */
public class DAOException extends Exception {
    /**
     * Make a new exception.
     *
     * @param message the error message
     */
    public DAOException(String message) {
        super(message);
    }
}
