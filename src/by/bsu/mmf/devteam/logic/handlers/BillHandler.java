package by.bsu.mmf.devteam.logic.handlers;

import by.bsu.mmf.devteam.database.dao.BillDAO;
import by.bsu.mmf.devteam.database.dao.JobDAO;
import by.bsu.mmf.devteam.database.dao.ProjectDAO;
import by.bsu.mmf.devteam.database.dao.SpecificationDAO;
import by.bsu.mmf.devteam.exception.data.DAOException;
import by.bsu.mmf.devteam.exception.logic.BusinessLogicException;
import by.bsu.mmf.devteam.logic.bean.entity.Project;
import by.bsu.mmf.devteam.logic.builders.BillNameBuilder;
import by.bsu.mmf.devteam.resource.ResourceManager;

/**
 *
 * @author Dmitry Petrovich
 * @since 2.3.0-beta
 */
public class BillHandler {
    /** Logger messages */
    private static final String ERROR_SAVE_BILL = "logger.logic.error.save.bill";

    /**
     * This method saves new bill to database. <br />
     * Must be executed only if was successfully created project by customer.
     *
     * @param sid Specification id
     * @throws BusinessLogicException object if creation is failed
     */
    public static void saveBill(int sid) throws BusinessLogicException{
        BillDAO billDAO = new BillDAO();
        JobDAO jobDAO = new JobDAO();
        ProjectDAO projectDAO = new ProjectDAO();
        SpecificationDAO specDao = new SpecificationDAO();
        try {
            String name = BillNameBuilder.createBillName(billDAO.getLastBillName());
            int cost = jobDAO.getTotalCostOfSpecJobs(sid);
            Project project = projectDAO.getProject(sid);
            int customer = specDao.getUserId(sid);
            billDAO.createBill(name, customer, project.getId(), project.getManager(), cost);
        } catch (DAOException e) {
            throw new BusinessLogicException(ResourceManager.getProperty(ERROR_SAVE_BILL) + sid, e);
        }
    }

}
