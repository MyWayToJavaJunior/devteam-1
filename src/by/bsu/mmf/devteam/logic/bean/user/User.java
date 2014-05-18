package by.bsu.mmf.devteam.logic.bean.user;

/**
 * Bean object
 *
 * @author Dmitry Petrovich
 * @since 1.0.0-alpha
 */
public class User {
    /* Keeps user id */
    private int id;

    /* Keeps user role */
    private Role role;

    /* Keeps page language */
    private String language;

    /* Keeps qualification */
    private String qualification;

    /**
     * Return user id
     *
     * @return User id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets user id
     *
     * @param id User id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns user role
     *
     * @return User role
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets user role
     *
     * @param role Role object
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Returns page language
     *
     * @return Language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Sets page language
     *
     * @param language Default or selected language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Returns user qualification. Check for NULL! <br />
     * Not all users has a qualification like managers or customers.
     *
     * @return Qualification name
     */
    public String getQualification() {
        return qualification;
    }

    /**
     * Sets user qualification.
     *
     * @param qualification Qualification name
     */
    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

}
