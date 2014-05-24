package by.bsu.mmf.devteam.testing;

import by.bsu.mmf.devteam.logic.bean.verifiable.ProposedOrder;
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
public class ValidationOrderTest {
    private ProposedOrder order;
    private Validator validator;

    @Before
    public void init() {
        order = new ProposedOrder();
        order.setSpecification("c");
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testOrder() {
        Set<ConstraintViolation<ProposedOrder>> violations = validator.validate(order);
        assertEquals(violations.size(), 0);
    }

}
