package by.bsu.mmf.devteam.command.authorize;

import by.bsu.mmf.devteam.command.Command;
import by.bsu.mmf.devteam.database.dao.UserDAO;
import by.bsu.mmf.devteam.exception.logic.BusinessLogicException;
import by.bsu.mmf.devteam.exception.logic.CommandException;
import by.bsu.mmf.devteam.exception.data.DAOException;
import by.bsu.mmf.devteam.logic.bean.user.User;
import by.bsu.mmf.devteam.logic.bean.verifiable.SignInForm;
import by.bsu.mmf.devteam.logic.bean.verifiable.builder.VerifiableBuilder;
import by.bsu.mmf.devteam.logic.bean.verifiable.validator.VerifiableValidator;
import by.bsu.mmf.devteam.logic.builders.UserBuilder;
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
    /* Initializes activity logger */
    private static Logger logger = Logger.getLogger("activity");

    /* Logger messages */
    private static final String MSG_EXECUTE_ERROR = "logger.error.execute.login";
    private static final String MSG_SIGNED_IN = "logger.activity.user.signed.in";
    private static final String MSG_SIGN_FAILED = "logger.activity.user.sign.failed";

    /* Keeps session lifecycle */
    private static final int SESSION_LIFECYCLE = 600;

    /* Attributes and parameters */
    private static final String PARAM_USER = "user";
    private static final String PARAM_FORWARD_LOGIN = "forward.common.login";
    private static final String PARAM_REDIRECT_COMMAND = "controller?executionCommand=REDIRECT";
    private static final String PARAM_INCORRECT_MSG = "Incorrect login or password";
    private static final String ATTRIBUTE_INCORRECT_MSG = "errorLoginPasswordMessage";

    /**
     * Implementation of login command
     *
     * @param request HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws CommandException If errors occurred when executing command
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        SignInForm form = VerifiableBuilder.buildSignInForm(request);
        User user = (User)request.getSession().getAttribute(PARAM_USER);
        if (user == null) {
            try {
                user = UserBuilder.buildUser(form);
                setSessionLifecycle(request, user);
            } catch (BusinessLogicException e) {
                throw new CommandException(ResourceManager.getProperty(MSG_EXECUTE_ERROR), e);
            }
        }
        if (user != null) {
            logger.info(ResourceManager.getProperty(MSG_SIGNED_IN) + user.getId());
            setForward(PARAM_REDIRECT_COMMAND);
        } else {
            logger.info(ResourceManager.getProperty(MSG_SIGN_FAILED) + form.getLogin() + "," + form.getPassword());
            request.setAttribute(ATTRIBUTE_INCORRECT_MSG, PARAM_INCORRECT_MSG);
            setForward(ResourceManager.getProperty(PARAM_FORWARD_LOGIN));
        }
    }

    private void setSessionLifecycle(HttpServletRequest request, User user) {
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(SESSION_LIFECYCLE);
        session.setAttribute(PARAM_USER, user);
    }

}
