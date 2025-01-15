package misc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AssertWithLog {

    public void assertWithLog(boolean condition, Logger logger, String currentBrowser) {
        //если не передан message, вставляем туда название тестового метода
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        //Arrays.stream(stackTrace).forEach(x -> System.out.println(x));

        assertWithLog(
                condition,
                stackTrace[2].getMethodName(), //название тестового метода, из которого вызван текущий assertWithLog
                logger,
                currentBrowser
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
