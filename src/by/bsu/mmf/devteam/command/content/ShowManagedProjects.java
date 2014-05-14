package by.bsu.mmf.devteam.command.content;

import by.bsu.mmf.devteam.command.Command;
import by.bsu.mmf.devteam.database.dao.ProjectDAO;
import by.bsu.mmf.devteam.exception.infrastructure.CommandException;
import by.bsu.mmf.devteam.exception.infrastructure.DAOException;
import by.bsu.mmf.devteam.logic.bean.user.User;
import by.bsu.mmf.devteam.resource.ResourceManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowManagedProjects extends Command {
    private static final String PARAM_LIST_OF_PROJECT = "projectList";
    private static final String PARAM_USER_ATTRIBUTE = "user";
    private static final String PARAM_FORWARD_PAGE = "forward.manager.projects";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        User user = (User)request.getSession().getAttribute(PARAM_USER_ATTRIBUTE);
        ProjectDAO dao = new ProjectDAO();
        try {
            request.setAttribute(PARAM_LIST_OF_PROJECT, dao.getManagerProjects(user.getId()));
        } catch (DAOException e) {
            throw new CommandException(",", e);
        }
        setForward(ResourceManager.getProperty(PARAM_FORWARD_PAGE));
    }

}
