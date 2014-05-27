package by.bsu.mmf.devteam.logic.handlers;

import by.bsu.mmf.devteam.database.dao.JobDAO;
import by.bsu.mmf.devteam.database.dao.ProjectDAO;
import by.bsu.mmf.devteam.database.dao.UserDAO;
import by.bsu.mmf.devteam.exception.data.DAOException;
import by.bsu.mmf.devteam.exception.logic.BusinessLogicException;
import by.bsu.mmf.devteam.logic.bean.verifiable.PreparedJob;
import by.bsu.mmf.devteam.logic.bean.verifiable.PreparedProject;
import by.bsu.mmf.devteam.resource.ResourceManager;

import java.util.ArrayList;

/**
 *
 * @author Dmitry Petrovich
 * @since 2.3.0-beta
 */
public class ProjectHandler {
    /** Logger messages */
    private static final String ERROR_SAVE_PROJECT = "logger.logic.error.save.project";

    public static void saveProject(PreparedProject project, int mid) throws BusinessLogicException {
        ProjectDAO projectDAO = new ProjectDAO();
        JobDAO jobDAO = new JobDAO();
        UserDAO userDAO = new UserDAO();
        ArrayList<PreparedJob> jobs = project.getJobs();
        try {
            for (PreparedJob job : jobs) {
                jobDAO.setJobCost(job.getId(), job.getCost());
                ArrayList<String> mails = job.getEmployees();
                for (String mail : mails) {
                    userDAO.takeEmployee(job.getId(), mail.replace("%40","@"));
                }
            }
            projectDAO.saveProject(project.getName(), mid, project.getId());
        } catch (DAOException e) {
            throw new BusinessLogicException(ResourceManager.getProperty(ERROR_SAVE_PROJECT) + project.getId(), e);
        }
    }

}
