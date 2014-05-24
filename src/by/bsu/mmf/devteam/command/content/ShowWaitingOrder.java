package by.bsu.mmf.devteam.command.content;

import by.bsu.mmf.devteam.command.Command;
import by.bsu.mmf.devteam.database.dao.JobDAO;
import by.bsu.mmf.devteam.database.dao.SpecificationDAO;
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
public class ShowWaitingOrder extends Command {
    /* Initialize activity logger */
    private static Logger logger = Logger.getLogger("activity");

    /* Logger messages */
    private static final String MSG_EXECUTE_ERROR = "logger.error.execute.waiting.order";
    private static final String MSG_REQUESTED = "logger.activity.requested.waiting.order";

    /* Attributes and parameters */
    private static final String USER_ATTRIBUTE = "user";
    private static final String PARAM_ORDER_ID_ATTRIBUTE = "orderId";
    private static final String PARAM_LIST_OF_ORDER_JOBS = "orderJobs";
    private static final String PARAM_CUSTOMER_ORDER_NAME = "orderName";

    /* Forward page */
    private static final String PARAM_FORWARD_PAGE = "forward.manager.project";

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
        int order = Integer.parseInt(request.getParameter(PARAM_ORDER_ID_ATTRIBUTE));
        SpecificationDAO dao = new SpecificationDAO();
        JobDAO jobDAO = new JobDAO();
        try {
            request.setAttribute(PARAM_CUSTOMER_ORDER_NAME, dao.getSpecificationName(order));
            request.setAttribute(PARAM_LIST_OF_ORDER_JOBS, jobDAO.getSpecificationJobs(order));
        } catch (DAOException e) {
            throw new CommandException(ResourceManager.getProperty(MSG_EXECUTE_ERROR) + user.getId(), e);
        }
        setForward(ResourceManager.getProperty(PARAM_FORWARD_PAGE));
        logger.info(ResourceManager.getProperty(MSG_REQUESTED) + user.getId());
    }

}
