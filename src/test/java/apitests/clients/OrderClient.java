package apitests.clients;

import apitests.helpers.TestData;
import apitests.models.Order;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class OrderClient {

    @Step("Создать заказ")
    public Response createOrder(Order order, String token) {
        RequestSpecification request = given()
                .baseUri(TestData.BASE_URL)
                .header("Content-Type", "application/json")
                .body(order);

        if (token != null) {
            request.header("Authorization", token);
        }

        return request
                .when()
                .post(TestData.ORDERS_ENDPOINT);
    }

    @Step("Получить заказы пользователя")
    public Response getUserOrders(String token) {
        RequestSpecification request = given()
                .baseUri(TestData.BASE_URL);

        if (token != null) {
            request.header("Authorization", token);
        }

        return request
                .when()
                .get(TestData.ORDERS_ENDPOINT);
    }

    @Step("Получить список ингредиентов")
    public Response getIngredients() {
        return given()
                .baseUri(TestData.BASE_URL)
                .when()
                .get(TestData.INGREDIENTS_ENDPOINT);
    }
}