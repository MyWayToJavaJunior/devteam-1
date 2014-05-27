package by.bsu.mmf.devteam.command.content;

import by.bsu.mmf.devteam.command.Command;
import by.bsu.mmf.devteam.database.dao.JobDAO;
import by.bsu.mmf.devteam.database.dao.ProjectDAO;
import by.bsu.mmf.devteam.database.dao.TimeDao;
import by.bsu.mmf.devteam.database.dao.UserDAO;
import by.bsu.mmf.devteam.exception.logic.CommandException;
import by.bsu.mmf.devteam.exception.data.DAOException;
import by.bsu.mmf.devteam.logic.bean.entity.Job;
import by.bsu.mmf.devteam.logic.bean.entity.Project;
import by.bsu.mmf.devteam.logic.bean.user.User;
import by.bsu.mmf.devteam.resource.ResourceManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class realize command which show employee job in which he works.
 *
 * @author Dmitry Petrovich
 * @since 1.0.0-alpha
 */
public class ShowCurrentJob extends Command {
    /* Initializes activity logger */
    private static Logger logger = Logger.getLogger("activity");

    /* Logger messages */
    private static final String MSG_SHOW_JOB_ERROR = "logger.error.show.current.job";
    private static final String MSG_SHOW_JOB = "logger.activity.employee.show.job";

    /* Forward pages */
    private static final String FORWARD_PAGE = "forward.employee.current.project";

    /* Page parameters */
    private static final String PARAM_USER_ATTRIBUTE = "user";
    private static final String PARAM_IS_FREE = "isFree";
    private static final String PARAM_PROJECT = "currentProject";
    private static final String PARAM_JOB = "employeeJob";
    private static final String PARAM_MANAGER_MAIL = "managerMail";

    /**
     * Implementation of command
     *
     * @param request HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws CommandException If an error has occurred on runtime
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        User user = (User)request.getSession().getAttribute(PARAM_USER_ATTRIBUTE);
        UserDAO uDao = new UserDAO();
        JobDAO jDao = new JobDAO();
        ProjectDAO pDao = new ProjectDAO();
        TimeDao tDao = new TimeDao();
        try {
            if (uDao.isEmployeeFree(user.getId())) {
                request.setAttribute(PARAM_IS_FREE, true);
                Job job = jDao.getJobWhereEmployeeBusy(user.getId());
                job.setTime(tDao.getExistingElapsedTime(user.getId(), job.getId()));
                request.setAttribute(PARAM_JOB, job);
                Project project = pDao.getProject(job.getSpecification());
                request.setAttribute(PARAM_PROJECT, project);
                request.setAttribute(PARAM_MANAGER_MAIL, uDao.getUserMail(project.getManager()));
            } else {
                request.setAttribute(PARAM_IS_FREE, false);
            }
        } catch (DAOException e) {
            throw new CommandException(ResourceManager.getProperty(MSG_SHOW_JOB_ERROR) + user.getId(), e);
        }
        setForward(ResourceManager.getProperty(FORWARD_PAGE));
        logger.info(ResourceManager.getProperty(MSG_SHOW_JOB) + user.getId());
    }

}
