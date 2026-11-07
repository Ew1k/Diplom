package uitests.setUp;

import uitests.helpers.WebDriverFactory;
import uitests.pageobjects.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;

    protected MainPage mainPage;
    protected LoginPage loginPage;
    protected RegisterPage registerPage;
    protected ForgotPasswordPage forgotPasswordPage;
    protected AccountPage accountPage;
    protected ConstructorPage constructorPage;

    @BeforeEach
    public void setUp() {

        String browser = System.getProperty("browser", "chrome");
        System.out.println("Выбранный браузер: " + browser);

        driver = WebDriverFactory.createDriver(browser);

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);

        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        registerPage = new RegisterPage(driver);
        forgotPasswordPage = new ForgotPasswordPage(driver);
        accountPage = new AccountPage(driver);
        constructorPage = new ConstructorPage(driver);

        mainPage.open();
    }

    @AfterEach
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }
}