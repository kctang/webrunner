package net.big2.webrunner.core.common;

import org.junit.Assert;
import org.junit.Test;

public class WebRunnerExceptionTest {
    @Test
    public void pojo_ok() {
        WebRunnerException e = new WebRunnerException("bob");
        Assert.assertEquals("bob", e.getMessage());
    }
}
