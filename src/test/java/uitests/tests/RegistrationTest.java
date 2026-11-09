package uitests.tests;

import uitests.helpers.ApiClient;
import uitests.helpers.TestUser;
import uitests.helpers.UserGenerator;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import uitests.setUp.BaseTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegistrationTest extends BaseTest {

    private TestUser user;
    private String accessToken;

    @Test
    public void successfulRegistration() {

        user = UserGenerator.generateUser();

        mainPage.clickLoginButton();
        loginPage.openRegistration();

        registerPage.fill(
                user.getName(),
                user.getEmail(),
                user.getPassword()
        );



        registerPage.submit();

        System.out.println(
                "Токен: " + ((JavascriptExecutor) driver)
                        .executeScript("return localStorage.getItem('accessToken');")
        );


        accessToken = (String) ((JavascriptExecutor) driver)
                .executeScript(
                        "return localStorage.getItem('accessToken');"
                );

        assertNotNull(
                accessToken,
                "После регистрации должен сохраниться токен авторизации"
        );

        assertTrue(
                mainPage.isAccountButtonVisible(),
                "После регистрации должен отображаться личный кабинет"
        );
    }

    @Test
    public void registrationWithShortPasswordShowsError() {

        user = UserGenerator.generateUser();

        mainPage.clickLoginButton();
        loginPage.openRegistration();

        registerPage.fill(
                user.getName(),
                user.getEmail(),
                "12345"
        );

        registerPage.submit();

        assertTrue(
                registerPage.isPasswordErrorVisible(),
                "При пароле короче 6 символов должна появиться ошибка"
        );
    }

    @AfterEach
    public void deleteRegisteredUser() {

        if (accessToken != null && !accessToken.isEmpty()) {

            Response response = new ApiClient()
                    .deleteUser(accessToken);

            if (response.statusCode() != 202
                    && response.statusCode() != 200) {

                throw new AssertionError(
                        "Не удалось удалить зарегистрированного пользователя. "
                                + "HTTP " + response.statusCode()
                                + ": " + response.asString()
                );
            }
        }
    }
}