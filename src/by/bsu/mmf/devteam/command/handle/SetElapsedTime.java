package by.bsu.mmf.devteam.command.handle;

import by.bsu.mmf.devteam.command.Command;
import by.bsu.mmf.devteam.database.dao.TimeDao;
import by.bsu.mmf.devteam.exception.logic.CommandException;
import by.bsu.mmf.devteam.exception.data.DAOException;
import by.bsu.mmf.devteam.logic.bean.user.User;
import by.bsu.mmf.devteam.resource.ResourceManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This this realizes command which sets elapsed time from employees.
 *
 * @author Dmitry Petrovich
 * @since 1.0.0-alpha
 */
public class SetElapsedTime extends Command {
    /* Initialize activity logger */
    private static Logger logger = Logger.getLogger("activity");

    /* Logger messages */
    private static final String MSG_EXECUTE_ERROR = "logger.error.execute.set.time";
    private static final String MSG_ACTIVITY = "logger.activity.set.time";
    private static final String MSG_INCORRECT_TIME = "logger.error.incorrect.time";

    /* Forward page */
    private static final String FORWARD_PAGE = "redirect.employee.default.page";

    /* Attributes and parameters */
    private static final String PARAM_JOB_ID = "jobId";
    private static final String PARAM_USER_ATTRIBUTE = "user";
    private static final String PARAM_NEW_TIME = "newTime";
    private static final String PARAM_RESULT_MESSAGE = "result";

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
        try {
            int job = Integer.parseInt(request.getParameter(PARAM_JOB_ID));
            int time = Integer.parseInt(request.getParameter(PARAM_NEW_TIME));
            TimeDao dao = new TimeDao();
            if (dao.isExistElapsedTime(user.getId(), job)) {
                dao.updateElapsedTime(user.getId(), job, time);
            } else {
                dao.saveElapsedTime(user.getId(), job, time);
            }
            request.setAttribute(PARAM_RESULT_MESSAGE, true);
        } catch (NumberFormatException e) {
            logger.error(ResourceManager.getProperty(MSG_INCORRECT_TIME) + user.getId(), e);
            request.setAttribute(PARAM_RESULT_MESSAGE, false);
            return;
        } catch (DAOException e) {
            throw new CommandException(ResourceManager.getProperty(MSG_EXECUTE_ERROR) + user.getId(), e);
        } finally {
            setForward(ResourceManager.getProperty(FORWARD_PAGE));
        }
        logger.info(ResourceManager.getProperty(MSG_ACTIVITY) + user.getId());
    }

}
