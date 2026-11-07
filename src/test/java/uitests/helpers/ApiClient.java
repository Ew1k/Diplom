package uitests.helpers;


import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ApiClient {

    @Step("Создать пользователя через API")
    public Response createUser(uitests.helpers.TestUser user) {

        return given()
                .baseUri(Config.URL)
                .contentType("application/json")
                .body(user)
                .when()
                .post("/api/auth/register");
    }

    @Step("Удалить пользователя через API")
    public Response deleteUser(String accessToken) {

        String token = accessToken.startsWith("Bearer ")
                ? accessToken
                : "Bearer " + accessToken;

        return given()
                .baseUri(Config.URL)
                .header("Authorization", token)
                .when()
                .delete("/api/auth/user");
    }
}