package tests;

import apitests.clients.UserClient;
import apitests.helpers.UserGenerator;
import apitests.models.User;
import apitests.models.UserCredentials;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginUserTest {

    private final UserClient userClient = new UserClient();

    private User user;
    private String token;

    @BeforeEach
    @Step("Создать пользователя перед тестом")
    public void setUp() {
        user = UserGenerator.getRandomUser();

        Response response = userClient.createUser(user);

        assertEquals(200, response.statusCode());
        assertEquals(Boolean.TRUE, response.path("success"));

        token = response.path("accessToken");
    }

    @Test
    @Step("Авторизоваться под существующим пользователем")
    public void loginExistingUserTest() {
        UserCredentials credentials = new UserCredentials(
                user.getEmail(),
                user.getPassword()
        );

        Response response = userClient.loginUser(credentials);

        assertEquals(200, response.statusCode());
        assertEquals(Boolean.TRUE, response.path("success"));
        assertEquals(user.getEmail(), response.path("user.email"));
    }

    @Test
    @Step("Попытаться авторизоваться с неправильными данными")
    public void loginWithIncorrectCredentialsTest() {
        UserCredentials credentials = new UserCredentials(
                user.getEmail(),
                "wrongPassword123"
        );

        Response response = userClient.loginUser(credentials);

        assertEquals(401, response.statusCode());
        assertEquals(Boolean.FALSE, response.path("success"));
        assertEquals(
                "email or password are incorrect",
                response.path("message")
        );
    }

    @AfterEach
    @Step("Удалить пользователя после теста")
    public void tearDown() {
        if (token != null) {
            userClient.deleteUser(token);
        }
    }
}