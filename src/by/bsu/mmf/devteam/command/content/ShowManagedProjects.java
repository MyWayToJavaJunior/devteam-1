package by.bsu.mmf.devteam.command.content;

import by.bsu.mmf.devteam.command.Command;
import by.bsu.mmf.devteam.database.dao.ProjectDAO;
import by.bsu.mmf.devteam.database.dao.SpecificationDAO;
import by.bsu.mmf.devteam.database.dao.TimeDao;
import by.bsu.mmf.devteam.exception.infrastructure.CommandException;
import by.bsu.mmf.devteam.exception.infrastructure.DAOException;
import by.bsu.mmf.devteam.logic.bean.entity.Project;
import by.bsu.mmf.devteam.logic.bean.user.User;
import by.bsu.mmf.devteam.resource.ResourceManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author Dmitry Petrovich
 * @since 1.0.0-alpha
 */
public class ShowManagedProjects extends Command {
    private static final String PARAM_LIST_OF_PROJECT = "projectList";
    private static final String PARAM_USER_ATTRIBUTE = "user";
    private static final String PARAM_FORWARD_PAGE = "forward.manager.projects";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        User user = (User)request.getSession().getAttribute(PARAM_USER_ATTRIBUTE);
        ProjectDAO pDao = new ProjectDAO();
        TimeDao tDao = new TimeDao();
        SpecificationDAO sDao = new SpecificationDAO();
        try {
            List<Project> projects = pDao.getManagerProjects(user.getId());
            for(Project project : projects) {
                project.setStatus(sDao.getSpecificationStatus(project.getSpecification()));
                project.setTime(tDao.getTotalElapsedTimeOnProject(project.getSpecification()));
            }
            request.setAttribute(PARAM_LIST_OF_PROJECT, projects);
        } catch (DAOException e) {
            throw new CommandException(",", e);
        }
        setForward(ResourceManager.getProperty(PARAM_FORWARD_PAGE));
    }

}
