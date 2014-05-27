package by.bsu.mmf.devteam.logic.bean.verifiable.validator;

import by.bsu.mmf.devteam.database.dao.UserDAO;
import by.bsu.mmf.devteam.exception.data.DAOException;
import by.bsu.mmf.devteam.logic.bean.verifiable.PreparedJob;
import by.bsu.mmf.devteam.logic.bean.verifiable.PreparedProject;
import by.bsu.mmf.devteam.logic.bean.verifiable.ProposedOrder;
import by.bsu.mmf.devteam.logic.bean.verifiable.SignInForm;
import by.bsu.mmf.devteam.resource.ResourceManager;
import org.apache.log4j.Logger;

import javax.validation.*;
import java.util.ArrayList;
import java.util.Set;

/**
 * This class defines validator for verifiable objects.
 *
 * @author Dmitry Petrovich
 * @since 2.3.0-beta
 */
public class VerifiableValidator {
    /** Initializes logger */
    private static Logger logger = Logger.getLogger("errors");

    /** Logger message */
    private static final String ERROR_CHECK_STATUS = "logger.validator.error.check.status";

    /** Keeps validator factory */
    private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    /** Keeps validator object */
    private static Validator validator = factory.getValidator();

    /**
     * This method verifies ProposedOrder object.
     *
     * @param order ProposedOrder object
     * @return True if data correct, otherwise false
     */
    public static boolean isOrderValid(ProposedOrder order) {
        Set<ConstraintViolation<ProposedOrder>> violations = validator.validate(order);
        return violations.size() == 0 && order.getCount() == order.getJobs().size();
    }

    /**
     * This method verifies SignInForm object.
     *
     * @param form SignInForm object
     * @return True if data correct, otherwise false.
     */
    public static boolean isSignInFormValid(SignInForm form) {
        Set<ConstraintViolation<SignInForm>> violations = validator.validate(form);
        return violations.size() == 0;
    }

    /**
     * This method verifies PreparedProject object.
     *
     * @param project PreparedProject object
     * @return True if data correct, otherwise false.
     */
    public static boolean isPreparedProjectValid(PreparedProject project) {
        Set<ConstraintViolation<PreparedProject>> violations = validator.validate(project);
        if (violations.size() != 0) {
            return false;
        }
        ArrayList<PreparedJob> jobs = project.getJobs();
        UserDAO dao = new UserDAO();
        for (PreparedJob job : jobs) {
            ArrayList<String> mails = job.getEmployees();
            for (String mail : mails) {
                try {
                    if (!dao.isEmployeeFree(mail)) {
                        return false;
                    }
                } catch (DAOException e) {
                    logger.error(ResourceManager.getProperty(ERROR_CHECK_STATUS) + mail, e);
                    return false;
                }
            }
        }
        return true;
    }

}
