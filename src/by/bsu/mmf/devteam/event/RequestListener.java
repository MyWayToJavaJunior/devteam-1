package by.bsu.mmf.devteam.event;

import org.apache.log4j.Logger;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Dmitry Petrovich
 * @since 1.0.0-alpha
 */
public class RequestListener implements ServletRequestListener {
    private static Logger logger = Logger.getLogger("activity");

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        String command = servletRequestEvent.getServletRequest().getParameter("executionCommand");
        HttpServletRequest request = (HttpServletRequest)servletRequestEvent.getServletRequest();
        logger.info("Request initialized. Command: " + command + " " + request.getContextPath());
    }

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
        String command = servletRequestEvent.getServletRequest().getParameter("executionCommand");
        logger.info("Request destroyed. Command: " + command);
    }

}
