package by.bsu.mmf.devteam.logic.observers.language;

import by.bsu.mmf.devteam.database.dao.UserDAO;
import by.bsu.mmf.devteam.exception.data.DAOException;
import by.bsu.mmf.devteam.logic.bean.user.User;
import by.bsu.mmf.devteam.logic.observers.Observer;
import by.bsu.mmf.devteam.resource.ResourceManager;
import org.apache.log4j.Logger;

import java.util.EventObject;

/**
 *
 * @author Dmitry Petrovich
 * @since 2.3.0-beta
 */
public class LanguageObserver implements Observer {
    /** Initializes error logger */
    private static final Logger logger = Logger.getLogger("logic");

    /** Logger message */
    private static final String ERROR_UPDATE_LANG = "logger.observer.error.change.lang";

    /**
     * Updates database when user changing UI language.
     *
     * @param event May be instance of EventObject class
     */
    @Override
    public void handle(EventObject event) {
        LanguageEvent languageEvent = (LanguageEvent)event;
        User user = languageEvent.getSource();
        UserDAO dao = new UserDAO();
        try {
            dao.changeUILanguage(user.getLanguage(), user.getId());
        } catch (DAOException e) {
            logger.error(ResourceManager.getProperty(ERROR_UPDATE_LANG) + user.getId(), e);
        }
    }

}
