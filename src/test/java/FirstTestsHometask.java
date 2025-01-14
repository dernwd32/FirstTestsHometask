
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
    @Disabled
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
        assertEquals(checkingText, textValue);
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

        //если модалка видна до клика - фейл
        if (myModal.isDisplayed()) logger.warn("{}before click = FAIL", currentBrowser);
        else logger.info("{}before click = PASS", currentBrowser);
        // assertTrue(!myModal.isDisplayed());
        assertAll( () -> assertTrue(!myModal.isDisplayed()));

        //кликаем по кнопке открыть
        openModalBtn.click();

        //если модалка не видна после клика - фейл
        if (myModal.isDisplayed()) logger.info("{}after click = PASS", currentBrowser);
        else logger.warn("{}after click = FAIL", currentBrowser);
        assertTrue(myModal.isDisplayed());

        //кликаем по крестику
        closeModalBtn.click();

        //если модалка видна после второго клика - фейл
        if (myModal.isDisplayed()) logger.warn("{}after 2click = FAIL", currentBrowser);
        else logger.info("{}after 2click = PASS", currentBrowser);
       // assertTrue(!myModal.isDisplayed());
        assertAll( () -> assertTrue(!myModal.isDisplayed()));

    }

    @ParameterizedTest
    @ValueSource(  classes = { ChromeDriver.class, FirefoxDriver.class } )
    @DisplayName("Третий тест домашки: отправка формы")
    @Disabled
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
        assertTrue(isEqualsValues);
    }



    @AfterEach
    void tearDown() {
        driver.close();
    }
}
