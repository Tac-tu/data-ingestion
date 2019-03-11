package data_ingestion.matcher;

import data_ingestion.shared.entity.MatchedResult;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
import java.util.regex.Pattern;

public class RegexMatcher extends AbstractMatcher {
    private static Pattern pattern = Pattern.compile("");

    @Override
    public MatchedResult match(Map<String, String> extractedFields, String hittaResponse) {
        MatchedResult matchedResult = new MatchedResult();
        Map<String, Integer> idScores = new HashMap<>();

        Map<String, String> hittaCompanies = getHittaCompaniesFromResponse(hittaResponse);

        for (Map.Entry<String, String> hittaComp : hittaCompanies.entrySet()) {
            int matchedFields = 0;
            for (Map.Entry<String, String> extractedField : extractedFields.entrySet()) {
                if (
                    Pattern.compile(Pattern.quote(extractedField.getValue()), Pattern.CASE_INSENSITIVE)
                        .matcher(hittaComp.getValue()).find()
                ) {
                    matchedFields++;
                }
            }
            idScores.put(hittaComp.getKey(), matchedFields);
        }

        matchedResult.setHittaIds(getSortedIdsByMatchRate(idScores));

        return matchedResult;
    }

    private Map<String, String> getHittaCompaniesFromResponse(String hittaResponse) {
        JSONObject jsonObject = new JSONObject(hittaResponse);
        JSONObject result = jsonObject.getJSONObject("result");
        JSONArray companies = (JSONArray) result.get("company");

        Map<String, String> hittaCompanies = new HashMap<>();

        for (int i=0; i<companies.length(); i++){
            hittaCompanies.put(
                companies.getJSONObject(i).getString("id"),
                companies.getString(i)
            );
        }

        return hittaCompanies;
    }

    private ArrayList<String> getSortedIdsByMatchRate(Map<String, Integer> unsortedMap) {
        //convert map to a List
        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(unsortedMap.entrySet());

        //sorting the list with a comparator
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        //convert sortedMap back to Map
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return new ArrayList<>(sortedMap.keySet());
    }

}
