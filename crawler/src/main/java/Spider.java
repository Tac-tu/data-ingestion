import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.WebDriver;
import provider.PageContentProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Spider {

    public Map<String, Document> crawl(WebDriver driver, String startUrl, int maxPages) {
        String urlRegex = "^https?://" + startUrl.substring(startUrl.indexOf("://") + "://".length()) + ".*$";
        ArrayList<String> pagesToVisit = new ArrayList<>();
        Map<String,Document> pagesVisited = new HashMap<>();

        PageContentProvider contentProvider = new PageContentProvider();

        String currentUrl = startUrl;
        do
        {
            Document content = contentProvider.getContent(driver, currentUrl, true);

            List<Element> elements = content.select("a[href]");
            addPagesToVisit(elements, urlRegex, pagesToVisit);

            pagesVisited.put(currentUrl,content);

            if (pagesVisited.size() >= maxPages) {
                break;
            }

            currentUrl = getNextUrl(currentUrl, pagesToVisit, pagesVisited);
        } while (!pagesVisited.containsKey(currentUrl));

        driver.quit();

        return pagesVisited;
    }

    private static String getNextUrl(String currentUrl, ArrayList<String> pagesToVisit, Map<String,Document> pagesVisited) {
        while (!pagesToVisit.isEmpty() && pagesVisited.containsKey(currentUrl)) {
            currentUrl = pagesToVisit.remove(0);
        }
        return currentUrl;
    }

    private static void addPagesToVisit(List<Element> linksOnPage, String urlRegex, ArrayList<String> pagesToVisit) {
        for (Element link : linksOnPage) {
            //add links to 'pages to visit'
            String absUrl = link.absUrl("href");
            if (absUrl.matches(urlRegex)) {
                pagesToVisit.add(absUrl);
            }
        }
    }

}