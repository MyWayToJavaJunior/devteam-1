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
 * @since 1.0.0
 */
@WebServlet("/controller")
public class Controller extends HttpServlet {
    private static Logger logger = Logger.getLogger("activity");
    private static final String FORWARD_SERVER_ERROR = "forward.error.500";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Command command = CommandFactory.getCommand(request);
        try {
            command.execute(request, response);
        } catch (CommandException e) {
            command.setForward(ResourceManager.getProperty(FORWARD_SERVER_ERROR));
            logger.error("Command error: ", e);
        } finally {
            try {
                request.getRequestDispatcher(command.getForward()).forward(request, response);
            } catch (NullPointerException e) {
                logger.error(e);
            }
        }
    }

}
