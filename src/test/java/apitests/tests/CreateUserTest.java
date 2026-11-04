package tests;

import apitests.clients.UserClient;
import apitests.helpers.UserGenerator;
import apitests.models.User;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateUserTest {

    private final UserClient userClient = new UserClient();

    private String token;

    @Test
    @Step("Зарегистрировать уникального пользователя")
    public void createUniqueUserTest() {
        User user = UserGenerator.getRandomUser();

        Response response = userClient.createUser(user);

        assertEquals(200, response.statusCode());
        assertEquals(Boolean.TRUE, response.path("success"));

        token = response.path("accessToken");
    }

    @Test
    @Step("Попытаться зарегистрировать уже существующего пользователя")
    public void createDuplicateUserTest() {
        User user = UserGenerator.getRandomUser();

        Response firstResponse = userClient.createUser(user);

        assertEquals(200, firstResponse.statusCode());
        assertEquals(Boolean.TRUE, firstResponse.path("success"));

        token = firstResponse.path("accessToken");

        Response secondResponse = userClient.createUser(user);

        assertEquals(403, secondResponse.statusCode());
        assertEquals(Boolean.FALSE, secondResponse.path("success"));
        assertEquals("User already exists", secondResponse.path("message"));
    }

    @Test
    @Step("Попытаться зарегистрировать пользователя без обязательного поля")
    public void createUserWithoutRequiredFieldTest() {
        User user = UserGenerator.getRandomUser();

        Map<String, String> userData = new HashMap<>();
        userData.put("email", user.getEmail());
        userData.put("password", user.getPassword());

        Response response = userClient.createUser(userData);

        assertEquals(403, response.statusCode());
        assertEquals(Boolean.FALSE, response.path("success"));
        assertEquals(
                "Email, password and name are required fields",
                response.path("message")
        );
    }

    @AfterEach
    @Step("Удалить созданного пользователя")
    public void tearDown() {
        if (token != null) {
            userClient.deleteUser(token);
        }
    }
}