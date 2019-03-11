package data_ingestion.app.config;

public class Configuration {
    private String chromeDriverPath;
    private int crawledPagesLimit;
    private String persisterUrl;
    private HittaConfig hitta;
    private String log;

    public String getChromeDriverPath() {
        return chromeDriverPath;
    }

    public int getCrawledPagesLimit() {
        return crawledPagesLimit;
    }

    public String getPersisterUrl() {
        return persisterUrl;
    }

    public HittaConfig getHitta() {
        return hitta;
    }

    public String getLog() {
        return log;
    }
}
