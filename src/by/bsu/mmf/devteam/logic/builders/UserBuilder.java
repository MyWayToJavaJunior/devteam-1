package by.bsu.mmf.devteam.logic.builders;

import by.bsu.mmf.devteam.database.dao.UserDAO;
import by.bsu.mmf.devteam.exception.data.DAOException;
import by.bsu.mmf.devteam.exception.logic.BusinessLogicException;
import by.bsu.mmf.devteam.logic.bean.user.User;
import by.bsu.mmf.devteam.logic.bean.verifiable.SignInForm;
import by.bsu.mmf.devteam.logic.bean.verifiable.validator.VerifiableValidator;
import by.bsu.mmf.devteam.resource.ResourceManager;
import by.bsu.mmf.devteam.util.Hasher;

/**
 * This class contain method building user object
 *
 * @author Dmitry Petrovich
 * @since 2.3.0-beta
 */
public class UserBuilder {

    /** Attributes and parameters */
    private static final String USER_BUILDER_ERROR = "";

    public static User buildUser(SignInForm form) throws BusinessLogicException {
        User user = null;
        if (VerifiableValidator.isSignInFormValid(form)) {
            try {
                UserDAO dao = new UserDAO();
                user = dao.getUser(form.getLogin(), Hasher.getMD5(form.getPassword()));
            } catch (DAOException e) {
                throw new BusinessLogicException(ResourceManager.getProperty(USER_BUILDER_ERROR), e);
            }
        }
        return user;
    }

}
