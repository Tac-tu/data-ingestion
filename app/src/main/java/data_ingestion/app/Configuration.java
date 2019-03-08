package data_ingestion.app;

public class Configuration {
    private String chromeDriverPath;
    private int crawledPagesLimit;
    private String log;

    public String getChromeDriverPath() {
        return chromeDriverPath;
    }

    public int getCrawledPagesLimit() {
        return crawledPagesLimit;
    }

    public String getLog() {
        return log;
    }
}
