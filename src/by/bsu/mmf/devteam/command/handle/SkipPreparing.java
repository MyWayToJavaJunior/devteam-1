package by.bsu.mmf.devteam.command.handle;

import by.bsu.mmf.devteam.command.Command;
import by.bsu.mmf.devteam.database.dao.SpecificationDAO;
import by.bsu.mmf.devteam.exception.infrastructure.CommandException;
import by.bsu.mmf.devteam.exception.infrastructure.DAOException;
import by.bsu.mmf.devteam.resource.ResourceManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SkipPreparing extends Command {
    private static final String PARAM_WAITING_STATUS = "waiting";
    private static final String PARAM_SPECIFICATION_ID = "specId";
    private static final String PARAM_DEFAULT_PAGE = "redirect.manager.default.page";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int specId = Integer.parseInt(request.getParameter(PARAM_SPECIFICATION_ID));
        SpecificationDAO specificationDAO = new SpecificationDAO();
        try {
            specificationDAO.setSpecificationStatus(specId, PARAM_WAITING_STATUS);
        } catch (DAOException e) {
            throw new CommandException(",", e);
        }
        setForward(ResourceManager.getProperty(PARAM_DEFAULT_PAGE));
    }

}
