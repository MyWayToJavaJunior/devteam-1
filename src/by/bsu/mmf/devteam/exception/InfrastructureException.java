package by.bsu.mmf.devteam.exception;

/**
 * This class defines abstract class for application exceptions.
 *
 * @author Dmitry Petrovich
 * @since 1.0.0-alpha
 */
public abstract class InfrastructureException extends Exception {

    /**
     * Constructor
     *
     * @param s Message
     * @param throwable Exception object
     */
    public InfrastructureException(String s, Throwable throwable) {
        super(s, throwable);
    }

}
