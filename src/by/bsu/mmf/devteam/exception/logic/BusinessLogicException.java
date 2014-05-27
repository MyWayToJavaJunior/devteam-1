package by.bsu.mmf.devteam.exception.logic;

import by.bsu.mmf.devteam.exception.InfrastructureException;

/**
 * This class defines exception which throws on business logic layer. <br />
 * Need to be thrown if business error has occurred.
 *
 * @author Dmitry Petrovich
 * @since 2.3.0-beta
 */
public class BusinessLogicException extends InfrastructureException {

    /**
     * Constructor
     *
     * @param s Message
     * @param throwable Exception object from lower level of business logic
     */
    public BusinessLogicException(String s, Throwable throwable) {
        super(s, throwable);
    }

}
