
package uitests.pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Objects;

public class ConstructorPage extends BasePage {

    private final By bunsHeading =
            By.xpath("//h2[normalize-space()='Булки']");

    private final By saucesHeading =
            By.xpath("//h2[normalize-space()='Соусы']");

    private final By fillingsHeading =
            By.xpath("//h2[normalize-space()='Начинки']");

    public ConstructorPage(WebDriver driver) {
        super(driver);
    }

    private By getTab(String tabName) {
        return By.xpath(
                "//div[contains(@class,'tab_tab__')]" +
                        "[.//span[normalize-space()='" + tabName + "']]" +
                        "[not(ancestor::div[contains(@class,'BurgerIngredients_ingredients__menuContainer__Xu3Mo')])]"
        );
    }

//    @Step("Получить координату заголовка «Булки»")
//    public double getBunsHeadingPosition() {
//        return getHeadingPosition(bunsHeading);
//    }

    @Step("Выбрать вкладку «Булки»")
    public void selectBuns() {
        clickTab("Булки");
    }

    @Step("Выбрать вкладку «Соусы»")
    public void selectSauces() {
        clickTab("Соусы");
    }

    @Step("Выбрать вкладку «Начинки»")
    public void selectFillings() {
        clickTab("Начинки");
    }

    @Step("Проверить положение заголовка «Булки»")
    public boolean areBunsAtPosition(double expectedY) {
        return isHeadingAtPosition(bunsHeading, expectedY);
    }

    @Step("Проверить положение заголовка «Соусы»")
    public boolean areSaucesAtPosition(double expectedY) {
        return isHeadingAtPosition(saucesHeading, expectedY);
    }

    @Step("Проверить положение заголовка «Начинки»")
    public boolean areFillingsAtPosition(double expectedY) {
        return isHeadingAtPosition(fillingsHeading, expectedY);
    }

    @Step("Проверить положение заголовка «Булки» после клика на другой заголовок")
    public boolean areBunsAtAtPositionAfterClick(double expectedY) {
        return isHeadingAtPosition(bunsHeading, expectedY);
    }

    @Step("Проверить, что вкладка «Булки» активна")
    public boolean areBunsActive() {
        return isActive(getTab("Булки"));
    }

//    @Step("Проверить, что вкладка «Соусы» активна")
//    public boolean areSaucesActive() {
//        return isActive(getTab("Соусы"));
//    }
//
//    @Step("Проверить, что вкладка «Начинки» активна")
//    public boolean areFillingsActive() {
//        return isActive(getTab("Начинки"));
//    }

    private boolean isActive(By locator) {
        WebElement tab = wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );

        return tab.getAttribute("class")
                .contains("tab_tab_type_current__2BEPc");
    }

    private void clickTab(String tabName) {
        WebElement tab = wait.until(
                ExpectedConditions.elementToBeClickable(getTab(tabName))
        );

        tab.click();

        wait.until(driver ->
                Objects.requireNonNull(driver.findElement(getTab(tabName))
                                .getAttribute("class"))
                        .contains("tab_tab_type_current__2BEPc")
        );
    }

    private double getHeadingPosition(By headingLocator) {
        WebElement heading = wait.until(
                ExpectedConditions.visibilityOfElementLocated(headingLocator)
        );

        return ((Number) Objects.requireNonNull(((JavascriptExecutor) driver).executeScript(
                "return arguments[0].getBoundingClientRect().top;",
                heading
        ))).doubleValue();
    }

    private boolean isHeadingAtPosition(By headingLocator, double expectedY) {
        return wait.until(driver -> {
            double actualY = getHeadingPosition(headingLocator);

            System.out.println(
                    "Ожидаемая координата: " + expectedY +
                            ", фактическая координата: " + actualY
            );

            return Math.abs(actualY - expectedY) <= 5;
        });
    }
}
