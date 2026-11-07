package uitests.tests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountTest extends ApiUserTestBase {

    private void login() {

        mainPage.clickLoginButton();

        loginPage.login(
                testUser.getEmail(),
                testUser.getPassword()
        );
    }

    @Test
    public void openPersonalAccount() {

        login();

        mainPage.openAccount();

        assertTrue(
                accountPage.isDisplayed(),
                "Должен открыться личный кабинет"
        );
    }

    @Test
    public void logoutFromPersonalAccount() {

        login();

        mainPage.openAccount();

        accountPage.logout();

        assertTrue(
                loginPage.isDisplayed(),
                "После выхода должна открыться страница входа"
        );
    }


    @Test
    public void goToConstructorUsingHeaderLink() {

        login();

        mainPage.openAccount();
        mainPage.openConstructor();

        assertTrue(
                constructorPage.areBunsActive(),
                "После перехода должна открыться вкладка «Булки»"
        );
    }

    @Test
    public void goToConstructorUsingLogo() {

        login();

        mainPage.openAccount();
        mainPage.clickLogo();

        assertTrue(
                constructorPage.areBunsActive(),
                "После нажатия на логотип должен открыться конструктор"
        );
    }
}