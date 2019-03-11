package data_ingestion.shared.entity;

import org.springframework.data.annotation.Id;

import java.util.Map;

public class ExtractionSetup {

    @Id private String id;

    private String source;
    private Map<String, String> extractionRules;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Map<String, String> getExtractionRules() {
        return extractionRules;
    }

    public void setExtractionRules(Map<String, String> extractionRules) {
        this.extractionRules = extractionRules;
    }
}
