package by.bsu.mmf.devteam.command;

import by.bsu.mmf.devteam.command.authorize.Login;
import by.bsu.mmf.devteam.command.authorize.Logout;
import by.bsu.mmf.devteam.command.content.*;
import by.bsu.mmf.devteam.command.handle.*;
import by.bsu.mmf.devteam.command.language.ChangeLanguage;
import by.bsu.mmf.devteam.command.navigate.NotFound;
import by.bsu.mmf.devteam.command.navigate.RedirectToDefault;
import by.bsu.mmf.devteam.logic.checker.AccessChecker;
import by.bsu.mmf.devteam.resource.ResourceManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * This class realizes command factory
 *
 * @author Dmitry Petrovich
 * @since 1.0.0-alpha
 */
public class CommandFactory {
    /* Initializing command activity */
    private static Logger logger = Logger.getLogger("activity");

    /* Keeps name of parameter which contains command name */
    private static final String EXECUTION_COMMAND = "executionCommand";

    /* Keeps illegal command message */
    private static final String LOGGER_ILLEGAL_COMMAND = "logger.error.illegal.command";

    /**
     * This method defines command
     *
     * @param request HttpServletRequest object
     * @return Command object
     */
    public static Command getCommand(HttpServletRequest request) {
        Command command = null;
        CommandEnum commandType = getCommandEnum(request.getParameter(EXECUTION_COMMAND));
        if (AccessChecker.checkPermission(request, commandType)) {
            switch (commandType) {
                case CLOSE_PROJECT:
                    command = new CloseProject();
                    break;
                case CREATE_ORDER:
                    command = new CreateOrder();
                    break;
                case CREATE_PROJECT:
                    command = new CreateProject();
                    break;
                case CHANGE_LANGUAGE:
                    command = new ChangeLanguage();
                    break;
                case SHOW_BILLS:
                    command = new ShowBills();
                    break;
                case SHOW_JOB:
                    command = new ShowCurrentJob();
                    break;
                case SHOW_ORDER_FORM:
                    command = new ShowOrderForm();
                    break;
                case SHOW_PROJECT:
                    command = new ShowManagedProject();
                    break;
                case SHOW_SPECIFICATIONS:
                    command = new ShowSpecifications();
                    break;
                case SKIP_PREPARING:
                    command = new SkipPreparing();
                    break;
                case SET_TIME:
                    command = new SetElapsedTime();
                    break;
                case LOGIN:
                    command = new Login();
                    break;
                case LOGOUT:
                    command = new Logout();
                    break;
                case PREPARE_PROJECT:
                    command = new PrepareProject();
                    break;
                case REDIRECT:
                    command = new RedirectToDefault();
                    break;
                case WAITING_ORDERS:
                    command = new ShowWaitingOrders();
                    break;
                case MANAGED_PROJECTS:
                    command = new ShowManagedProjects();
                    break;
                case MANAGED_BILLS:
                    command = new ShowManagedBills();
                    break;
                default:
                    command = new NotFound();
                    break;
            }
        } else {
            command = new Login();
        }
        return command;
    }

    /**
     * This method initializing enum from command name
     *
     * @param executionCommand Name of command
     * @return Command enum
     */
    private static CommandEnum getCommandEnum(String executionCommand) {
        CommandEnum commandEnum = null;
        try {
            commandEnum = CommandEnum.valueOf(executionCommand);
        } catch (IllegalArgumentException exception) {
            logger.error(ResourceManager.getProperty(LOGGER_ILLEGAL_COMMAND), exception);
            commandEnum = CommandEnum.ILLEGAL_COMMAND;
        }
        return commandEnum;
    }

}
