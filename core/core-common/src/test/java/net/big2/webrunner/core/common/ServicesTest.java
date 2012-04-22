package net.big2.webrunner.core.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:webrunner.xml")
public class ServicesTest {
    @Autowired
    ApplicationContext context;

    @Test
    public void emptyTest() {
        assertNotNull(context);
    }

}
