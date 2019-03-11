package data_ingestion.extractor;

import data_ingestion.extractor.exception.InvalidPageResultException;
import data_ingestion.shared.entity.ParsedDocument;

import java.util.Map;

public abstract class AbstractExtractor {
    public static final String[] requiredFields = {"title", "description"};

    public abstract ParsedDocument extract(Map<String, String> rules, String rawDoc) throws InvalidPageResultException;
}
