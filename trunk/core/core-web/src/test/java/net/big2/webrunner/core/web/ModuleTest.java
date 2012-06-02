package net.big2.webrunner.core.web;

import net.big2.webrunner.core.common.storage.StorageService;
import net.big2.webrunner.core.common.test.BaseWebRunnerTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import static org.junit.Assert.*;

public class ModuleTest extends BaseWebRunnerTest {
    @Autowired
    ApplicationContext context;

    @Autowired
    StorageService storageService;

    @Test
    public void contextLoaded() {
        assertNotNull(context);
        assertNotNull(storageService);
    }

}
