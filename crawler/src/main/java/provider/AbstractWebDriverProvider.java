package provider;

import org.openqa.selenium.WebDriver;

public abstract class AbstractWebDriverProvider {

    public abstract WebDriver getDriver(String binaryPath);
}
