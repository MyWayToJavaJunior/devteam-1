package by.bsu.mmf.devteam.command.content;

import by.bsu.mmf.devteam.command.Command;
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
public class ShowWaitingOrders extends Command {
    /* Initialize activity logger */
    private static Logger logger = Logger.getLogger("activity");

    /* Logger messages */
    private static final String MSG_EXECUTE_ERROR = "logger.error.execute.waiting.orders";
    private static final String MSG_REQUESTED = "logger.activity.requested.waiting.orders";

    /* Attributes and parameters */
    private static final String USER_ATTRIBUTE = "user";
    private static final String PARAM_ORDERS_LIST = "ordersList";

    /* Forward page */
    private static final String PARAM_FORWARD_WAITING_ORDERS_PAGE = "forward.manager.waiting.orders";

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
        SpecificationDAO sDao = new SpecificationDAO();
        try {
            request.setAttribute(PARAM_ORDERS_LIST, sDao.getWaitingSpecifications());
        } catch (DAOException e) {
            throw new CommandException(ResourceManager.getProperty(MSG_EXECUTE_ERROR) + user.getId(), e);
        }
        setForward(ResourceManager.getProperty(PARAM_FORWARD_WAITING_ORDERS_PAGE));
        logger.info(ResourceManager.getProperty(MSG_REQUESTED) + user.getId());
    }

}
