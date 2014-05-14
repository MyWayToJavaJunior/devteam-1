package by.bsu.mmf.devteam.command.handle;

import by.bsu.mmf.devteam.command.Command;
import by.bsu.mmf.devteam.database.dao.JobDAO;
import by.bsu.mmf.devteam.database.dao.ProjectDAO;
import by.bsu.mmf.devteam.database.dao.UserDAO;
import by.bsu.mmf.devteam.exception.infrastructure.CommandException;
import by.bsu.mmf.devteam.exception.infrastructure.DAOException;
import by.bsu.mmf.devteam.logic.bean.entity.Job;
import by.bsu.mmf.devteam.logic.bean.user.User;
import by.bsu.mmf.devteam.resource.ResourceManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CreateProject extends Command {
    private static final String PARAM_USER_ATTRIBUTE = "user";
    private static final String PARAM_SPECIFICATION_ID = "specId";
    private static final String PARAM_SPECIFICATION_NAME = "nameOfNewSpec";
    private static final String FORWARD_CUSTOMER_PROJECTS = "redirect.manager.projects";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int specId = Integer.parseInt(request.getParameter(PARAM_SPECIFICATION_ID));
        JobDAO jDao = new JobDAO();
        UserDAO uDao = new UserDAO();
        ProjectDAO pDao = new ProjectDAO();
        try {
            List<Job> jobs = jDao.getSpecificationJobs(specId);
            for (Job job : jobs) {
                int cost = Integer.parseInt(request.getParameter("cost" + job.getId()));
                jDao.setJobCost(job.getId(), cost);
                String[] employees = request.getParameterValues("employees" + job.getId());
                for (String employee : employees) {
                    uDao.takeEmployee(job.getId(), employee.replace("%40","@"));
                }
            }
            String project = request.getParameter(PARAM_SPECIFICATION_NAME);
            User user = (User)request.getSession().getAttribute(PARAM_USER_ATTRIBUTE);
            pDao.saveProject(project, user.getId(), specId);
        } catch (DAOException e) {
            throw new CommandException(",", e);
        }
        setForward(ResourceManager.getProperty(FORWARD_CUSTOMER_PROJECTS));
    }

}
