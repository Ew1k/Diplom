package uitests.helpers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.nio.file.Files;
import java.nio.file.Paths;

public final class WebDriverFactory {

    private WebDriverFactory() {
    }

    public static WebDriver createDriver(String browser) {

        String selectedBrowser = browser == null
                ? "chrome"
                : browser.toLowerCase();

        if ("chrome".equals(selectedBrowser)) {
            WebDriverManager.chromedriver().setup();
            return new ChromeDriver();
        }

        if ("yandex".equals(selectedBrowser)) {

            if (!Files.exists(Paths.get(Config.YANDEX_BROWSER_PATH))) {
                throw new IllegalStateException(
                        "Не найден Яндекс Браузер: "
                                + Config.YANDEX_BROWSER_PATH
                );
            }

            if (!Files.exists(Paths.get(Config.YANDEX_DRIVER_PATH))) {
                throw new IllegalStateException(
                        "Не найден YandexDriver: "
                                + Config.YANDEX_DRIVER_PATH
                );
            }

            System.setProperty(
                    "webdriver.chrome.driver",
                    Config.YANDEX_DRIVER_PATH
            );

            ChromeOptions options = new ChromeOptions();
            options.setBinary(Config.YANDEX_BROWSER_PATH);

            return new ChromeDriver(options);
        }

        throw new IllegalArgumentException(
                "Неизвестный браузер: " + browser
        );
    }
}