package by.bsu.mmf.devteam.logic.observers.language;

import by.bsu.mmf.devteam.logic.bean.user.User;

import java.util.EventObject;

/**
 *
 * @author Dmitry Petrovich
 * @since 2.3.0-beta
 */
public class LanguageEvent extends EventObject {

    /**
     * Constructor
     *
     * @param user User object
     */
    public LanguageEvent(User user) {
        super(user);
    }

    /**
     * Return changed User object
     *
     * @return User object
     */
    @Override
    public User getSource() {
        return (User)super.getSource();
    }

}
