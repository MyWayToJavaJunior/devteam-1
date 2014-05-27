package by.bsu.mmf.devteam.command.content;

import by.bsu.mmf.devteam.command.Command;
import by.bsu.mmf.devteam.database.dao.SpecificationDAO;
import by.bsu.mmf.devteam.exception.logic.CommandException;
import by.bsu.mmf.devteam.exception.data.DAOException;
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
public class ShowSpecifications extends Command {
    /* Initialize activity logger */
    private static Logger logger = Logger.getLogger("activity");

    /* Logger messages */
    private static final String MSG_EXECUTE_ERROR = "logger.error.execute.specifications";
    private static final String MSG_REQUESTED = "logger.activity.requested.specifications";

    /* Attributes and parameters */
    private static final String LIST_OF_SPECIFICATIONS = "specificationsList";
    private static final String USER_ATTRIBUTE = "user";
    private static final String SPECIFICATIONS_PAGE = "forward.customer.specifications";

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
        SpecificationDAO dao = new SpecificationDAO();
        try {
            request.setAttribute(LIST_OF_SPECIFICATIONS, dao.getUserSpecifications(user.getId()));
        } catch (DAOException e) {
            throw new CommandException(ResourceManager.getProperty(MSG_EXECUTE_ERROR) + user.getId(), e);
        }
        setForward(ResourceManager.getProperty(SPECIFICATIONS_PAGE));
        logger.info(ResourceManager.getProperty(MSG_REQUESTED) + user.getId());
    }

}
