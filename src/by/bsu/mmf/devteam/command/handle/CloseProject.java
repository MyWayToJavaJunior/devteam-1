package by.bsu.mmf.devteam.command.handle;

import by.bsu.mmf.devteam.command.Command;
import by.bsu.mmf.devteam.database.dao.ProjectDAO;
import by.bsu.mmf.devteam.database.dao.SpecificationDAO;
import by.bsu.mmf.devteam.database.dao.UserDAO;
import by.bsu.mmf.devteam.exception.infrastructure.CommandException;
import by.bsu.mmf.devteam.exception.infrastructure.DAOException;
import by.bsu.mmf.devteam.logic.bean.entity.Project;
import by.bsu.mmf.devteam.resource.ResourceManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Dmitry Petrovich
 * @since 2.0.0-beta
 */
public class CloseProject extends Command {
    private static final String PARAM_PROJECT_ID = "projectId";
    private static final String FORWARD_PAGE = "forward.manager.projects";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int pid = Integer.parseInt(request.getParameter(PARAM_PROJECT_ID));
        SpecificationDAO sDao = new SpecificationDAO();
        ProjectDAO pDao = new ProjectDAO();
        UserDAO uDao = new UserDAO();
        try {
            Project project = pDao.getProjectById(pid);
            uDao.exemptEmployees(project.getSpecification());
            sDao.setSpecificationStatus(project.getSpecification(), "completed");
        } catch (DAOException e) {
            throw new CommandException(",", e);
        }
        setForward(ResourceManager.getProperty(FORWARD_PAGE));
    }

}
