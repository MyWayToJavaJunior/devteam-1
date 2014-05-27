package by.bsu.mmf.devteam.command.handle;

import by.bsu.mmf.devteam.command.Command;
import by.bsu.mmf.devteam.database.dao.JobDAO;
import by.bsu.mmf.devteam.database.dao.QualificationDAO;
import by.bsu.mmf.devteam.database.dao.SpecificationDAO;
import by.bsu.mmf.devteam.exception.logic.CommandException;
import by.bsu.mmf.devteam.exception.data.DAOException;
import by.bsu.mmf.devteam.logic.bean.user.User;
import by.bsu.mmf.devteam.logic.bean.verifiable.ProposedJob;
import by.bsu.mmf.devteam.logic.bean.verifiable.ProposedOrder;
import by.bsu.mmf.devteam.logic.bean.verifiable.builder.VerifiableBuilder;
import by.bsu.mmf.devteam.logic.bean.verifiable.validator.VerifiableValidator;
import by.bsu.mmf.devteam.resource.ResourceManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * This class realizes command which save customer specifications.
 *
 * @author Dmitry Petrovich
 * @since 1.0.0-alpha
 */
public class CreateOrder extends Command {
    /** Initialize activity logger */
    private static Logger logger = Logger.getLogger("activity");

    /** Logger messages */
    private static final String MSG_EXECUTE_ERROR = "logger.error.execute.create.order";

    /** Attributes and parameters */
    private static final String ATTRIBUTE_USER = "user";
    private static final String ATTRIBUTE_FORM_CORRECT = "isFormCorrect";
    private static final String PARAM_FORWARD_PAGE = "redirect.customer.show.order.form";

    /**
     * Implementation of command
     *
     * @param request HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws CommandException If an error has occurred on runtime
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        ProposedOrder order = VerifiableBuilder.buildOrder(request);
        if (VerifiableValidator.isOrderValid(order)) {
            User user = (User) request.getSession().getAttribute(ATTRIBUTE_USER);
            SpecificationDAO sDao = new SpecificationDAO();
            JobDAO jDao = new JobDAO();
            QualificationDAO qDao = new QualificationDAO();
            try {
                int specId = sDao.saveSpecification(user.getId(), order.getSpecification());
                ArrayList<ProposedJob> jobs = order.getJobs();
                for (ProposedJob job : jobs) {
                    int qid = qDao.defineQualification(job.getQualification());
                    jDao.saveJob(specId, job.getName(), job.getCount(), qid);
                }
            } catch (DAOException e) {
                throw new CommandException(ResourceManager.getProperty(MSG_EXECUTE_ERROR), e);
            }
            request.setAttribute(ATTRIBUTE_FORM_CORRECT, true);
        } else {
            request.setAttribute(ATTRIBUTE_FORM_CORRECT, false);
        }
        setForward(ResourceManager.getProperty(PARAM_FORWARD_PAGE));
    }

}
