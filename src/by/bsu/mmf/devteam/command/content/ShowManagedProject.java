package by.bsu.mmf.devteam.command.content;

import by.bsu.mmf.devteam.command.Command;
import by.bsu.mmf.devteam.database.dao.*;
import by.bsu.mmf.devteam.exception.infrastructure.CommandException;
import by.bsu.mmf.devteam.exception.infrastructure.DAOException;
import by.bsu.mmf.devteam.logic.bean.entity.Job;
import by.bsu.mmf.devteam.logic.bean.entity.Project;
import by.bsu.mmf.devteam.logic.bean.user.User;
import by.bsu.mmf.devteam.resource.ResourceManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Dmitry Petrovich
 * @since 2.0.0-beta
 */
public class ShowManagedProject extends Command {
    /* Initializes activity logger */
    private static Logger logger = Logger.getLogger("activity");

    /* Logger messages */
    private static final String MSG_EXECUTE_ERROR = "logger.error.execute.managed.project";
    private static final String MSG_MANAGED_PROJECT = "logger.activity.manager.managed.project";

    /* Forward pages */
    private static final String FORWARD_TO_PROJECT_PAGE = "forward.manager.project";

    /* Attributes and parameters */
    private static final String USER_ATTRIBUTE = "user";
    private static final String PARAM_PROJECT_NAME = "projectName";
    private static final String PARAM_PROJECT_ID = "projectId";
    private static final String PARAM_JOBS_LIST = "jobsList";
    private static final String PARAM_JOBS_MAP = "jobsMap";

    /**
     * Implementation of command
     *
     * @param request HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws CommandException If an error has occurred on runtime
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        User user = (User)request.getSession().getAttribute(USER_ATTRIBUTE);
        int pid = Integer.parseInt(request.getParameter(PARAM_PROJECT_ID));
        ProjectDAO pDao = new ProjectDAO();
        JobDAO jDao = new JobDAO();
        UserDAO uDao = new UserDAO();
        TimeDao tDao = new TimeDao();
        try {
            Project project = pDao.getProjectById(pid);
            List<Job> jobs = jDao.getSpecificationJobs(project.getSpecification());
            Map<Integer, Map> detailedJobs = new HashMap<>();
            for (Job job : jobs) {
                Map<String, Integer> employee_time = new HashMap<>();
                List<String> mails = uDao.getEmployeeMailsWorkingOnJob(job.getId());
                for (String mail : mails) {
                    employee_time.put(mail, tDao.getEmployeeElapsedTimeOnProject(mail));
                }
                detailedJobs.put(job.getId(), employee_time);
            }
            request.setAttribute(PARAM_JOBS_LIST, jobs);
            request.setAttribute(PARAM_JOBS_MAP, detailedJobs);
            request.setAttribute(PARAM_PROJECT_NAME, project.getName());
        } catch (DAOException e) {
            throw new CommandException(ResourceManager.getProperty(MSG_EXECUTE_ERROR) + user.getId(), e);
        } finally {
            setForward(ResourceManager.getProperty(FORWARD_TO_PROJECT_PAGE));
        }
        logger.info(ResourceManager.getProperty(MSG_MANAGED_PROJECT) + user.getId());
    }

}
