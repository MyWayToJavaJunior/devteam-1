package by.bsu.mmf.devteam.logic.bean.verifiable;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Dmitry Petrovich
 * @since 2.2.0-beta
 */
public class SignInForm {
    @NotNull(message = "Login can't be empty")
    @Email(message = "")    // Bad work!
    private String login;

    @NotNull(message = "Password can't be empty")
    @Size(min = 1, message = "Password length must be more than 0 characters")
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
