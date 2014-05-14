package by.bsu.mmf.devteam.command.content;

import by.bsu.mmf.devteam.command.Command;
import by.bsu.mmf.devteam.database.dao.BillDAO;
import by.bsu.mmf.devteam.exception.infrastructure.CommandException;
import by.bsu.mmf.devteam.exception.infrastructure.DAOException;
import by.bsu.mmf.devteam.logic.bean.user.User;
import by.bsu.mmf.devteam.resource.ResourceManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowBills extends Command {
    private static Logger logger = Logger.getLogger("activity");
    private static final String CUSTOMER_BILLS_PAGE = "forward.customer.bills";
    private static final String LIST_OF_BILLS = "billsList";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        User user = (User)request.getSession().getAttribute("user");
        BillDAO dao = new BillDAO();
        try {
            request.setAttribute(LIST_OF_BILLS, dao.getCustomerBills(user.getId()));
        } catch (DAOException e) {
            throw new CommandException("An error has occured to show customer bills.", e);
        }
        setForward(ResourceManager.getProperty(CUSTOMER_BILLS_PAGE));
    }

}
