package by.bsu.mmf.devteam.command.navigate;

import by.bsu.mmf.devteam.command.Command;
import by.bsu.mmf.devteam.exception.infrastructure.CommandException;
import by.bsu.mmf.devteam.resource.ResourceManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Dmitry Petrovich
 * @since 1.0.0-alpha
 */
public class NotFound extends Command {
    private static final String FORWARD_NOT_FOUND = "forward.error.404";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        setForward(ResourceManager.getProperty(FORWARD_NOT_FOUND));
    }

}
