package unitTests;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import praktikum.Ingredient;
import praktikum.IngredientType;

import static org.junit.jupiter.api.Assertions.*;


import java.util.stream.Stream;

public class IngredientTest {
    static Stream<Object[]> data() {
        return Stream.of (
                        new Object[] {IngredientType.SAUCE, "hot sauce", 1000f},
                        new Object[] {IngredientType.SAUCE,"sour cream", 200f},
                        new Object[] {IngredientType.FILLING,"cutlet", 100f},
                        new Object[] {IngredientType.FILLING,"sausage", 300f}
        );
    }

    @ParameterizedTest
    @MethodSource("data")
    public void gettersReturnCorrectValues(IngredientType type, String name, float price) {
         Ingredient ingredient = new Ingredient(type, name, price);

         assertEquals(type, ingredient.getType());
         assertEquals(name, ingredient.getName());
         assertEquals(price, ingredient.getPrice(), 0.1f);


    }




}
