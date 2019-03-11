package data_ingestion.app;

import data_ingestion.app.client.exception.NotFoundException;
import data_ingestion.app.client.handler.HittaAuthHandler;
import data_ingestion.app.client.handler.RestResponseErrorHandler;
import data_ingestion.app.config.Configuration;
import data_ingestion.crawler.Spider;
import data_ingestion.crawler.provider.ChromeDriverProvider;
import data_ingestion.extractor.AbstractExtractor;
import data_ingestion.extractor.RegexExtractor;
import data_ingestion.extractor.exception.InvalidPageResultException;
import data_ingestion.matcher.AbstractMatcher;
import data_ingestion.matcher.RegexMatcher;
import data_ingestion.shared.entity.ExtractionSetup;
import data_ingestion.shared.entity.MatchedResult;
import data_ingestion.shared.entity.ParsedDocument;
import data_ingestion.shared.entity.SourceSetup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.*;


public class DataIngestionApplication extends BaseApplication {
    private static final Logger log = LoggerFactory.getLogger(DataIngestionApplication.class);
    private static final RestTemplate restClient = buildRestClient();
    private static final Spider spider = new Spider();
    private static final AbstractExtractor extractor = new RegexExtractor();
    private static final AbstractMatcher matcher = new RegexMatcher();
    private Configuration configuration;
    private String sourceName;

    public DataIngestionApplication(String sourceName, Configuration configuration) {
        this.sourceName = sourceName;
        this.configuration = configuration;
    }

    public void start() {
        super.start();

        log.info("Starting things!");

        try (Scanner reader = new Scanner(System.in)) {
            SourceSetup sourceSetup = handleSourceSetup(configuration.getPersisterUrl(), reader);
            WebDriver driver = (new ChromeDriverProvider()).getDriver(configuration.getChromeDriverPath());
            Map<String, Document> crawledPages = spider.crawl(driver, sourceSetup.getUrl(), configuration.getCrawledPagesLimit());
            ExtractionSetup extractionSetup = handleExtractionSetup(configuration.getPersisterUrl(), reader);
            reader.close();
            ArrayList<ParsedDocument> parsedDocuments = new ArrayList<>();
            for (Map.Entry<String, Document> crawledPage : crawledPages.entrySet()) {
                ParsedDocument parsedDocument;
                try {
                    parsedDocument = extractor.extract(extractionSetup.getExtractionRules(), crawledPage.getValue().outerHtml());
                } catch (InvalidPageResultException e) {
                    continue;
                }
                System.out.println("Parsed page: " + crawledPage.getKey());
                parsedDocument.setUrl(crawledPage.getKey());
                parsedDocument.setSource(sourceName);
                parsedDocuments.add(parsedDocument);
            }
            for (ParsedDocument pd : parsedDocuments) {
                String search = pd.getExtractedFields().get("title");
                if (search.contains(" ")) {
                    search = search.substring(0, search.indexOf(" "));
                }
                search = pd.getExtractedFields().getOrDefault("category", search);
                String url = configuration.getHitta().getApiUrl() + "?what=" + search + "&page.number=1&page.size=10";
                if (pd.getExtractedFields().containsKey("city")) {
                    url += "&where=" + pd.getExtractedFields().get("city");
                }
                ResponseEntity<String> response = restClient.exchange(
                        url,
                        HttpMethod.GET, HittaAuthHandler.getHittaHttpEntity(configuration.getHitta()),
                        String.class
                );
                MatchedResult matchedResult = matcher.match(pd.getExtractedFields(), response.getBody());
                if (matchedResult.getHittaIds().size() > 0) {
                    //persist match result
                    System.out.println("Matched ids sorted by relevance:\n" + matchedResult.getHittaIds().toString());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private SourceSetup handleSourceSetup(String persisterUrl, Scanner reader) {
        String findSourcePath = "s-setup/search/findOneBySource?source="+sourceName;
        SourceSetup sourceSetup;
        try {
            sourceSetup = restClient.getForObject(persisterUrl + findSourcePath, SourceSetup.class);
            System.out.println("The current source url is: " + sourceSetup.getUrl() + "\nDo you want to change it? (y/n)");
            if (reader.nextLine().equals("y")) {
                System.out.println("Type new source url:");
                sourceSetup.setUrl(reader.nextLine());
                log.info("PATCH URL: " + persisterUrl + "s-setup/" + sourceSetup.getId());
                restClient.patchForObject(persisterUrl + "s-setup/" + sourceSetup.getId(), sourceSetup, SourceSetup.class);
                System.out.println("Setup updated!");
            }
        } catch (NotFoundException e) {
            System.out.println("There is no setup for the current source. Insert source url:");
            String sourceUrl = reader.nextLine();
            sourceSetup = new SourceSetup();
            sourceSetup.setSource(sourceName);
            sourceSetup.setUrl(sourceUrl);
            restClient.postForObject(persisterUrl + "s-setup", sourceSetup, SourceSetup.class);
            System.out.println("Setup saved!");
        }

        return sourceSetup;
    }

    private ExtractionSetup handleExtractionSetup(String persisterUrl, Scanner reader) throws Exception {
        String findExtractionPath = "e-setup/search/findOneBySource?source="+sourceName;
        ExtractionSetup extractionSetup;
        Map<String, String> extractionRules = new HashMap<>();
        String addExRulesMessage = "Add new extraction rules (REQUIRED: " + Arrays.toString(AbstractExtractor.requiredFields) + "):";
        try {
            extractionSetup = restClient.getForObject(persisterUrl + findExtractionPath, ExtractionSetup.class);
            System.out.println("Found extraction setup!\nDo you want to change it? (y/n)");
            if (reader.nextLine().equals("y")) {
                System.out.println(addExRulesMessage);
                addExtractionRules(reader, extractionRules);
                extractionSetup.setExtractionRules(extractionRules);
                log.info("PATCH URL: " + persisterUrl + "e-setup/" + extractionSetup.getId());
                restClient.patchForObject(persisterUrl + "e-setup/" + extractionSetup.getId(), extractionSetup, ExtractionSetup.class);
                System.out.println("Setup updated!");
            }
        } catch (NotFoundException e) {
            System.out.println("There is no extraction setup for the current source.\n" + addExRulesMessage);
            extractionSetup = new ExtractionSetup();
            extractionSetup.setSource(sourceName);
            addExtractionRules(reader, extractionRules);
            extractionSetup.setExtractionRules(extractionRules);
            restClient.postForObject(persisterUrl + "e-setup", extractionSetup, ExtractionSetup.class);
            System.out.println("Setup saved!");
        }

        return extractionSetup;
    }

    private void addExtractionRules(Scanner reader, Map<String, String> extractionRules) throws Exception {
        String ruleName;
        String rulePattern;
        while (true) {
            System.out.println("rule name:");
            ruleName = reader.nextLine();
            System.out.println("pattern:");
            rulePattern = reader.nextLine();
            extractionRules.put(ruleName, rulePattern);
            System.out.println("Add another? (y/n)");
            if (! reader.nextLine().equals("y")) {
                break;
            }
        }

        for (String rName : AbstractExtractor.requiredFields) {
            if (! extractionRules.containsKey(rName)) {
                throw new Exception("REQUIRED EXTRACTION FIELDS MISSING!");
            }
        }
    }

    private static RestTemplate buildRestClient() {
        return new RestTemplateBuilder().errorHandler(new RestResponseErrorHandler()).build();
    }

    public void stop() {
        log.info("Stop everything!");
    }
}