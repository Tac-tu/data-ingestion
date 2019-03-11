package data_ingestion.matcher;

import data_ingestion.shared.entity.MatchedResult;

import java.util.Map;

public abstract class AbstractMatcher {

    public abstract MatchedResult match(Map<String, String> extractedFields, String hittaResponse);
}
