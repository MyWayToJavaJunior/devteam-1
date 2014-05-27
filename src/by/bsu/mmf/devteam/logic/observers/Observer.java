package by.bsu.mmf.devteam.logic.observers;

import java.util.EventObject;

/**
 *
 * @author Dmitry Petrovich
 * @since 2.3.0-beta
 */
public interface Observer {

    /**
     * Need to be overridden in concrete observers.
     *
     * @param event May be instance of EventObject class
     */
    public void handle(EventObject event);

}
