package data_ingestion.shared.entity;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;

public class MatchedResult {

    @Id private String id;

    private String source;
    private String url;
    private ArrayList<String> hittaIds;

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

    public ArrayList<String> getHittaIds() {
        return hittaIds;
    }

    public void setHittaIds(ArrayList<String> hittaIds) {
        this.hittaIds = hittaIds;
    }
}
