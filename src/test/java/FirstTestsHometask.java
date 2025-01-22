import asserts.AssertWithLog;
import org.apache.logging.log4j.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import webdriver.ConstructWebDriver;
import waiters.StandartWaiter;

import static org.junit.jupiter.api.Assertions.*;


public class FirstTestsHometask  {
    //подключаем логгер
    private static final Logger logger = LogManager.getLogger(FirstTestsHometask.class);
    //объявляем переменную вебдрайвера
    private WebDriver driver;// = new ChromeDriver(options);
    //Создаём объект от класса, создающего "параметризованный" вебдрайвер
    ConstructWebDriver constructWebDriver = new ConstructWebDriver();




  //  @Test
    @ParameterizedTest
    @ValueSource(  classes = { ChromeDriver.class, FirefoxDriver.class } )
    @DisplayName("Первый тест домашки: поле ввода")
    void ifEqualsInputText(Class<? extends WebDriver> webDriverClass)  {

        //передаем тип браузера и аргументы запуска и получаем готовый вебдрайвер по заданным параметрам
        driver = constructWebDriver.constructWebDriver(
                webDriverClass,
                "-headless",
                "https://otus.home.kartushin.su/training.html"
        );

        //подключаем класс-обёртку, объединяющую логгирование и assertTrue
        AssertWithLog assertWithLog = new AssertWithLog(driver, logger);

        //для записи в лог текущего значения браузера
        //String currentBrowser = webDriverClass.getSimpleName().replace("Driver", "").toLowerCase();

        //находим объекты ДОМа, которыми будем пользоваться
        WebElement textInput = driver.findElement(By.id("textInput"));

        //вводим текст в поле
        String checkingText = "ОТУС";
        textInput.sendKeys(checkingText);

        //получаем value поля
        String textValue = textInput.getAttribute("value");

        //сравниваем
        assertWithLog.assertWithLog( checkingText.equals(textValue) );
    }

    @ParameterizedTest
    @ValueSource(  classes = { ChromeDriver.class, FirefoxDriver.class } )
    @DisplayName("Второй тест домашки: модалка")
    void ifModalShowAndHideIsCorrect(Class<? extends WebDriver> webDriverClass)  {
        //передаем тип браузера и аргументы запуска и получаем готовый вебдрайвер по заданным параметрам
        driver = constructWebDriver.constructWebDriver(
                webDriverClass,
                "-kiosk",
                "https://otus.home.kartushin.su/training.html");

        //подключаем класс-обёртку, объединяющую логгирование и assertTrue
        AssertWithLog assertWithLog = new AssertWithLog(driver, logger);

        //Подключаем waiter
        StandartWaiter standartWaiter = new StandartWaiter(driver);

        //для записи в лог текущего значения браузера
        //String currentBrowser = webDriverClass.getSimpleName().replace("Driver", "").toLowerCase();

        //находим объекты ДОМа, которыми будем пользоваться
        WebElement closeModalBtn = driver.findElement(By.id("closeModal"));
        WebElement myModal = driver.findElement(By.id("myModal"));
        WebElement openModalBtn = driver.findElement(By.id("openModalBtn")); //


        boolean invisibleBeforeOpening, visibleAfterOpening, invisibleAfterClosing;

        //видимость до открытия
        invisibleBeforeOpening = standartWaiter.waitForElementNotVisible(myModal); //myModal.isDisplayed();

        //кликаем по кнопке открыть
        openModalBtn.click();

        //видимость после открытия
        visibleAfterOpening = standartWaiter.waitForElementVisible(myModal); //myModal.isDisplayed();

        //кликаем по крестику
        closeModalBtn.click();

        //видимость после закрытия
        invisibleAfterClosing =  standartWaiter.waitForElementNotVisible(myModal); //myModal.isDisplayed();myModal.isDisplayed();

        assertAll(
                () -> assertWithLog.assertWithLog( invisibleBeforeOpening, "ifModalShowAndHideIsCorrect > invisibleBeforeOpening"),

                () -> assertWithLog.assertWithLog( visibleAfterOpening, "ifModalShowAndHideIsCorrect > visibleAfterOpening"),

                () -> assertWithLog.assertWithLog( invisibleAfterClosing, "ifModalShowAndHideIsCorrect > invisibleAfterClosing")
        );


    }

