package webdriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;


public class ConstructWebDriver implements IWebDriver{
    final int DEFAULT_IMPLICITLY_DURATION = 5;

    @Override
    public WebDriver constructWebDriver(Class<? extends WebDriver> webDriverClass, String mode, String url)  {

        WebDriver driver;


        switch (webDriverClass.getSimpleName()) {
            case "FirefoxDriver" -> {
                //закидываем аргументы киоска и хэдлесса
                FirefoxOptions options = new FirefoxOptions().addArguments(mode);
                driver = WebDriverManager.getInstance(webDriverClass).capabilities(options).create();

                //лиса не умеет в фулскрин через аргументы, т.ч. для неё вот так
                if (mode.matches("start-fullscreen"))
                    driver.manage().window().fullscreen();

            }
            default -> { //дефолтные параметры => хром
                ChromeOptions options = new ChromeOptions().addArguments(mode);
                driver = WebDriverManager.getInstance(webDriverClass).capabilities(options).create();
            }
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(DEFAULT_IMPLICITLY_DURATION));
        driver.get(url);

        return driver;
    }
}
