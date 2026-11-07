package apitests.helpers;
import apitests.models.User;
import io.qameta.allure.Step;

import java.util.UUID;

public class UserGenerator {

    @Step("Создание уникальног опользователя")
    public static User getRandomUser() {
        String random = UUID.randomUUID().toString();

        return new User(
                "user_"+ random + "@test.ru",
                "123456123",
                "Testuser"
        );


    }
}
