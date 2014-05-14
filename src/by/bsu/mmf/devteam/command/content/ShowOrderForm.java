package by.bsu.mmf.devteam.command.content;

import by.bsu.mmf.devteam.command.Command;
import by.bsu.mmf.devteam.database.dao.QualificationDAO;
import by.bsu.mmf.devteam.exception.infrastructure.CommandException;
import by.bsu.mmf.devteam.exception.infrastructure.DAOException;
import by.bsu.mmf.devteam.resource.ResourceManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowOrderForm extends Command {
    private static final String PARAM_QUALIFICATIONS = "qualifications";
    private static final String FORWARD_ORDER_FORM = "forward.customer.add.specification";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        QualificationDAO dao = new QualificationDAO();
        try {
            request.setAttribute(PARAM_QUALIFICATIONS, dao.getAllQualifications());
        } catch (DAOException e) {
            throw new CommandException("An error has occured when trying get list of qualifications", e);
        }
        setForward(ResourceManager.getProperty(FORWARD_ORDER_FORM));
    }

}
