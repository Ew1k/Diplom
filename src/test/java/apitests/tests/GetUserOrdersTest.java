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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetUserOrdersTest {

    private final UserClient userClient = new UserClient();
    private final OrderClient orderClient = new OrderClient();

    private String token;

    @BeforeEach
    @Step("Создать пользователя и заказ")
    public void setUp() {
        User user = UserGenerator.getRandomUser();

        Response createUserResponse = userClient.createUser(user);

        assertEquals(200, createUserResponse.statusCode());

        token = createUserResponse.path("accessToken");

        Response ingredientsResponse = orderClient.getIngredients();

        assertEquals(200, ingredientsResponse.statusCode());

        String firstIngredient = ingredientsResponse.path("data[0]._id");
        String secondIngredient = ingredientsResponse.path("data[1]._id");

        List<String> ingredients = Arrays.asList(
                firstIngredient,
                secondIngredient
        );

        Response createOrderResponse = orderClient.createOrder(
                new Order(ingredients),
                token
        );

        assertEquals(200, createOrderResponse.statusCode());
        assertEquals(Boolean.TRUE, createUserResponse.path("success"));
    }

    @Test
    @Step("Получить заказы авторизованного пользователя")
    public void getOrdersWithAuthorizationTest() {
        Response response = orderClient.getUserOrders(token);

        assertEquals(200, response.statusCode());
        assertEquals(Boolean.TRUE, response.path("success"));
        assertTrue(response.<Integer>path("orders.size()") > 0);
    }

    @Test
    @Step("Получить заказы без авторизации")
    public void getOrdersWithoutAuthorizationTest() {
        Response response = orderClient.getUserOrders(null);

        assertEquals(401, response.statusCode());
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