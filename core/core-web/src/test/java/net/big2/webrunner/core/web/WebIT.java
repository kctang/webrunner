package net.big2.webrunner.core.web;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.Assert.assertEquals;

public class WebIT {
    static WebDriver driver;

    @BeforeClass
    public static void beforeClass() {
        driver = new FirefoxDriver();
    }

    @AfterClass
    public static void afterClass() {
        driver.close();
    }

    @Test
    public void simple() {
        driver.get("http://localhost:8080/hello.jsp");

        assertEquals("Hello", driver.getTitle());
    }

}
