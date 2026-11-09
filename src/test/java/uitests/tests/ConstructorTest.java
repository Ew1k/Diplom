
package uitests.tests;

import org.junit.jupiter.api.Test;
import uitests.setUp.BaseTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConstructorTest extends BaseTest {
    double expectedY = 243;

    @Test
    public void bunsTabCategoryToExpectedPosition() {


        constructorPage.selectBuns();

        assertTrue(
                constructorPage.areBunsAtPosition(expectedY),
                "Заголовок «Булки» должен находиться на ожидаемой координате"
        );
    }
    @Test
    public void saucesTabMovesCategoryToExpectedPosition() {

        constructorPage.selectSauces();

        assertTrue(
                constructorPage.areSaucesAtPosition(expectedY),
                "Заголовок «Соусы» должен находиться на ожидаемой координате"
        );
    }

    @Test
    public void fillingsTabMovesCategoryToExpectedPosition() {


        constructorPage.selectFillings();

        assertTrue(
                constructorPage.areFillingsAtPosition(expectedY),
                "Заголовок «Начинки» должен находиться на ожидаемой координате"
        );
    }

    @Test
    public void bunsTabMovesCategoryToExpectedPosition() {

        constructorPage.selectSauces();
        constructorPage.selectBuns();

        assertTrue(
                constructorPage.areBunsAtAtPositionAfterClick(expectedY),
                "Заголовок «Булки» должен находиться на ожидаемой координате"
        );

    }


}
