package uitests.tests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest extends ApiUserTestBase {

    @Test
    public void loginFromMainPageButton() {

        mainPage.clickLoginButton();

        loginPage.login(
                testUser.getEmail(),
                testUser.getPassword()
        );

        assertTrue(
                mainPage.isAccountButtonVisible(),
                "После входа должен отображаться личный кабинет"
        );
    }

    @Test
    public void loginFromPersonalAccountButton() {

        mainPage.openAccount();

        loginPage.login(
                testUser.getEmail(),
                testUser.getPassword()
        );

        assertTrue(
                mainPage.isAccountButtonVisible(),
                "После входа должен отображаться личный кабинет"
        );
    }

    @Test
    public void loginFromRegistrationFormLink() {

        mainPage.clickLoginButton();
        loginPage.openRegistration();
        registerPage.backToLogin();

        assertTrue(
                loginPage.isDisplayed(),
                "Должна открыться форма входа"
        );

        loginPage.login(
                testUser.getEmail(),
                testUser.getPassword()
        );

        assertTrue(mainPage.isAccountButtonVisible());
    }

    @Test
    public void loginFromForgotPasswordPageLink() {

        mainPage.clickLoginButton();
        loginPage.openForgotPassword();
        forgotPasswordPage.backToLogin();

        assertTrue(
                loginPage.isDisplayed(),
                "Должна открыться форма входа"
        );

        loginPage.login(
                testUser.getEmail(),
                testUser.getPassword()
        );

        assertTrue(mainPage.isAccountButtonVisible());
    }
}