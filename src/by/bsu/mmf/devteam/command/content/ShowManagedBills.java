package by.bsu.mmf.devteam.command.content;

import by.bsu.mmf.devteam.command.Command;
import by.bsu.mmf.devteam.database.dao.BillDAO;
import by.bsu.mmf.devteam.exception.infrastructure.CommandException;
import by.bsu.mmf.devteam.exception.infrastructure.DAOException;
import by.bsu.mmf.devteam.logic.bean.user.User;
import by.bsu.mmf.devteam.resource.ResourceManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowManagedBills extends Command {
    private static final String ATTRIBUTE_USER = "user";
    private static final String PARAM_BILLS_LIST = "billsList";
    private static final String FORWARD_MANAGED_BILLS = "forward.manager.managed.bills";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        User user = (User)request.getSession().getAttribute(ATTRIBUTE_USER);
        BillDAO bDao = new BillDAO();
        try {
            request.setAttribute(PARAM_BILLS_LIST, bDao.getManagerBills(user.getId()));
        } catch (DAOException e) {
            throw new CommandException(",", e);
        }
        setForward(ResourceManager.getProperty(FORWARD_MANAGED_BILLS));
    }

}
