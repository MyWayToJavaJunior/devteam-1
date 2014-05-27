package by.bsu.mmf.devteam.command.handle;

import by.bsu.mmf.devteam.command.Command;
import by.bsu.mmf.devteam.database.dao.JobDAO;
import by.bsu.mmf.devteam.database.dao.SpecificationDAO;
import by.bsu.mmf.devteam.database.dao.UserDAO;
import by.bsu.mmf.devteam.exception.logic.CommandException;
import by.bsu.mmf.devteam.exception.data.DAOException;
import by.bsu.mmf.devteam.logic.bean.entity.Job;
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
 * @since 1.0.0-alpha
 */
public class PrepareProject extends Command {
    /* Initialize activity logger */
    private static Logger logger = Logger.getLogger("activity");

    /* Logger messages */
    private static final String MSG_EXECUTE_ERROR = "logger.error.execute.prepare.project";
    private static final String MSG_ACTIVITY = "logger.activity.prepare.project";

    /* Attributes and parameters */
    private static final String PARAM_PROCESSING_STATUS = "processing";
    private static final String PARAM_ORDER_ID = "orderId";
    private static final String PARAM_SPECIFICATION_NAME = "specName";
    private static final String PARAM_SPECIFICATION_ID = "specId";
    private static final String PARAM_JOBS_LIST = "jobsList";
    private static final String PARAM_MAILS_MAP = "mailsMap";

    /* Forward page */
    private static final String PARAM_FORWARD_PAGE = "forward.manager.create.project";

    /**
     * Implementation of command
     *
     * @param request HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws CommandException If an error has occurred on runtime
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        SpecificationDAO sDao = new SpecificationDAO();
        JobDAO jDao = new JobDAO();
        UserDAO uDao = new UserDAO();
        int order = Integer.parseInt(request.getParameter(PARAM_ORDER_ID));
        try {
            sDao.setSpecificationStatus(order, PARAM_PROCESSING_STATUS);
            request.setAttribute(PARAM_SPECIFICATION_ID, order);
            request.setAttribute(PARAM_SPECIFICATION_NAME, sDao.getSpecificationName(order));
            List<Job> jobs = jDao.getSpecificationJobs(order);
            request.setAttribute(PARAM_JOBS_LIST, jobs);
            Map<Integer, List<String>> mails = new HashMap<>();
            for (Job job : jobs) {
                mails.put(job.getId(), uDao.getFreeUsersMailsByQualification(job.getQualification()));
            }
            request.setAttribute(PARAM_MAILS_MAP, mails);
        } catch (DAOException e) {
            throw new CommandException(ResourceManager.getProperty(MSG_EXECUTE_ERROR) + order, e);
        }
        setForward(ResourceManager.getProperty(PARAM_FORWARD_PAGE));
        logger.info(ResourceManager.getProperty(MSG_ACTIVITY) + order);
    }

}
