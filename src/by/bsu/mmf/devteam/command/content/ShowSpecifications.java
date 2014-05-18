package by.bsu.mmf.devteam.command.content;

import by.bsu.mmf.devteam.command.Command;
import by.bsu.mmf.devteam.database.dao.SpecificationDAO;
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
public class ShowSpecifications extends Command {
    private static Logger logger = Logger.getLogger("activity");
    private static final String LIST_OF_SPECIFICATIONS = "specificationsList";
    private static final String PARAM_USER = "user";
    private static final String SPECIFICATIONS_PAGE = "forward.customer.specifications";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        User user = (User)request.getSession().getAttribute(PARAM_USER);
        SpecificationDAO dao = new SpecificationDAO();
        try {
            request.setAttribute(LIST_OF_SPECIFICATIONS, dao.getUserSpecifications(user.getId()));
        } catch (DAOException e) {
            throw new CommandException("Exception in ShowSpecification command", e);
        }
        setForward(ResourceManager.getProperty(SPECIFICATIONS_PAGE));
    }

}
