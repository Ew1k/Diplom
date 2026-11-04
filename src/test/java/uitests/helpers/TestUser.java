package uitests.helpers;

public class TestUser {

    private final String name;
    private final String email;
    private final String password;

    public TestUser(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}