package apitests.models;

import java.util.List;

public class Order {

    private final List<String> ingredients;

    public Order(List<String> ingredients) {
        this.ingredients = List.copyOf(ingredients);
    }

    public List<String> getIngredients() {
        return ingredients;
    }
}