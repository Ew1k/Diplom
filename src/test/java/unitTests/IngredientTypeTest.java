package unitTests;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import praktikum.IngredientType;
import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

public class IngredientTypeTest {
    static Stream<Object[]> data() {
        return Stream.of (
                new Object[]{IngredientType.SAUCE, "SAUCE"},
                new Object[]{IngredientType.FILLING, "FILLING"}
        );

    }

    @ParameterizedTest
    @MethodSource("data")
    public void IngredientTypeReturnsCorrectName(IngredientType type, String expectedName) {

        assertEquals(expectedName, type.name());

    }

    @ParameterizedTest
    @MethodSource("data")
    public void IngredientTypeReturnCorrectEnum(IngredientType type, String expectedName) {

        assertEquals(type, IngredientType.valueOf(expectedName));

    }

}
