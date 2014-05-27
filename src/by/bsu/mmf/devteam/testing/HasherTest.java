import by.bsu.mmf.devteam.util.Hasher;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dmitry Petrovich
 * @since 1.0.0-alpha
 */
public class HasherTest  {
    /* Keeps origin value */
    private String value;

    /* Keeps hash value */
    private String expected;

    /**
     * Initializing values
     */
    @Before
    public void init() {
        value = "password";
        expected = "5f4dcc3b5aa765d61d8327deb882cf99";
    }

    /**
     * This method tests Hasher algorithm
     */
    @Test
    public void testHash() {
        String result = Hasher.getMD5(value);
        assertEquals(expected, result);
    }

}
