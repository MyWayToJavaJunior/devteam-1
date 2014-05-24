package by.bsu.mmf.devteam.command.handle;

import by.bsu.mmf.devteam.command.Command;
import by.bsu.mmf.devteam.database.dao.ProjectDAO;
import by.bsu.mmf.devteam.database.dao.SpecificationDAO;
import by.bsu.mmf.devteam.database.dao.UserDAO;
import by.bsu.mmf.devteam.exception.infrastructure.CommandException;
import by.bsu.mmf.devteam.exception.infrastructure.DAOException;
import by.bsu.mmf.devteam.logic.bean.entity.Project;
import by.bsu.mmf.devteam.resource.ResourceManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Dmitry Petrovich
 * @since 2.0.0-beta
 */
public class CloseProject extends Command {
    /* Initialize activity logger */
    private static Logger logger = Logger.getLogger("activity");

    /* Logger messages */
    private static final String MSG_EXECUTE_ERROR = "logger.error.execute.close.project";
    private static final String MSG_ACTION = "logger.activity.action.close.project";

    /* Parameters */
    private static final String PARAM_PROJECT_ID = "projectId";
    private static final String PARAM_COMPLETED = "completed";

    /* Forward page */
    private static final String FORWARD_PAGE = "forward.manager.projects";

    /**
     * Implementation of command
     *
     * @param request HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws CommandException If an error has occurred on runtime
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int pid = Integer.parseInt(request.getParameter(PARAM_PROJECT_ID));
        SpecificationDAO sDao = new SpecificationDAO();
        ProjectDAO pDao = new ProjectDAO();
        UserDAO uDao = new UserDAO();
        try {
            Project project = pDao.getProjectById(pid);
            uDao.exemptEmployees(project.getSpecification());
            sDao.setSpecificationStatus(project.getSpecification(), PARAM_COMPLETED);
        } catch (DAOException e) {
            throw new CommandException(ResourceManager.getProperty(MSG_EXECUTE_ERROR) + pid, e);
        }
        setForward(ResourceManager.getProperty(FORWARD_PAGE));
        logger.info(ResourceManager.getProperty(MSG_ACTION) + pid);
    }

}
