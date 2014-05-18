package by.bsu.mmf.devteam.command.content;

import by.bsu.mmf.devteam.command.Command;
import by.bsu.mmf.devteam.database.dao.SpecificationDAO;
import by.bsu.mmf.devteam.exception.infrastructure.CommandException;
import by.bsu.mmf.devteam.exception.infrastructure.DAOException;
import by.bsu.mmf.devteam.resource.ResourceManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Dmitry Petrovich
 * @since 1.0.0-alpha
 */
public class ShowWaitingOrders extends Command {
    private static final String PARAM_FORWARD_WAITING_ORDERS_PAGE = "forward.manager.waiting.orders";
    private static final String PARAM_ORDERS_LIST = "ordersList";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        SpecificationDAO sDao = new SpecificationDAO();
        try {
            request.setAttribute(PARAM_ORDERS_LIST, sDao.getWaitingSpecifications());
        } catch (DAOException e) {
            e.printStackTrace();
        }
        setForward(ResourceManager.getProperty(PARAM_FORWARD_WAITING_ORDERS_PAGE));
    }

}
