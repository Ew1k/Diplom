package uitests.pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private final By emailInput =
            By.xpath("//input[@type='text' or @type='email']");

    private final By passwordInput =
            By.cssSelector("input[type='password']");

    private final By loginButton =
            By.xpath("//button[normalize-space()='Войти']");

    private final By registerLink =
            By.xpath("//a[contains(.,'Зарегистрироваться')]");

    private final By forgotPasswordLink =
            By.xpath("//a[contains(.,'Восстановить пароль')]");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Войти под пользователем {email}")
    public void login(String email, String password) {

        type(emailInput, email);
        type(passwordInput, password);
        click(loginButton);
    }

    @Step("Открыть регистрацию")
    public void openRegistration() {
        click(registerLink);
    }

    @Step("Открыть восстановление пароля")
    public void openForgotPassword() {
        click(forgotPasswordLink);
    }

    @Step("Проверить отображение формы входа")
    public boolean isDisplayed() {
        return isVisible(loginButton)
                && isVisible(passwordInput);
    }
}