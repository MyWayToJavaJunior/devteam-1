package by.bsu.mmf.devteam.exception.logic;

import by.bsu.mmf.devteam.exception.InfrastructureException;

/**
 * This class defines exception which throws from commands. <br />
 * Need to be thrown if command execution can not be continued.
 *
 * @author Dmitry Petrovich
 * @since 1.0.0-alpha
 */
public class CommandException extends InfrastructureException {

    /**
     * Constructor
     *
     * @param s Message
     * @param throwable Exception object from lower level of business logic
     */
    public CommandException(String s, Throwable throwable) {
        super(s, throwable);
    }

}
