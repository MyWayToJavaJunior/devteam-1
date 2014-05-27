package by.bsu.mmf.devteam.command.navigate;

import by.bsu.mmf.devteam.command.Command;
import by.bsu.mmf.devteam.exception.logic.CommandException;
import by.bsu.mmf.devteam.resource.ResourceManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Dmitry Petrovich
 * @since 1.0.0-alpha
 */
public class NotFound extends Command {
    /* Initialize activity logger */
    private static Logger logger = Logger.getLogger("activity");

    /* Logger messages */
    private static final String MSG_ACTIVITY = "logger.activity.requested.not.found";

    /* Forward page */
    private static final String FORWARD_NOT_FOUND = "forward.error.404";

    /* Parameters */
    private static final String PARAM_EXECUTION_COMMAND = "executionCommand";

    /**
     * Implementation of command
     *
     * @param request HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws CommandException If an error has occurred on runtime
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        setForward(ResourceManager.getProperty(FORWARD_NOT_FOUND));
        String command = request.getParameter(PARAM_EXECUTION_COMMAND);
        logger.info(ResourceManager.getProperty(MSG_ACTIVITY) + command);
    }

}
