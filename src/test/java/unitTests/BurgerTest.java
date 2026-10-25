package unitTests;

import org.junit.jupiter.api.*;
import praktikum.Bun;
import praktikum.Burger;
import praktikum.Ingredient;
import praktikum.IngredientType;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BurgerTest {

    private Burger burger;
    private Bun bun;
    private Ingredient sauce;
    private Ingredient filling;

    @BeforeEach
    public void setUp() {
        burger = new Burger();
        bun = mock(Bun.class);
        sauce = mock(Ingredient.class);
        filling = mock(Ingredient.class);


        when(bun.getName()).thenReturn("black bun");
        when(bun.getPrice()).thenReturn(50f);

        when(sauce.getType()).thenReturn(IngredientType.SAUCE);
        when(sauce.getName()).thenReturn("hot sauce");
        when(sauce.getPrice()).thenReturn(60f);

        when(filling.getType()).thenReturn(IngredientType.FILLING);
        when(filling.getName()).thenReturn("cutlet");
        when(filling.getPrice()).thenReturn(70f);
    }

    @Test
    public void setBunSetsCorrect(){
        burger.setBuns(bun);

        assertSame(bun, burger.bun);

        System.out.println(bun); //0000000000000000000000000000000
    }
    @Test
    public void addIngredientAddsCorrectToList(){
        burger.addIngredient(sauce);

        assertEquals(1,burger.ingredients.size());
        assertEquals(sauce, burger.ingredients.get(0));


    }

    @Test
    public void removeIngredientRemovesCorrectIngredient(){
        burger.addIngredient(sauce);
        burger.addIngredient(filling);

        burger.removeIngredient(0);

        assertEquals(1,burger.ingredients.size());
        assertEquals(filling,burger.ingredients.get(0));

    }

    @Test
    public void moveIngredientMovesCorrectIngredient(){
        burger.addIngredient(sauce);
        burger.addIngredient(filling);

        burger.moveIngredient(0,1);

        assertEquals(2,burger.ingredients.size());
        assertEquals(filling, burger.ingredients.get(0));
        assertEquals(sauce, burger.ingredients.get(1));

    }

    @Test
    public void getPriceGetsCorrectPrice() {
        burger.setBuns(bun);
        burger.addIngredient(sauce);
        burger.addIngredient(filling);

        assertEquals(230,burger.getPrice(), 0.1f);
    }
    @Test
    public void getPriceUsesBunPriceTwice() {
        burger.setBuns(bun);

        assertEquals(100, burger.getPrice(), 0.1f);

        verify(bun,times(1)).getPrice();
    }

    @Test
    public void getPriceAddsPriceOfEveryIngredient() {
        burger.setBuns(bun);
        burger.addIngredient(sauce);
        burger.addIngredient(filling);


        burger.getPrice();

        verify(sauce,times(1)).getPrice();
        verify(filling,times(1)).getPrice();

    }
    @Test
    public void getReceiptContainsBunNameTwice() {

        burger.setBuns(bun);
        String receipt = burger.getReceipt();
        int firstIndex = receipt.indexOf("black bun");
        int secondIndex = receipt.indexOf("black bun", firstIndex + 1);

        assertTrue(firstIndex >= 0);
        assertTrue(secondIndex > firstIndex);
    }

    @Test
    public void getReceiptContainsIngredientTypesAndNames() {
        burger.setBuns(bun);
        burger.addIngredient(sauce);
        burger.addIngredient(filling);

        String receipt = burger.getReceipt();

        assertTrue(receipt.contains("sauce"));
        assertTrue(receipt.contains("hot sauce"));
        assertTrue(receipt.contains("filling"));
        assertTrue(receipt.contains("cutlet"));
    }

    @Test
    public void getReceiptContainsPrice() {
        burger.setBuns(bun);
        burger.addIngredient(sauce);
        burger.addIngredient(filling);

        String receipt = burger.getReceipt();

        assertTrue(receipt.contains("Price:"));
        assertEquals(230.0f, burger.getPrice(), 0.001f);
    }

    @Test
    public void getReceiptWithoutIngredientsContainsNoIngredientNames() {
        burger.setBuns(bun);

        String receipt = burger.getReceipt();

        assertTrue(receipt.contains("black bun"));
        assertTrue(receipt.contains("Price:"));
        assertFalse(receipt.contains("hot sauce"));
        assertFalse(receipt.contains("cutlet"));
    }
}
