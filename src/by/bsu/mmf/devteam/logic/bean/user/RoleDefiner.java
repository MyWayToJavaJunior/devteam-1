package by.bsu.mmf.devteam.logic.bean.user;

/**
 *
 * @author Dmitry Petrovich
 * @since 2.0.0-beta
 */
public class RoleDefiner {

    /**
     * This method defines user role in application
     *
     * @param value Role name from database
     * @return Object role
     */
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
