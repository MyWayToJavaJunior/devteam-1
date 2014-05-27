package by.bsu.mmf.devteam.command.content;

import by.bsu.mmf.devteam.command.Command;
import by.bsu.mmf.devteam.database.dao.BillDAO;
import by.bsu.mmf.devteam.database.dao.ProjectDAO;
import by.bsu.mmf.devteam.exception.logic.CommandException;
import by.bsu.mmf.devteam.exception.data.DAOException;
import by.bsu.mmf.devteam.logic.bean.entity.Bill;
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
 * @since 1.0.0-alpha
 */
public class ShowManagedBills extends Command {
    /* Initializes activity logger */
    private static Logger logger = Logger.getLogger("activity");

    /* Logger messages */
    private static final String MSG_EXECUTE_ERROR = "logger.error.execute.managed.bills";
    private static final String MSG_SHOW_BILLS = "logger.activity.manager.managed.bills";

    /* Attributes and parameters */
    private static final String ATTRIBUTE_USER = "user";
    private static final String PARAM_BILLS_LIST = "billsList";
    private static final String PARAM_NAMES_MAP  = "namesMap";

    /* Forward page */
    private static final String FORWARD_MANAGED_BILLS = "forward.manager.managed.bills";

    /**
     * Implementation of command
     *
     * @param request HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws CommandException If an error has occurred on runtime
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        User user = (User)request.getSession().getAttribute(ATTRIBUTE_USER);
        BillDAO bDao = new BillDAO();
        ProjectDAO pDao = new ProjectDAO();
        Map<Integer, String> names = new HashMap<>();
        try {
            List<Bill> bills = bDao.getManagerBills(user.getId());
            for(Bill bill : bills) {
                names.put(bill.getId(), pDao.getProjectById(bill.getPid()).getName());
            }
            request.setAttribute(PARAM_BILLS_LIST, bills);
            request.setAttribute(PARAM_NAMES_MAP, names);
        } catch (DAOException e) {
            throw new CommandException(ResourceManager.getProperty(MSG_EXECUTE_ERROR) + user.getId(), e);
        }
        setForward(ResourceManager.getProperty(FORWARD_MANAGED_BILLS));
        logger.info(ResourceManager.getProperty(MSG_SHOW_BILLS) + user.getId());
    }

}
