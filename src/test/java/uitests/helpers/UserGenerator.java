package uitests.helpers;

import java.util.UUID;

public final class UserGenerator  {

    private UserGenerator() {
    }

    public static TestUser generateUser() {

        String uniqueId = UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 12);

        return new TestUser(
                "Test User",
                "test-" + uniqueId + "@example.com",
                "Password123"
        );
    }
}