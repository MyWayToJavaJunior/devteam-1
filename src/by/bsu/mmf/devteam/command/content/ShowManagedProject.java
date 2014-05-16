package by.bsu.mmf.devteam.command.content;

import by.bsu.mmf.devteam.command.Command;
import by.bsu.mmf.devteam.database.dao.*;
import by.bsu.mmf.devteam.exception.infrastructure.CommandException;
import by.bsu.mmf.devteam.exception.infrastructure.DAOException;
import by.bsu.mmf.devteam.logic.bean.entity.Job;
import by.bsu.mmf.devteam.logic.bean.entity.Project;
import by.bsu.mmf.devteam.logic.bean.user.User;
import by.bsu.mmf.devteam.resource.ResourceManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowManagedProject extends Command {
    private static final String FORWARD_TO_PROJECT_PAGE = "forward.manager.project";
    private static final String USER_ATTRIBUTE = "user";
    private static final String PARAM_PROJECT_OBJ = "projectObj";
    private static final String PARAM_PROJECT_ID = "projectId";
    private static final String PARAM_JOBS_LIST = "jobsList";
    private static final String PARAM_JOBS_MAP = "jobsMap";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        User user = (User)request.getSession().getAttribute(USER_ATTRIBUTE);
        int pid = Integer.parseInt(request.getParameter(PARAM_PROJECT_ID));
        SpecificationDAO sDao = new SpecificationDAO();
        ProjectDAO pDao = new ProjectDAO();
        JobDAO jDao = new JobDAO();
        UserDAO uDao = new UserDAO();
        TimeDao tDao = new TimeDao();
        try {
            Project project = pDao.getProject(pid);
            List<Job> jobs = jDao.getSpecificationJobs(project.getSpecification());
            Map<Integer, Map> detailedJobs = new HashMap<>();
            for (Job job : jobs) {
                Map<String, Integer> employee_time = new HashMap<>();
                List<String> mails = uDao.getEmployeeMailsWorkingOnJob(job.getId());
                for (String mail : mails) {
                    employee_time.put(mail, tDao.getEmployeeElapsedTimeOnProject(mail));
                }
                detailedJobs.put(job.getId(), employee_time);
            }
            request.setAttribute(PARAM_JOBS_LIST, jobs);
            request.setAttribute(PARAM_JOBS_MAP, detailedJobs);
            request.setAttribute(PARAM_PROJECT_OBJ, project);
        } catch (DAOException e) {
            throw new CommandException(",", e);
        } finally {
            setForward(ResourceManager.getProperty(FORWARD_TO_PROJECT_PAGE));
        }
    }

}
