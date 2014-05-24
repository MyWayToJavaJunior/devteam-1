package by.bsu.mmf.devteam.command.content;

import by.bsu.mmf.devteam.command.Command;
import by.bsu.mmf.devteam.database.dao.QualificationDAO;
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
public class ShowOrderForm extends Command {
    /* Initializes activity logger */
    private static Logger logger = Logger.getLogger("activity");

    /* Logger messages */
    private static final String MSG_EXECUTE_ERROR = "logger.error.execute.order.form";
    private static final String MSG_REQUESTED_COMMAND = "logger.activity.requested.order.form";

    /* Attributes and parameters */
    private static final String PARAM_QUALIFICATIONS = "qualifications";
    private static final String USER_ATTRIBUTE = "user";

    /* Forward page */
    private static final String FORWARD_ORDER_FORM = "forward.customer.add.specification";

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
        QualificationDAO dao = new QualificationDAO();
        try {
            request.setAttribute(PARAM_QUALIFICATIONS, dao.getAllQualifications());
        } catch (DAOException e) {
            throw new CommandException(ResourceManager.getProperty(MSG_EXECUTE_ERROR) + user.getId(), e);
        }
        setForward(ResourceManager.getProperty(FORWARD_ORDER_FORM));
        logger.info(ResourceManager.getProperty(MSG_REQUESTED_COMMAND) + user.getId());
    }

}
