package by.bsu.mmf.devteam.command.authorize;

import by.bsu.mmf.devteam.command.Command;
import by.bsu.mmf.devteam.exception.logic.CommandException;
import by.bsu.mmf.devteam.logic.bean.user.User;
import by.bsu.mmf.devteam.resource.ResourceManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Implementing command pattern.
 *
 * @author Dmitry Petrovich
 * @since 1.0.0-alpha
 */
public class Logout extends Command {
    /* Initializes activity logger */
    private static Logger logger = Logger.getLogger("activity");

    /* Logger messages */
    private static final String MSG_SIGN_OUT = "logger.activity.user.sign.out";

    /* Forward pages */
    private static final String LOGIN_PAGE = "forward.common.login";

    /* Attributes */
    private static final String USER_ATTRIBUTE = "user";

    /**
     * This method invalidates user session
     *
     * @param request HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws CommandException If execution is failed
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        User user = (User)request.getSession().getAttribute(USER_ATTRIBUTE);
        request.getSession().invalidate();
        setForward(ResourceManager.getProperty(LOGIN_PAGE));
        logger.info(ResourceManager.getProperty(MSG_SIGN_OUT) + user.getId());
    }

}
