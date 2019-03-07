package provider;

import handler.ChildComponentsHandler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;

public class PageContentProvider {

    public Document getContent(WebDriver driver, String url, boolean appendChildren) {
        driver.get(url);
        driver.switchTo().defaultContent();

        Document parent = Jsoup.parse(driver.getPageSource());

        if (appendChildren) {
            ChildComponentsHandler.appendChildComponents(driver, parent);
        }

        return parent;
    }
}