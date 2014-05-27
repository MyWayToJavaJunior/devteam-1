package by.bsu.mmf.devteam.command.handle;

import by.bsu.mmf.devteam.command.Command;
import by.bsu.mmf.devteam.database.dao.SpecificationDAO;
import by.bsu.mmf.devteam.exception.logic.CommandException;
import by.bsu.mmf.devteam.exception.data.DAOException;
import by.bsu.mmf.devteam.resource.ResourceManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Dmitry Petrovich
 * @since 1.0.0-alpha
 */
public class SkipPreparing extends Command {
    /* Initialize activity logger */
    private static Logger logger = Logger.getLogger("activity");

    /* Logger messages */
    private static final String MSG_EXECUTE_ERROR = "logger.error.execute.skip.preparing";
    private static final String MSG_ACTIVITY = "logger.activity.skip.preparing";

    /* Attributes and parameters */
    private static final String PARAM_WAITING_STATUS = "waiting";
    private static final String PARAM_SPECIFICATION_ID = "specId";

    /* Forward page */
    private static final String PARAM_DEFAULT_PAGE = "redirect.manager.default.page";

    /**
     * Implementation of command
     *
     * @param request HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws CommandException If an error has occurred on runtime
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int specId = Integer.parseInt(request.getParameter(PARAM_SPECIFICATION_ID));
        SpecificationDAO specificationDAO = new SpecificationDAO();
        try {
            specificationDAO.setSpecificationStatus(specId, PARAM_WAITING_STATUS);
        } catch (DAOException e) {
            throw new CommandException(ResourceManager.getProperty(MSG_EXECUTE_ERROR) + specId, e);
        }
        setForward(ResourceManager.getProperty(PARAM_DEFAULT_PAGE));
        logger.info(ResourceManager.getProperty(MSG_ACTIVITY) + specId);
    }

}
