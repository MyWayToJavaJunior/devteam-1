package by.bsu.mmf.devteam.command.language;

import by.bsu.mmf.devteam.command.Command;
import by.bsu.mmf.devteam.exception.infrastructure.CommandException;
import by.bsu.mmf.devteam.logic.bean.user.User;
import by.bsu.mmf.devteam.resource.ResourceManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Dmitry Petrovich
 * @since 1.0.0-alpha
 */
public class ChangeLanguage extends Command {
    private static final String ATTRIBUTE_USER = "user";
    private static final String REDIRECT_TO_DEFAULT_PAGE = "forward.common.redirect.command";
    private static final String PARAM_LANGUAGE_CHOICE = "choice";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        User user = (User)request.getSession().getAttribute(ATTRIBUTE_USER);
        String lang = request.getParameter(PARAM_LANGUAGE_CHOICE);
        switch (lang) {
            case "ru":
                user.setLanguage("ru");
                break;
            case "en":
                user.setLanguage("en");
                break;
        }
        setForward(ResourceManager.getProperty(REDIRECT_TO_DEFAULT_PAGE));
    }

}
