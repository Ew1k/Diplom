package uitests.pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage extends BasePage {

    private final By nameInput =
            By.xpath("//label[normalize-space()='Имя']/following::input[@type='text'][1]");

    private final By emailInput =
            By.xpath("//label[normalize-space()='Email']/following::input[@type='text'][1]");
    private final By passwordInput =
            By.cssSelector("input[type='password']");

    private final By registerButton =
            By.xpath("//button[normalize-space()='Зарегистрироваться']");

    private final By loginLink =
            By.xpath("//a[contains(.,'Войти')]");

    private final By passwordError =
            By.xpath("//*[contains(.,'Некорректный пароль')]");

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    @Step("Заполнить форму регистрации")
    public void fill(String name, String email, String password) {

        type(nameInput, name);
        type(emailInput, email);
        type(passwordInput, password);
    }

    @Step("Нажать кнопку регистрации")
    public void submit() {
        click(registerButton);
    }

    @Step("Вернуться на страницу входа")
    public void backToLogin() {
        click(loginLink);
    }

    @Step("Проверить ошибку некорректного пароля")
    public boolean isPasswordErrorVisible() {
        return isVisible(passwordError);
    }
}
