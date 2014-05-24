package by.bsu.mmf.devteam.controller;

import by.bsu.mmf.devteam.command.Command;
import by.bsu.mmf.devteam.command.CommandFactory;
import by.bsu.mmf.devteam.exception.infrastructure.CommandException;
import by.bsu.mmf.devteam.resource.ResourceManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet controller
 *
 * @author Dmitry Petrovich
 * @since 1.0.0-alpha
 */
@WebServlet("/controller")
public class Controller extends HttpServlet {
    /* Initializes error logger */
    private static Logger logger = Logger.getLogger("errors");

    /* Logger messages */
    private static final String MSG_EXECUTE_ERROR = "logger.error.execute.command";

    /* Forward page */
    private static final String FORWARD_SERVER_ERROR = "forward.error.500";

    /**
     * Overrides method which handling requests with method get
     *
     * @param req HttpServletRequest object
     * @param resp HttpServletResponse object
     * @throws ServletException object
     * @throws IOException object
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    /**
     * Overrides method which handling requests with method post
     *
     * @param req HttpServletRequest object
     * @param resp HttpServletResponse object
     * @throws ServletException object
     * @throws IOException object
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    /**
     *
     * @param request HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws ServletException object
     * @throws IOException object
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Command command = CommandFactory.getCommand(request);
        try {
            command.execute(request, response);
            request.getRequestDispatcher(command.getForward()).forward(request, response);
        } catch (CommandException e) {
            command.setForward(ResourceManager.getProperty(FORWARD_SERVER_ERROR));
            logger.error(ResourceManager.getProperty(MSG_EXECUTE_ERROR), e);
        }
    }

}
