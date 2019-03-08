import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import data_ingestion.crawler.provider.AbstractWebDriverProvider;
import data_ingestion.crawler.provider.ChromeDriverProvider;

import static org.junit.Assert.assertTrue;

public class ChromeDriveProviderTest {
    private WebDriver driver;

    @Before
    public void setUp() {
        AbstractWebDriverProvider driverProvider = new ChromeDriverProvider();
        String driverBinPath = "C:\\Program Files (x86)\\Google\\Chrome\\driver\\chromedriver.exe";
        this.driver = driverProvider.getDriver(driverBinPath);
    }

    @Test
    public void testGetDriver() {
        assertTrue(driver.toString().matches("ChromeDriver: chrome on [^(]*\\(\\w+\\)"));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
