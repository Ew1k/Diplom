package uitests.pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage extends BasePage {

    private final By loginButton =
            By.xpath("//button[contains(.,'Войти в аккаунт')]");

    private final By accountButton =
            By.xpath("//a[contains(@href,'account')]");

    private final By constructorButton =
            By.xpath("//*[self::a or self::button][contains(.,'Конструктор')]");

    private final By logo =
            By.cssSelector("a[href='/']");

    public MainPage(WebDriver driver) {
        super(driver);
    }

    @Step("Нажать «Войти в аккаунт»")
    public void clickLoginButton() {
        click(loginButton);
    }

    @Step("Открыть личный кабинет")
    public void openAccount() {
        click(accountButton);
    }

    @Step("Открыть конструктор")
    public void openConstructor() {
        click(constructorButton);
    }

    @Step("Нажать на логотип")
    public void clickLogo() {
        click(logo);
    }


    @Step("Проверить отображение личного кабинета")
    public boolean isAccountButtonVisible() {
        return isVisible(accountButton);
    }
}