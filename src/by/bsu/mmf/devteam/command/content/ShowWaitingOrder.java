package by.bsu.mmf.devteam.command.content;

import by.bsu.mmf.devteam.command.Command;
import by.bsu.mmf.devteam.database.dao.JobDAO;
import by.bsu.mmf.devteam.database.dao.SpecificationDAO;
import by.bsu.mmf.devteam.exception.infrastructure.CommandException;
import by.bsu.mmf.devteam.exception.infrastructure.DAOException;
import by.bsu.mmf.devteam.resource.ResourceManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowWaitingOrder extends Command {
    private static final String PARAM_ORDER_ID_ATTRIBUTE = "orderId";
    private static final String PARAM_LIST_OF_ORDER_JOBS = "orderJobs";
    private static final String PARAM_CUSTOMER_ORDER_NAME = "orderName";
    private static final String PARAM_FORWARD_PAGE = "forward.manager.project";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int order = Integer.parseInt(request.getParameter(PARAM_ORDER_ID_ATTRIBUTE));
        SpecificationDAO dao = new SpecificationDAO();
        JobDAO jobDAO = new JobDAO();
        try {
            request.setAttribute(PARAM_CUSTOMER_ORDER_NAME, dao.getSpecificationName(order));
            request.setAttribute(PARAM_LIST_OF_ORDER_JOBS, jobDAO.getSpecificationJobs(order));
        } catch (DAOException e) {
            throw new CommandException(".", e);
        }
        setForward(ResourceManager.getProperty(PARAM_FORWARD_PAGE));
    }

}
