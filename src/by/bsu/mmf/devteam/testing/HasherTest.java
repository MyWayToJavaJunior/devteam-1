package by.bsu.mmf.devteam.testing;

import by.bsu.mmf.devteam.util.Hasher;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class HasherTest  {
    private String value;
    private String expected;

    @Before
    public void init() {
        value = "password";
        expected = "5f4dcc3b5aa765d61d8327deb882cf99";
    }

    @Test
    public void testHash() {
        String result = Hasher.getMD5(value);
        assertEquals(expected, result);
    }

}
