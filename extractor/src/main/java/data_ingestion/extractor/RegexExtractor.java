package data_ingestion.extractor;

import data_ingestion.extractor.exception.InvalidPageResultException;
import data_ingestion.shared.entity.ParsedDocument;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexExtractor extends AbstractExtractor {
    private static Pattern pattern = Pattern.compile("");

    @Override
    public ParsedDocument extract(Map<String, String> rules, String rawDoc) throws InvalidPageResultException {
        ParsedDocument parsedDocument = new ParsedDocument();
        Map<String, String> extractedFields = new HashMap<>();
        String extractedValue = "";
        for (Map.Entry<String, String> extractionRule : rules.entrySet()) {
            pattern = Pattern.compile(Pattern.quote(extractionRule.getValue()));
            Matcher m = pattern.matcher(rawDoc);
            if (m.find() && m.groupCount() > 0) {
                extractedValue = m.group(1);
            }
            if (
                extractedValue.equals("")
                && Arrays.stream(AbstractExtractor.requiredFields).anyMatch(extractionRule.getKey()::equals)
            ) {
                //blacklist documents that don't match requiredFields
                throw new InvalidPageResultException();
            }
            extractedFields.put(extractionRule.getKey(), extractedValue);
            extractedValue = "";
        }

        parsedDocument.setExtractedFields(extractedFields);

        return parsedDocument;
    }

}
