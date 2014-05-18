package by.bsu.mmf.devteam.command.authorize;

import by.bsu.mmf.devteam.command.Command;
import by.bsu.mmf.devteam.database.dao.UserDAO;
import by.bsu.mmf.devteam.exception.infrastructure.CommandException;
import by.bsu.mmf.devteam.exception.infrastructure.DAOException;
import by.bsu.mmf.devteam.logic.bean.user.User;
import by.bsu.mmf.devteam.resource.ResourceManager;
import by.bsu.mmf.devteam.util.Hasher;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * This class realizes login command
 *
 * @author Dmitry Petrovich
 * @since 1.0.0-alpha
 */
public class Login extends Command {
    /* Initialize activity logger */
    private static Logger logger = Logger.getLogger("activity");

    /* Keeps session lifecycle */
    private static final int SESSION_LIFECYCLE = 600;

    private static final String PARAM_USER = "user";
    private static final String PARAM_NAME_EMAIL = "email";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String PARAM_FORWARD_LOGIN = "forward.common.login";
    private static final String PARAM_REDIRECT_COMMAND = "controller?executionCommand=REDIRECT";

    /**
     * Implementation of login command
     *
     * @param request HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws CommandException If errors occurred when executing command
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String email = request.getParameter(PARAM_NAME_EMAIL);
        String password = request.getParameter(PARAM_NAME_PASSWORD);
        UserDAO dao = new UserDAO();
        User user = (User)request.getSession().getAttribute(PARAM_USER);
        if (user == null) {
            if (email != null && password != null) {
                try {
                    user = dao.getUser(email, Hasher.getMD5(password));
                    HttpSession session = request.getSession();
                    session.setMaxInactiveInterval(SESSION_LIFECYCLE);
                    session.setAttribute(PARAM_USER, user);
                } catch (DAOException e) {
                    throw new CommandException("An error has occurred when searching user in DB", e);
                }
            }
        }
        if (user != null) {
            setForward(PARAM_REDIRECT_COMMAND);
        } else {
            request.setAttribute("errorLoginPasswordMessage", "Incorrect login or password");
            setForward(ResourceManager.getProperty(PARAM_FORWARD_LOGIN));
        }
    }

}
