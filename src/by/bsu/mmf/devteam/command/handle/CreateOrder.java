package by.bsu.mmf.devteam.command.handle;

import by.bsu.mmf.devteam.command.Command;
import by.bsu.mmf.devteam.database.dao.JobDAO;
import by.bsu.mmf.devteam.database.dao.QualificationDAO;
import by.bsu.mmf.devteam.database.dao.SpecificationDAO;
import by.bsu.mmf.devteam.exception.infrastructure.CommandException;
import by.bsu.mmf.devteam.exception.infrastructure.DAOException;
import by.bsu.mmf.devteam.logic.bean.user.User;
import by.bsu.mmf.devteam.resource.ResourceManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author Dmitry Petrovich
 * @since 1.0.0-alpha
 */
public class CreateOrder extends Command {
    /* Initialize activity logger */
    private static Logger logger = Logger.getLogger("activity");

    /* Logger messages */
    private static final String MSG_EXECUTE_ERROR = "";

    private static final String PARAM_USER = "user";
    private static final String PARAM_SPECIFICATION_NAME = "nameOfNewSpec";
    private static final String PARAM_FORWARD_PAGE = "forward.customer.add.specification";

    /**
     * Implementation of command
     *
     * @param request HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws CommandException If an error has occurred on runtime
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            User user = (User) request.getSession().getAttribute(PARAM_USER);
            SpecificationDAO sDao = new SpecificationDAO();
            JobDAO jDao = new JobDAO();
            QualificationDAO qDao = new QualificationDAO();
            try {
                String specName = new String(request.getParameter(PARAM_SPECIFICATION_NAME).getBytes("UTF-8"), "CP1251");
                int specId = sDao.saveSpecification(user.getId(), specName);
                int jobs = Integer.parseInt(request.getParameter("jobsCount"));
                for (int i = 1; i <= jobs; i++) {
                    String name = new String(request.getParameter("job" + i).getBytes("UTF-8"), "CP1251");
                    int count = Integer.parseInt(request.getParameter("count" + i));
                    String qualification = request.getParameter("qualification" + i);
                    int qid = qDao.defineQualification(qualification);
                    jDao.saveJob(specId, name, count, qid);
                }
            } catch (DAOException e) {
                throw new CommandException(",", e);
            } catch (UnsupportedEncodingException e) {

            }
            request.setAttribute("operationResult", "Specification was successfully saved.");
            setForward(ResourceManager.getProperty(PARAM_FORWARD_PAGE));
        }catch (NullPointerException e) {
            logger.error(e);
        }
    }

}
