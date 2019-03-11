package data_ingestion.shared.entity;

import org.springframework.data.annotation.Id;

public class SourceSetup {

    @Id private String id;

    private String source;
    private String url;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
