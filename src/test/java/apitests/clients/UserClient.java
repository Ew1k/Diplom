package apitests.clients;

import apitests.helpers.TestData;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class UserClient {

    @Step("Создать пользователя")
    public Response createUser(Object user) {
        return given()
                .baseUri(TestData.BASE_URL)
                .header("Content-Type", "application/json")
                .body(user)
                .when()
                .post(TestData.REGISTER_ENDPOINT);
    }

    @Step("Авторизоваться")
    public Response loginUser(Object credentials) {
        return given()
                .baseUri(TestData.BASE_URL)
                .header("Content-Type", "application/json")
                .body(credentials)
                .when()
                .post(TestData.LOGIN_ENDPOINT);
    }

    @Step("Изменить данные пользователя")
    public Response updateUser(Map<String, String> data, String token) {
        RequestSpecification request = given()
                .baseUri(TestData.BASE_URL)
                .header("Content-Type", "application/json")
                .body(data);

        if (token != null) {
            request.header("Authorization", token);
        }

        return request
                .when()
                .patch(TestData.USER_ENDPOINT);
    }

    @Step("Удалить пользователя")
    public Response deleteUser(String token) {
        return given()
                .baseUri(TestData.BASE_URL)
                .header("Authorization", token)
                .when()
                .delete(TestData.USER_ENDPOINT);
    }
}