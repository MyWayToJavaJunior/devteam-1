package by.bsu.mmf.devteam.command;

import by.bsu.mmf.devteam.logic.bean.user.Role;

public enum CommandEnum {
    CLOSE_PROJECT(Role.MANAGER),
    CREATE_BILL(Role.MANAGER),
    CREATE_ORDER(Role.CUSTOMER),
    CREATE_PROJECT(Role.MANAGER),
    CHANGE_LANGUAGE(Role.ALL),
    ILLEGAL_COMMAND(Role.ALL),
    SHOW_BILLS(Role.CUSTOMER),
    SHOW_JOB(Role.EMPLOYEE),
    SHOW_FINISHED(Role.EMPLOYEE),
    SHOW_ORDER_FORM(Role.CUSTOMER),
    SHOW_PROJECT(Role.MANAGER),
    SHOW_SPECIFICATIONS(Role.CUSTOMER),
    SKIP_PREPARING(Role.MANAGER),
    SET_TIME(Role.EMPLOYEE),
    MANAGED_PROJECTS(Role.MANAGER),
    MANAGED_BILLS(Role.MANAGER),
    PREPARE_PROJECT(Role.MANAGER),
    WAITING_ORDER(Role.MANAGER),
    WAITING_ORDERS(Role.MANAGER),
    LOGIN(Role.ALL),
    LOGOUT(Role.ALL),
    REDIRECT(Role.ALL);

    private Role type;

    private CommandEnum(Role type) {
        this.type = type;
    }

    public Role getUserType() {
        return type;
    }
}
