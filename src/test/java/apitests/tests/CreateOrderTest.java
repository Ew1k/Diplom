package apitests.tests;

import apitests.clients.OrderClient;
import apitests.clients.UserClient;
import apitests.helpers.UserGenerator;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import apitests.models.Order;
import apitests.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CreateOrderTest {

    private final UserClient userClient = new UserClient();
    private final OrderClient orderClient = new OrderClient();

    private String token;
    private List<String> ingredients;

    @BeforeEach
    @Step("Создать пользователя и получить ID ингредиентов")
    public void setUp() {
        User user = UserGenerator.getRandomUser();

        Response createUserResponse = userClient.createUser(user);

        assertEquals(200, createUserResponse.statusCode());

        token = createUserResponse.path("accessToken");

        Response ingredientsResponse = orderClient.getIngredients();

        assertEquals(200, ingredientsResponse.statusCode());

        String firstIngredient = ingredientsResponse.path("data[0]._id");
        String secondIngredient = ingredientsResponse.path("data[1]._id");

        ingredients = Arrays.asList(firstIngredient, secondIngredient);
    }

    @Test
    @Step("Создать заказ с авторизацией")
    public void createOrderWithAuthorizationTest() {
        Order order = new Order(ingredients);

        Response response = orderClient.createOrder(order, token);

        assertEquals(200, response.statusCode());
        assertEquals(Boolean.TRUE, response.path("success"));
        assertNotNull(response.path("name"));
    }

    @Test
    @Step("Создать заказ без авторизации")
    public void createOrderWithoutAuthorizationTest() {
        Order order = new Order(ingredients);

        Response response = orderClient.createOrder(order, null);

        assertEquals(200, response.statusCode());
        assertEquals(Boolean.TRUE, response.path("success"));
        assertNotNull(response.path("name"));
    }

    @Test
    @Step("Попытаться создать заказ без ингредиентов")
    public void createOrderWithoutIngredientsTest() {
        Order order = new Order(Collections.emptyList());

        Response response = orderClient.createOrder(order, token);

        assertEquals(400, response.statusCode());
        assertEquals(
                "Ingredient ids must be provided",
                response.path("message")
        );
    }

    @Test
    @Step("Попытаться создать заказ с неправильным ID ингредиента")
    public void createOrderWithIncorrectIngredientTest() {
        Order order = new Order(
                Collections.singletonList("incorrect_ingredient_id")
        );

        Response response = orderClient.createOrder(order, token);

        assertEquals(500, response.statusCode());
        assertFalseSuccess(response);
    }

    @Step("Проверить, что заказ не был успешно создан")
    private void assertFalseSuccess(Response response) {
        String responseBody = response.asString();

        assertTrue(
                responseBody.contains("Internal Server Error"),
                "Ожидалась ошибка Internal Server Error. Фактический ответ: "
                        + responseBody
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