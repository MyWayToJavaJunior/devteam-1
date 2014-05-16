package by.bsu.mmf.devteam.logic.bean.user;

public class RoleDefiner {

    public static Role defineRole(String value) {
        Role role = null;
        switch (value) {
            case "customer":
                role = Role.CUSTOMER;
                break;
            case "developer":
                role = Role.EMPLOYEE;
                break;
            case "manager":
                role = Role.MANAGER;
                break;
        }
        return role;
    }

}
