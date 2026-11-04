package uitests.pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ForgotPasswordPage extends BasePage {

    private final By loginLink =
            By.xpath("//a[contains(.,'Войти')]");

    public ForgotPasswordPage(WebDriver driver) {
        super(driver);
    }

    @Step("Вернуться на страницу входа")
    public void backToLogin() {
        click(loginLink);
    }
}