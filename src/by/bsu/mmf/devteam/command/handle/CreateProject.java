package by.bsu.mmf.devteam.command.handle;

import by.bsu.mmf.devteam.command.Command;
import by.bsu.mmf.devteam.database.dao.*;
import by.bsu.mmf.devteam.exception.logic.BusinessLogicException;
import by.bsu.mmf.devteam.exception.logic.CommandException;
import by.bsu.mmf.devteam.exception.data.DAOException;
import by.bsu.mmf.devteam.logic.bean.entity.Job;
import by.bsu.mmf.devteam.logic.bean.user.User;
import by.bsu.mmf.devteam.logic.bean.verifiable.PreparedProject;
import by.bsu.mmf.devteam.logic.bean.verifiable.builder.VerifiableBuilder;
import by.bsu.mmf.devteam.logic.bean.verifiable.validator.VerifiableValidator;
import by.bsu.mmf.devteam.logic.handlers.BillHandler;
import by.bsu.mmf.devteam.logic.handlers.ProjectHandler;
import by.bsu.mmf.devteam.resource.ResourceManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * This class realizes command which create new project from customer order.
 *
 * @author Dmitry Petrovich
 * @since 1.0.0-alpha
 */
public class CreateProject extends Command {
    /** Initialize activity logger */
    private static Logger logger = Logger.getLogger("activity");

    /** Logger messages */
    private static final String MSG_EXECUTE_ERROR = "logger.error.execute.create.project";

    /** Attributes and parameters */
    private static final String PARAM_USER_ATTRIBUTE = "user";
    private static final String PARAM_SPECIFICATION_ID = "specId";
    private static final String ATTRIBUTE_FORM_CORRECT = "isFormCorrect";
    private static final String FORWARD_CUSTOMER_PROJECTS = "redirect.manager.projects";
    private static final String FORWARD_PREPARE_PROJECT = "redirect.manager.prepare.project";

    /**
     * Implementation of command
     *
     * @param request HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws CommandException If an error has occurred on runtime
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        User user = (User)request.getSession().getAttribute(PARAM_USER_ATTRIBUTE);
        int specId = Integer.parseInt(request.getParameter(PARAM_SPECIFICATION_ID));
        PreparedProject project = VerifiableBuilder.buildProject(request);
        if (VerifiableValidator.isPreparedProjectValid(project)) {
            try {
                ProjectHandler.saveProject(project, user.getId());
                BillHandler.saveBill(specId);
            } catch (BusinessLogicException e) {
                throw new CommandException(ResourceManager.getProperty(MSG_EXECUTE_ERROR), e);
            }
            setForward(ResourceManager.getProperty(FORWARD_CUSTOMER_PROJECTS));
        } else {
            request.setAttribute(ATTRIBUTE_FORM_CORRECT, false);
            setForward(ResourceManager.getProperty(FORWARD_PREPARE_PROJECT)+"&orderId="+specId);
        }
    }

}
