package by.bsu.mmf.devteam.command.navigate;

import by.bsu.mmf.devteam.command.Command;
import by.bsu.mmf.devteam.exception.infrastructure.CommandException;
import by.bsu.mmf.devteam.logic.bean.user.User;
import by.bsu.mmf.devteam.resource.ResourceManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RedirectToDefault extends Command {
    private static final String EXECUTION_COMMAND = "executionCommand";
    private static final String PARAM_USER = "user";
    private static final String FORWARD_DEFAULT_CUSTOMER_PAGE = "redirect.customer.default.page";
    private static final String FORWARD_DEFAULT_EMPLOYEE_PAGE = "redirect.employee.default.page";
    private static final String FORWARD_DEFAULT_MANAGER_PAGE = "redirect.manager.default.page";
    private static final String NOT_FOUND = "forward.error.404";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        User user = (User)request.getSession().getAttribute(PARAM_USER);
        switch (user.getRole()) {
            case CUSTOMER:
                setForward(ResourceManager.getProperty(FORWARD_DEFAULT_CUSTOMER_PAGE));
                break;
            case EMPLOYEE:
                setForward(ResourceManager.getProperty(FORWARD_DEFAULT_EMPLOYEE_PAGE));
                break;
            case MANAGER:
                setForward(ResourceManager.getProperty(FORWARD_DEFAULT_MANAGER_PAGE));
                break;
            default:
                setForward(ResourceManager.getProperty(NOT_FOUND));
                break;
        }
    }

}
