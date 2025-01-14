package misc;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

public class ConstructWebDriver {
    WebDriver driver;

    public WebDriver constructWebDriver(Class<? extends WebDriver> webDriverClass, String mode) {

        switch (webDriverClass.getSimpleName()) {
            case "FirefoxDriver" -> {
                //закидываем аргументы киоска и хэдлесса
                FirefoxOptions options = new FirefoxOptions().addArguments("-" + mode);
                this.driver =  WebDriverManager.getInstance(webDriverClass).capabilities(options).create();

                if (mode.equals("start-fullscreen"))
                    driver.manage().window().fullscreen();

            }
            default -> { //дефолтные параметры => хром
                ChromeOptions options = new ChromeOptions().addArguments("--" + mode);
                this.driver = WebDriverManager.getInstance(webDriverClass).capabilities(options).create();
            }
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://otus.home.kartushin.su/training.html");

        return driver;
    }
}
