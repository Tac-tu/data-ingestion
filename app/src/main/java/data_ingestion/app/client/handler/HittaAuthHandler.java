package data_ingestion.app.client.handler;

import data_ingestion.app.config.HittaConfig;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

public class HittaAuthHandler {
    private static final HttpHeaders headers = new HttpHeaders();

    public static HttpEntity<String> getHittaHttpEntity(HittaConfig hittaConfig) {
        String unixTs = "" + System.currentTimeMillis() / 1000L;
        String random16 = RandomStringUtils.randomAlphanumeric(16);
        headers.set("X-Hitta-CallerId", hittaConfig.getCallerId());
        headers.set("X-Hitta-Time", unixTs);
        headers.set("X-Hitta-Random", random16);
        String hashable = hittaConfig.getCallerId() + unixTs + hittaConfig.getApiKey() + random16;
        headers.set("X-Hitta-Hash", DigestUtils.sha1Hex(hashable));

        return new HttpEntity<>(headers);
    }
}
