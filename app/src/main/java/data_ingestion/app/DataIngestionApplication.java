package data_ingestion.app;

import data_ingestion.crawler.provider.ChromeDriverProvider;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import data_ingestion.crawler.Spider;

import java.util.Map;
import java.util.Scanner;


public class DataIngestionApplication extends BaseApplication {
    private static final Logger log = LoggerFactory.getLogger(DataIngestionApplication.class);
    private static final Spider spider = new Spider();
    private Configuration configuration;

    public DataIngestionApplication(Configuration configuration) {
        this.configuration = configuration;
    }

    public void start() {
        super.start();

        log.info("Starting things!");

        try {
            WebDriver driver = (new ChromeDriverProvider()).getDriver(configuration.getChromeDriverPath());

            Scanner reader = new Scanner(System.in);
            System.out.println("Insert url to be crawled: ");
            String url = reader.nextLine();

            Map<String, Document> crawledPages = spider.crawl(driver, url, configuration.getCrawledPagesLimit());
            for (String key: crawledPages.keySet()) {
                System.out.println(key);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void stop() {
        log.info("Stop everything!");
    }
}