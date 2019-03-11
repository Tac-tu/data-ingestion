package data_ingestion.shared.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.Map;

public class ParsedDocument {

    @Id private String id;

    private String source;
    private String url;
    private Map<String, String> extractedFields;
    @LastModifiedDate
    private Date lastModified;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getExtractedFields() {
        return extractedFields;
    }

    public void setExtractedFields(Map<String, String> extractedFields) {
        this.extractedFields = extractedFields;
    }
}
