package by.bsu.mmf.devteam.logic.checker;

import by.bsu.mmf.devteam.command.CommandEnum;
import by.bsu.mmf.devteam.logic.bean.user.Role;
import by.bsu.mmf.devteam.logic.bean.user.User;

import javax.servlet.http.HttpServletRequest;

public class AccessChecker {
    private static final String USER = "user";

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
