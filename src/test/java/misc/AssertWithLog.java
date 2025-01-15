package misc;

import org.apache.logging.log4j.Logger;


import static org.junit.jupiter.api.Assertions.*;

public class AssertWithLog {
    public void assertWithLog(boolean condition, String message, Logger logger, String currentBrowser) {

        message = String.format("%-13s", currentBrowser) + "-> "+ message;

        String messagePass = message + " > PASS";
        String messageFail = message + " > FAIL";

        if (condition) logger.info(messagePass);
        else logger.warn(messageFail);

        assertTrue(condition);

    }
}
