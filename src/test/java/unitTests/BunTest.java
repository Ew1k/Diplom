package unitTests;

import org.junit.jupiter.api.Test;
import praktikum.Bun;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BunTest {

    @Test
     void  ShouldCreateBunWithCorrectNameAndPrice () {
    Bun bun = new Bun("Black", 100);

    assertEquals("Black", bun.name);
    assertEquals(100, bun.price);
  }

    @Test
    void ShouldReturnCorrectName () {
        Bun bun = new Bun("White", 100);

        assertEquals("White", bun.getName());

    }
    @Test
    void ShouldReturnCorrectPrice() {
        Bun bun = new Bun("White", 55);

        assertEquals(55, bun.getPrice());

    }
}
