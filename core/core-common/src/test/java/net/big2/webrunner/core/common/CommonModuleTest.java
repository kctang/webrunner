package net.big2.webrunner.core.common;

import net.big2.webrunner.core.common.storage.LocalStorageService;
import net.big2.webrunner.core.common.storage.StorageService;
import net.big2.webrunner.core.common.test.BaseWebRunnerTest;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import static org.junit.Assert.*;

public class CommonModuleTest extends BaseWebRunnerTest {
    @Autowired
    ApplicationContext context;

    @Autowired
    StorageService storageService;

    @Test
    public void contextLoaded() {
        assertNotNull(context);
        assertNotNull(storageService);
    }

    // TODO: cannot test this as usage of @Cacheable introduce proxies that cause @Autowired to not work
    @Ignore
    @Test
    public void localStorageServiceAssumptions() {
        LocalStorageService lss = (LocalStorageService) storageService;

        assertTrue(lss.getDirectory().length() > 0);
        assertFalse(lss.getDirectory().contains("//"));
        assertFalse(lss.getDirectory().contains("\\\\"));
    }
}
