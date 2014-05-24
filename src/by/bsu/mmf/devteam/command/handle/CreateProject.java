package by.bsu.mmf.devteam.command.handle;

import by.bsu.mmf.devteam.command.Command;
import by.bsu.mmf.devteam.database.dao.*;
import by.bsu.mmf.devteam.exception.infrastructure.CommandException;
import by.bsu.mmf.devteam.exception.infrastructure.DAOException;
import by.bsu.mmf.devteam.logic.bean.entity.Job;
import by.bsu.mmf.devteam.logic.bean.user.User;
import by.bsu.mmf.devteam.logic.builders.BillNameBuilder;
import by.bsu.mmf.devteam.resource.ResourceManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 *
 * @author Dmitry Petrovich
 * @since 1.0.0-alpha
 */
public class CreateProject extends Command {
    /* Initialize activity logger */
    private static Logger logger = Logger.getLogger("activity");

    /* Logger messages */
    private static final String MSG_EXECUTE_ERROR = "";

    private static final String PARAM_USER_ATTRIBUTE = "user";
    private static final String PARAM_SPECIFICATION_ID = "specId";
    private static final String PARAM_SPEC_NAME = "nameOfNewSpec";
    private static final String FORWARD_CUSTOMER_PROJECTS = "redirect.manager.projects";

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
        JobDAO jDao = new JobDAO();
        BillDAO bDao = new BillDAO();
        UserDAO uDao = new UserDAO();
        ProjectDAO pDao = new ProjectDAO();
        SpecificationDAO sDao = new SpecificationDAO();
        try {
            List<Job> jobs = jDao.getSpecificationJobs(specId);
            int projectCost = 0;
            for (Job job : jobs) {
                int cost = Integer.parseInt(request.getParameter("cost" + job.getId()));
                projectCost += cost;
                jDao.setJobCost(job.getId(), cost);
                String[] employees = request.getParameterValues("employees" + job.getId());
                for (String employee : employees) {
                    uDao.takeEmployee(job.getId(), employee.replace("%40","@"));
                }
            }
            String projectName = new String(request.getParameter(PARAM_SPEC_NAME).getBytes("ISO-8859-1"), "CP1251");
            User user = (User)request.getSession().getAttribute(PARAM_USER_ATTRIBUTE);
            int pid = pDao.saveProject(projectName, user.getId(), specId);
            String billName = BillNameBuilder.createBillName(bDao.getLastBillName());
            int cid = sDao.getUserId(specId);
            bDao.createBill(billName, cid, pid, user.getId(), projectCost);
        } catch (DAOException e) {
            throw new CommandException(",", e);
        } catch (UnsupportedEncodingException e) {

        }
        setForward(ResourceManager.getProperty(FORWARD_CUSTOMER_PROJECTS));
    }

}
