package handler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;

public class ChildComponentsHandler {

    public static void appendChildComponents(WebDriver driver, Document parent) {
        addChildrenRecursively(driver, parent, "iframe");
    }

    private static void addChildrenRecursively(WebDriver browserDriver, Element parent, String childComponentTag) {
        String pageSource = browserDriver.getPageSource();
        Document document = Jsoup.parse(pageSource);
        parent.appendChild(document);
        Elements frameElements = document.getElementsByTag(childComponentTag);
        for (int i = 0; i < frameElements.size(); i++) {
            Element frameElement = frameElements.get(i);
            browserDriver.switchTo().frame(i);
            addChildrenRecursively(browserDriver, frameElement, childComponentTag);
            browserDriver.switchTo().parentFrame();
        }
    }

}