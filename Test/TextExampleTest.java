/**
 * Created by 杜鹏宇 on 2015/3/10.
 */

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TextExampleTest {
    private TextExample textExample;

    @Before
    public void setUp() throws Exception {
        textExample = new TextExample();
    }

    @Test
    public void testSayHello() throws Exception{
        assertEquals("Hello", textExample.sayHello());
    }
}
