package by.bsu.mmf.devteam.command.language;

import by.bsu.mmf.devteam.command.Command;
import by.bsu.mmf.devteam.exception.logic.CommandException;
import by.bsu.mmf.devteam.logic.bean.user.User;
import by.bsu.mmf.devteam.resource.ResourceManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Dmitry Petrovich
 * @since 1.0.0-alpha
 */
public class ChangeLanguage extends Command {
    /* Initialize activity logger */
    private static Logger logger = Logger.getLogger("activity");

    /* Logger messages */
    private static final String MSG_ACTIVITY = "logger.activity.change.language";

    /* Attributes and parameters */
    private static final String ATTRIBUTE_USER = "user";
    private static final String PARAM_LANGUAGE_CHOICE = "choice";

    /* Forward page */
    private static final String REDIRECT_TO_DEFAULT_PAGE = "forward.common.redirect.command";

    /**
     * Implementation of command
     *
     * @param request HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws CommandException If an error has occurred on runtime
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        User user = (User)request.getSession().getAttribute(ATTRIBUTE_USER);
        String lang = request.getParameter(PARAM_LANGUAGE_CHOICE);
        user.setLanguage(lang.equals("ru") ? "ru" : "en");
        setForward(ResourceManager.getProperty(REDIRECT_TO_DEFAULT_PAGE));
        logger.info(ResourceManager.getProperty(MSG_ACTIVITY) + user.getId() + ", " + lang);
    }

}
