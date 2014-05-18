package by.bsu.mmf.devteam.logic.checker;

import by.bsu.mmf.devteam.command.CommandEnum;
import by.bsu.mmf.devteam.logic.bean.user.Role;
import by.bsu.mmf.devteam.logic.bean.user.User;

import javax.servlet.http.HttpServletRequest;

/**
 * This class for check user permissions
 *
 * @author Dmitry Petrovich
 * @since 1.0.0-alpha
 */
public class AccessChecker {
    /* Keeps name of user attribute in session */
    private static final String USER = "user";

    /**
     * This method check permissions of all application users
     *
     * @param request HttpServletRequest object
     * @param command Requested command
     * @return True if access is allowed, otherwise false
     */
    public static boolean checkPermission(HttpServletRequest request, CommandEnum command) {
        User user = (User) request.getSession().getAttribute(USER);
        if (user == null) {
            return false;
        } else if (command.getUserType() == Role.ALL) {
            return true;
        }
        return command.getUserType() == user.getRole();
    }

}
