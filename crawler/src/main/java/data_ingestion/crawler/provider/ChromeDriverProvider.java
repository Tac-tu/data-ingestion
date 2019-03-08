package data_ingestion.crawler.provider;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeDriverProvider extends AbstractWebDriverProvider {

    @Override
    public ChromeDriver getDriver(String binaryPath) {
        System.setProperty("webdriver.chrome.driver", binaryPath);

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("window-size=1920,1080");
        chromeOptions.addArguments("disable-extensions");
        chromeOptions.addArguments("headless");

        return new ChromeDriver(chromeOptions);
    }
}
