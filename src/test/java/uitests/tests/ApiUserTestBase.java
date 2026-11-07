package uitests.tests;

import uitests.helpers.ApiClient;
import uitests.helpers.TestUser;
import uitests.helpers.UserGenerator;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import uitests.setUp.BaseTest;

public class ApiUserTestBase extends BaseTest {

    protected ApiClient apiClient;
    protected TestUser testUser;
    protected String accessToken;

    @BeforeEach
    public void createApiUser() {

        apiClient = new ApiClient();
        testUser = UserGenerator.generateUser();

        Response response = apiClient.createUser(testUser);

        if (response.statusCode() != 200) {
            throw new AssertionError(
                    "Не удалось создать пользователя. HTTP "
                            + response.statusCode()
                            + ": " + response.asString()
            );
        }

        accessToken = response.jsonPath().getString("accessToken");
    }

    @AfterEach
    public void deleteApiUser() {

        if (accessToken != null && !accessToken.isEmpty()) {

            Response response = apiClient.deleteUser(accessToken);

            if (response.statusCode() != 202
                    && response.statusCode() != 200) {

                throw new AssertionError(
                        "Не удалось удалить пользователя. HTTP "
                                + response.statusCode()
                                + ": " + response.asString()
                );
            }
        }
    }
}