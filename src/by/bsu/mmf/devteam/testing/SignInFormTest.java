import by.bsu.mmf.devteam.logic.bean.verifiable.SignInForm;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 *
 * @author Dmitry Petrovich
 * @since 2.2.0-beta
 */
public class SignInFormTest {
    private SignInForm form = new SignInForm();
    private Validator validator;

    @Before
    public void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testForm() {
        form.setLogin("alibaba@mail.ru");
        form.setPassword("1");
        Set<ConstraintViolation<SignInForm>> violations = validator.validate(form);
        assertEquals(violations.size(), 0);
    }

}
