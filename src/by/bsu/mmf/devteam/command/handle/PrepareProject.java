package by.bsu.mmf.devteam.command.handle;

import by.bsu.mmf.devteam.command.Command;
import by.bsu.mmf.devteam.database.dao.JobDAO;
import by.bsu.mmf.devteam.database.dao.SpecificationDAO;
import by.bsu.mmf.devteam.database.dao.UserDAO;
import by.bsu.mmf.devteam.exception.infrastructure.CommandException;
import by.bsu.mmf.devteam.exception.infrastructure.DAOException;
import by.bsu.mmf.devteam.logic.bean.entity.Job;
import by.bsu.mmf.devteam.resource.ResourceManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Dmitry Petrovich
 * @since 1.0.0-alpha
 */
public class PrepareProject extends Command {
    private static final String PARAM_PROCESSING_STATUS = "processing";
    private static final String PARAM_ORDER_ID = "orderId";
    private static final String PARAM_SPECIFICATION_NAME = "specName";
    private static final String PARAM_SPECIFICATION_ID = "specId";
    private static final String PARAM_JOBS_LIST = "jobsList";
    private static final String PARAM_MAILS_MAP = "mailsMap";
    private static final String PARAM_FORWARD_PAGE = "forward.manager.create.project";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        SpecificationDAO sDao = new SpecificationDAO();
        JobDAO jDao = new JobDAO();
        UserDAO uDao = new UserDAO();
        int order = Integer.parseInt(request.getParameter(PARAM_ORDER_ID));
        try {
            sDao.setSpecificationStatus(order, PARAM_PROCESSING_STATUS);
            request.setAttribute(PARAM_SPECIFICATION_ID, order);
            request.setAttribute(PARAM_SPECIFICATION_NAME, sDao.getSpecificationName(order));
            List<Job> jobs = jDao.getSpecificationJobs(order);
            request.setAttribute(PARAM_JOBS_LIST, jobs);
            Map<Integer, List<String>> mails = new HashMap<>();
            for (Job job : jobs) {
                mails.put(job.getId(), uDao.getFreeUsersMailsByQualification(job.getQualification()));
            }
            request.setAttribute(PARAM_MAILS_MAP, mails);
        } catch (DAOException e) {
            throw new CommandException(".", e);
        }
        setForward(ResourceManager.getProperty(PARAM_FORWARD_PAGE));
    }

}
