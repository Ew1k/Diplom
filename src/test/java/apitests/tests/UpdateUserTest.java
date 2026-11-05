package tests;

import apitests.clients.UserClient;
import apitests.helpers.UserGenerator;
import apitests.models.User;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpdateUserTest {

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
    @Step("Изменить email авторизованного пользователя")
    public void updateEmailWithAuthorizationTest() {
        Map<String, String> data = new HashMap<>();
        data.put("email", "new_" + user.getEmail());

        Response response = userClient.updateUser(data, token);

        assertEquals(200, response.statusCode());
        assertEquals(Boolean.TRUE, response.path("success"));
        assertEquals(data.get("email"), response.path("user.email"));
    }

    @Test
    @Step("Изменить имя авторизованного пользователя")
    public void updateNameWithAuthorizationTest() {
        Map<String, String> data = new HashMap<>();
        data.put("name", "NewTestName");

        Response response = userClient.updateUser(data, token);

        assertEquals(200, response.statusCode());
        assertEquals(Boolean.TRUE, response.path("success"));
        assertEquals(data.get("name"), response.path("user.name"));
    }

    @Test
    @Step("Изменить пароль авторизованного пользователя")
    public void updatePasswordWithAuthorizationTest() {
        String newPassword = "NewPassword123";

        Map<String, String> data = new HashMap<>();
        data.put("password", newPassword);

        Response response = userClient.updateUser(data, token);

        assertEquals(200, response.statusCode());
        assertEquals(Boolean.TRUE, response.path("success"));

        Response loginResponse = userClient.loginUser(
                new apitests.models.UserCredentials(user.getEmail(), newPassword)
        );

        assertEquals(200, loginResponse.statusCode());
        assertEquals(Boolean.TRUE, loginResponse.path("success"));

        // После смены пароля старый токен может стать недействительным.
        String newToken = loginResponse.path("accessToken");
        if (newToken != null) {
            token = newToken;
        }
    }

    @Test
    @Step("Попытаться изменить email без авторизации")
    public void updateEmailWithoutAuthorizationTest() {
        Map<String, String> data = new HashMap<>();
        data.put("email", "new_" + user.getEmail());

        checkUnauthorizedUpdate(data);
    }

    @Test
    @Step("Попытаться изменить имя без авторизации")
    public void updateNameWithoutAuthorizationTest() {
        Map<String, String> data = new HashMap<>();
        data.put("name", "NewTestName");

        checkUnauthorizedUpdate(data);
    }

    @Test
    @Step("Попытаться изменить пароль без авторизации")
    public void updatePasswordWithoutAuthorizationTest() {
        Map<String, String> data = new HashMap<>();
        data.put("password", "NewPassword123");

        checkUnauthorizedUpdate(data);
    }

    @Step("Проверить отказ в изменении данных без авторизации")
    private void checkUnauthorizedUpdate(Map<String, String> data) {
        Response response = userClient.updateUser(data, null);

        assertEquals(401, response.statusCode());
        assertEquals(Boolean.FALSE, response.path("success"));
        assertEquals(
                "You should be authorised",
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