    @ParameterizedTest
    @ValueSource(  classes = { ChromeDriver.class, FirefoxDriver.class } )
    @DisplayName("Третий тест домашки: отправка формы")
    void ifTextGotValuesFromForm(Class<? extends WebDriver> webDriverClass) {
        //передаем тип браузера и аргументы запуска и получаем готовый вебдрайвер по заданным параметрам
        driver = constructWebDriver.constructWebDriver(
                webDriverClass,
                "-start-fullscreen",
                "https://otus.home.kartushin.su/training.html"
        );

        AssertWithLog assertWithLog = new AssertWithLog(driver, logger);

        //Подключаем waiter
        //StandartWaiter standartWaiter = new StandartWaiter(driver);

        //для записи в лог текущего значения браузера
        //String currentBrowser = webDriverClass.getSimpleName().replace("Driver", "").toLowerCase();

        //находим объекты ДОМа, которыми будем пользоваться
        WebElement inputName = driver.findElement(By.id("name"));
        WebElement inputEmail = driver.findElement(By.id("email"));
        WebElement messageBox = driver.findElement(By.id("messageBox"));
        WebElement thisForm = driver.findElement(By.id("sampleForm"));
        WebElement hideBtn = driver.findElement(By.id("toggleMessage"));

        // ****************************************
        // проверим будет ли работать, если текстовый блок отображён

        //заводим текстовые переменные
        String checkingName = "MyTestName";
        String checkingEmail = "my@test.email";

        //заполняем ими соответствующие поля формы
        inputName.sendKeys(checkingName);
        inputEmail.sendKeys(checkingEmail);

        //отправляем форму
        thisForm.submit();

        //получаем значение текстового блока, изменённой в зав-ти от отправленных из формы значений
        String textValue = messageBox.getText();

        //проверяем соответствие значения текстового блока значениям отправленных из формы полей
        boolean ifDivContainsValuesWhenDisplayed = textValue.matches("(.*)" + checkingName + "(.*)" + checkingEmail + "(.*)");

        // ****************************************
        // проверим будет ли работать, если текстовый блок скрыт

        //заводим текстовые переменные
        String checkingNameAfterHide = "2MyTestName";
        String checkingEmailAfterHide = "2my@test.email";

        //очищаем ранее введённые значения полей
        //заполняем новыми значениями
        inputName.clear();
        inputName.sendKeys(checkingNameAfterHide);
        inputEmail.clear();
        inputEmail.sendKeys(checkingEmailAfterHide);

        //скрываем текстовый блок
        hideBtn.click();

        //отправляем форму
        thisForm.submit();

        //получаем значение текстовой строки, изменённой в зав-ти от отправленных из формы значений
        String textValueAfterHide = messageBox.getText();
        //System.out.println("textValueAfterHide = " + textValueAfterHide);

        boolean ifDivDoesntContainValuesWhenHidden = !textValueAfterHide.matches("(.*)" + checkingNameAfterHide + "(.*)" + checkingEmailAfterHide + "(.*)");
//      boolean ifDivMatchesValuesWhenHidden = standartWaiter.waitForTextMatches(
//                By.id("messageBox"),
//                "(.*)" + checkingNameAfterHide + "(.*)" + checkingEmailAfterHide + "(.*)"
//        );
        assertAll(
                () -> assertWithLog.assertWithLog( ifDivContainsValuesWhenDisplayed, "ifTextGotValuesFromForm > ifDivContainsValuesWhenDisplayed"),

                () -> assertWithLog.assertWithLog( ifDivDoesntContainValuesWhenHidden, "ifTextGotValuesFromForm > ifDivDoesntContainValuesWhenHidden")
        );
    }



    @AfterEach
        void tearDown() {
        driver.close();
    }
}
