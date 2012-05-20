package net.big2.webrunner.core.common;

import net.big2.webrunner.core.common.storage.LocalStorageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:webrunner.xml")
public class CommonModuleTest {
    @Autowired
    ApplicationContext context;

    @Autowired
    LocalStorageService storageService;

    @Test
    public void contextLoaded() {
        assertNotNull(context);
        assertNotNull(storageService);
    }

    @Test
    public void localStorageServiceAssumptions() {
        assertTrue(storageService.getDirectory().length() > 0);
        assertFalse(storageService.getDirectory().contains("//"));
        assertFalse(storageService.getDirectory().contains("\\\\"));
    }
}
