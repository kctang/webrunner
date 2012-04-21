package web;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.Assert.assertEquals;

public class DemoWebIT {
    @Test
    public void list() {
        driver.get("http://localhost:8080/add");

        assertEquals(
                "<html xmlns=\"http://www.w3.org/1999/xhtml\"><head></head><body>Added</body></html>",
                driver.getPageSource());
    }

    static WebDriver driver;

    @BeforeClass
    public static void beforeClass() {
        driver = new FirefoxDriver();
    }

    @AfterClass
    public static void afterClass() {
        driver.close();
    }

}
