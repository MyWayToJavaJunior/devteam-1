package by.bsu.mmf.devteam.command.content;

import by.bsu.mmf.devteam.command.Command;
import by.bsu.mmf.devteam.database.dao.BillDAO;
import by.bsu.mmf.devteam.exception.logic.CommandException;
import by.bsu.mmf.devteam.exception.data.DAOException;
import by.bsu.mmf.devteam.logic.bean.user.User;
import by.bsu.mmf.devteam.resource.ResourceManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This command collects all bills for user and forward user to bill page.
 *
 * @author Dmitry Petrovich
 * @since 1.0.0-alpha
 */
public class ShowBills extends Command {
    /* Initializes activity logger */
    private static Logger logger = Logger.getLogger("activity");

    /* Logger messages */
    private static final String MSG_SHOW_BILLS = "logger.activity.customer.show.bills";
    private static final String MSG_ERROR_LOADING_BILLS = "logger.error.show.bills";

    /* Forward pages */
    private static final String CUSTOMER_BILLS_PAGE = "forward.customer.bills";

    /* Parameters */
    private static final String LIST_OF_BILLS = "billsList";
    private static final String USER_ATTRIBUTE = "user";

    /**
     * This method executes the command
     *
     * @param request HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws CommandException If command can't be executed
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        User user = (User)request.getSession().getAttribute(USER_ATTRIBUTE);
        BillDAO dao = new BillDAO();
        try {
            request.setAttribute(LIST_OF_BILLS, dao.getCustomerBills(user.getId()));
        } catch (DAOException e) {
            throw new CommandException(ResourceManager.getProperty(MSG_ERROR_LOADING_BILLS) + user.getId(), e);
        }
        setForward(ResourceManager.getProperty(CUSTOMER_BILLS_PAGE));
        logger.info(ResourceManager.getProperty(MSG_SHOW_BILLS) + user.getId());
    }

}
