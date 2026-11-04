package uitests.pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class AccountPage extends BasePage {

    private final By profile =
            By.xpath("//*[contains(normalize-space(.),'Профиль')]");

    private final By logoutButton =
            By.xpath("//button[contains(.,'Выход') or contains(.,'Выйти')]");

    public AccountPage(WebDriver driver) {
        super(driver);
    }

    @Step("Проверить отображение личного кабинета")
    public boolean isDisplayed() {
        return isVisible(profile)
                && isVisible(logoutButton);
    }

    @Step("Выйти из аккаунта")
    public void logout() {
        click(logoutButton);
    }
}