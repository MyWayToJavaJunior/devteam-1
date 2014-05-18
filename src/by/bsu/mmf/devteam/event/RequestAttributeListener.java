package by.bsu.mmf.devteam.event;

import org.apache.log4j.Logger;

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.annotation.WebListener;

/**
 *
 * @author Dmitry Petrovich
 * @since 1.0.0-alpha
 */
@WebListener
public class RequestAttributeListener implements ServletRequestAttributeListener {
    private static Logger logger = Logger.getLogger("activity");

    @Override
    public void attributeAdded(ServletRequestAttributeEvent event) {
        logger.info("Name: " + event.getName() + " Value: " + event.getValue());
    }

    @Override
    public void attributeReplaced(ServletRequestAttributeEvent event) {
        logger.info("Name: " + event.getName() + " Value: " + event.getValue());
    }

    @Override
    public void attributeRemoved(ServletRequestAttributeEvent event) {
        logger.info("Name: " + event.getName() + " Value: " + event.getValue());
    }

}
