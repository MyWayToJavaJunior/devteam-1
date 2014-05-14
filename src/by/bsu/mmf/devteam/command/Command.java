package by.bsu.mmf.devteam.command;

import by.bsu.mmf.devteam.exception.infrastructure.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Command {
    private String forward;

    public abstract void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException;

    public String getForward() {
        return forward;
    }

    public void setForward(String forward) {
        this.forward = forward;
    }

}
