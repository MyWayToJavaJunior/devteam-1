package by.bsu.mmf.devteam.command;

import by.bsu.mmf.devteam.exception.logic.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Dmitry Petrovich
 * @since 1.0.0-alpha
 */
public abstract class Command {
    /* Keeps forward path */
    private String forward;

    /**
     * Need to be overridden by command
     *
     * @param request HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws CommandException if execution is failed
     */
    public abstract void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException;

    /**
     * Return forward page path
     *
     * @return Page path
     */
    public String getForward() {
        return forward;
    }

    /**
     * Sets forward page path
     *
     * @param forward Page path
     */
    public void setForward(String forward) {
        this.forward = forward;
    }

}
