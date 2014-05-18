package by.bsu.mmf.devteam.command.handle;

import by.bsu.mmf.devteam.command.Command;
import by.bsu.mmf.devteam.database.dao.JobDAO;
import by.bsu.mmf.devteam.database.dao.TimeDao;
import by.bsu.mmf.devteam.exception.infrastructure.CommandException;
import by.bsu.mmf.devteam.exception.infrastructure.DAOException;
import by.bsu.mmf.devteam.logic.bean.user.User;
import by.bsu.mmf.devteam.resource.ResourceManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Dmitry Petrovich
 * @since 1.0.0-alpha
 */
public class SetElapsedTime extends Command {
    private static Logger logger = Logger.getLogger("activity");
    private static final String FORWARD_PAGE = "redirect.employee.default.page";
    private static final String PARAM_JOB_ID = "jobId";
    private static final String PARAM_USER_ATTRIBUTE = "user";
    private static final String PARAM_NEW_TIME = "newTime";
    private static final String PARAM_RESULT_MESSAGE = "result";

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
            logger.error("Incorrect elapsed time. Employee id = " + user.getId(), e);
            request.setAttribute(PARAM_RESULT_MESSAGE, false);
            return;
        } catch (DAOException e) {
            throw new CommandException(",", e);
        } finally {
            setForward(ResourceManager.getProperty(FORWARD_PAGE));
        }
    }

}
