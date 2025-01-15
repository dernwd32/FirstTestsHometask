package misc;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;


import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


public class AssertWithLog {
    private WebDriver driver = null;
    private Logger logger = null;

    //конструктор с передачей текущего драйвера и логгера
    public AssertWithLog(WebDriver driver, Logger logger) {
        this.driver = driver;
        this.logger = logger;
    }

    //дефолтный конструктор
    public AssertWithLog(){};

    public void assertWithLog(boolean condition) {
        //если не передан message, вставляем туда название тестового метода
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        //Получаем имя текущего браузера
        Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
        String browserName = "[" + cap.getBrowserName() + "]";

        assertWithLog(
                condition,
                stackTrace[2].getMethodName(), //название тестового метода, из которого вызван текущий assertWithLog
                logger,
                browserName
        );
    }

    public void assertWithLog(boolean condition, String message, Logger logger, String currentBrowser) {

        message = String.format("%-11s", currentBrowser) + "-> "+ message;

        String messagePass = message + " > PASS";
        String messageFail = message + " > FAIL";

        if (condition) logger.info(messagePass);
        else logger.warn(messageFail);

        assertTrue(condition);

    }
}
