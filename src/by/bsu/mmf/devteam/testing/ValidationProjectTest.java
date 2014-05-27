import by.bsu.mmf.devteam.logic.bean.verifiable.PreparedJob;
import by.bsu.mmf.devteam.logic.bean.verifiable.PreparedProject;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

/**
 *
 * @author Dmitry Petrovich
 * @since 2.3.0-beta
 */
public class ValidationProjectTest {
    private Validator validator;
    private PreparedProject project;

    @Before
    public void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        PreparedJob job = new PreparedJob();
        job.setId(6);
        job.setCost(2000);
        ArrayList<String> mails = new ArrayList<>();
        mails.add("designer5@devteam.com");
        job.setEmployees(mails);
        project = new PreparedProject();
        project.setName("Design");
        project.setId(3);
        ArrayList<PreparedJob> map = new ArrayList<>();
        map.add(job);
        project.setJobs(map);
    }

    @Test
    public void testValid() {
        Set<ConstraintViolation<PreparedProject>> violations = validator.validate(project);
        assertEquals(violations.size(), 0);
    }
}
