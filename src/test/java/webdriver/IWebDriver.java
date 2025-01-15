package webdriver;

import org.openqa.selenium.WebDriver;

public interface IWebDriver {

    WebDriver constructWebDriver(Class<? extends WebDriver> webDriverClass, String mode, String url);

}
