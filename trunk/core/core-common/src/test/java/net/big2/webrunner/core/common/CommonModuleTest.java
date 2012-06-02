package net.big2.webrunner.core.common;

import net.big2.webrunner.core.common.storage.LocalStorageService;
import net.big2.webrunner.core.common.test.BaseWebRunnerTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import static org.junit.Assert.*;

public class CommonModuleTest extends BaseWebRunnerTest {
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
