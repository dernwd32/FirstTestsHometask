
import org.apache.logging.log4j.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import misc.ConstructWebDriver;

import static org.junit.jupiter.api.Assertions.*;


public class FirstTestsHometask  {
    //подключаем логгер
    private static final Logger logger = LogManager.getLogger(FirstTestsHometask.class);
    //объявляем переменную вебдрайвера
    private WebDriver driver;// = new ChromeDriver(options);
    //Создаём объект от класса, создающего "параметризованный" вебдрайвер
    private ConstructWebDriver constructWebDriver = new ConstructWebDriver();


  //  @Test
    @ParameterizedTest
    @ValueSource(  classes = { ChromeDriver.class, FirefoxDriver.class } )
    @DisplayName("Первый тест домашки: поле ввода")
    void ifEqualsInputText(Class<? extends WebDriver> webDriverClass)  {

        //передаем тип браузера и аргументы запуска и получаем готовый вебдрайвер по заданным параметрам
        driver = constructWebDriver.constructWebDriver(webDriverClass, "headless");

        //для записи в лог текущего значения браузера
        String currentBrowser = "[" + webDriverClass.getSimpleName().replace("Driver", "") + "] ";

        //находим объекты ДОМа, которыми будем пользоваться
        WebElement textInput = driver.findElement(By.id("textInput"));

        //вводим текст в поле
        String checkingText = "ОТУС";
        textInput.sendKeys(checkingText);

        //получаем value поля
        String textValue = textInput.getAttribute("value");

        //сравниваем
        if (checkingText.equals(textValue)) logger.info("{}text equals = PASS", currentBrowser);
        else logger.warn("{}text equals = FAIL", currentBrowser);
        assertEquals(checkingText, textValue, currentBrowser + "text equals = FAIL");
    }

    @ParameterizedTest
    @ValueSource(  classes = { ChromeDriver.class, FirefoxDriver.class } )
    @DisplayName("Второй тест домашки: модалка")
    //@Disabled
    void ifModalShowAndHideIsCorrect(Class<? extends WebDriver> webDriverClass)  {
        //передаем тип браузера и аргументы запуска и получаем готовый вебдрайвер по заданным параметрам
        driver = constructWebDriver.constructWebDriver(webDriverClass, "kiosk");

        //для записи в лог текущего значения браузера
        String currentBrowser = "[" + webDriverClass.getSimpleName().replace("Driver", "") + "] ";

        //находим объекты ДОМа, которыми будем пользоваться
        WebElement closeModalBtn = driver.findElement(By.id("closeModal"));
        WebElement myModal = driver.findElement(By.id("myModal"));
        WebElement openModalBtn = driver.findElement(By.id("openModalBtn")); //

        boolean visibleBeforeOpening, visibleAfterOpening, visibleAfterClosing;

        //видимость до открытия
        visibleBeforeOpening = myModal.isDisplayed();

        //кликаем по кнопке открыть
        openModalBtn.click();

        //видимость после открытия
        visibleAfterOpening = myModal.isDisplayed();

        //кликаем по крестику
        closeModalBtn.click();

        //видимость после закрытия
        visibleAfterClosing = myModal.isDisplayed();

        assertAll(
                () -> {
                    if (visibleBeforeOpening) logger.warn("{}visibleBeforeOpening = FAIL", currentBrowser);
                    else logger.info("{}visibleBeforeOpening = PASS", currentBrowser);
                    assertTrue(!visibleBeforeOpening, currentBrowser + "visibleBeforeOpening = FAIL");
                },

                () -> {
                    if (visibleAfterOpening) logger.info("{}visibleAfterOpening = PASS", currentBrowser);
                    else logger.warn("{}visibleAfterOpening = FAIL", currentBrowser);
                    assertTrue(visibleAfterOpening, currentBrowser + "visibleAfterOpening = FAIL");
                },

                () -> {
                    if (visibleAfterClosing) logger.warn("{}visibleAfterClosing = FAIL", currentBrowser);
                    else logger.info("{}visibleAfterClosing = PASS", currentBrowser);
                    assertTrue(!visibleAfterClosing,  currentBrowser + "visibleAfterClosing = FAIL");
                }
        );


    }

    @ParameterizedTest
    @ValueSource(  classes = { ChromeDriver.class, FirefoxDriver.class } )
    @DisplayName("Третий тест домашки: отправка формы")
    void ifTextGotValuesFromForm(Class<? extends WebDriver> webDriverClass) {
        //передаем тип браузера и аргументы запуска и получаем готовый вебдрайвер по заданным параметрам
        driver = constructWebDriver.constructWebDriver(webDriverClass, "start-fullscreen");

        //для записи в лог текущего значения браузера
        String currentBrowser = "[" + webDriverClass.getSimpleName().replace("Driver", "") + "] ";

        //находим объекты ДОМа, которыми будем пользоваться
        WebElement inputName = driver.findElement(By.id("name"));
        WebElement inputEmail = driver.findElement(By.id("email"));
        WebElement messageBox = driver.findElement(By.id("messageBox"));
        WebElement thisForm = driver.findElement(By.id("sampleForm"));

        //заводим текстовые переменные
        String checkingName = "MyTestName";
        String checkingEmail = "my@test.email";

        //заполняем ими соответствующие поля формы
        inputName.sendKeys(checkingName);
        inputEmail.sendKeys(checkingEmail);

        //отправляем форму
        thisForm.submit();

        //получаем значение текстовой строки, изменённой в зав-ти от отправленных из формы значений
        String textValue = messageBox.getText();

        //проверяем соответствие значения текстовой строки значениям отправленных из формы полей
        boolean isEqualsValues = textValue.matches("(.*)" + checkingName + "(.*)" + checkingEmail + "(.*)");
        if (isEqualsValues) logger.info("{}form sent correctly = PASS", currentBrowser);
        else logger.warn("{}form sent correctly = FAIL", currentBrowser);
        assertTrue(isEqualsValues, currentBrowser + "form sent correctly = FAIL");
    }



    @AfterEach
    void tearDown() {
        driver.close();
    }
}
