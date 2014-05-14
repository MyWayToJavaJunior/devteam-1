package by.bsu.mmf.devteam.command.content;

import by.bsu.mmf.devteam.command.Command;
import by.bsu.mmf.devteam.database.dao.JobDAO;
import by.bsu.mmf.devteam.database.dao.ProjectDAO;
import by.bsu.mmf.devteam.database.dao.UserDAO;
import by.bsu.mmf.devteam.exception.infrastructure.CommandException;
import by.bsu.mmf.devteam.exception.infrastructure.DAOException;
import by.bsu.mmf.devteam.logic.bean.entity.Job;
import by.bsu.mmf.devteam.logic.bean.entity.Project;
import by.bsu.mmf.devteam.logic.bean.user.User;
import by.bsu.mmf.devteam.resource.ResourceManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowCurrentJob extends Command {
    private static final String FORWARD_PAGE = "forward.employee.current.project";
    private static final String PARAM_USER_ATTRIBUTE = "user";
    private static final String PARAM_IS_FREE = "isFree";
    private static final String PARAM_PROJECT = "currentProject";
    private static final String PARAM_JOB = "employeeJob";
    private static final String PARAM_MANAGER_MAIL = "managerMail";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        User user = (User)request.getSession().getAttribute(PARAM_USER_ATTRIBUTE);
        UserDAO uDao = new UserDAO();
        JobDAO jDao = new JobDAO();
        ProjectDAO pDao = new ProjectDAO();
        try {
            if (uDao.isEmployeeFree(user.getId())) {
                request.setAttribute(PARAM_IS_FREE, true);
                Job job = jDao.getJobWhereEmployeeBusy(user.getId());
                request.setAttribute(PARAM_JOB, job);
                Project project = pDao.getProject(job.getSpecification());
                request.setAttribute(PARAM_PROJECT, project);
                request.setAttribute(PARAM_MANAGER_MAIL, uDao.getUserMail(project.getManager()));

            } else {
                request.setAttribute(PARAM_IS_FREE, false);
            }
        } catch (DAOException e) {
            throw new CommandException(", ", e);
        }
        setForward(ResourceManager.getProperty(FORWARD_PAGE));
    }

}
