package capstone.design.control_automation.common.client;

import capstone.design.control_automation.common.exception.ExternalApiErrorCode;
import capstone.design.control_automation.common.exception.ExternalApiException;
import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
public class GoogleStaticMapApiClient {

    private static final String STATIC_MAP_WARNING_HEADER = "X-Staticmap-API-Warning";

    private final RestClient googleClient;

    public GoogleStaticMapApiClient(@Qualifier("googleClient") RestClient googleClient) {
        this.googleClient = googleClient;
    }

    public byte[] exchange(URI uri) {
        return googleClient.get()
            .uri(uri)
            .exchange((req, res) -> {
                HttpStatus statusCode = (HttpStatus) res.getStatusCode();
                if (statusCode.isSameCodeAs(HttpStatus.BAD_REQUEST)) {
                    throw new ExternalApiException(ExternalApiErrorCode.GOOGLE_STATIC_MAP_INVALID_PARAM);
                }
                if (statusCode.isSameCodeAs(HttpStatus.FORBIDDEN)) {
                    throw new ExternalApiException(ExternalApiErrorCode.GOOGLE_STATIC_MAP_INVALID_KEY);
                }

                HttpHeaders headers = res.getHeaders();
                if (statusCode.is2xxSuccessful() && headers.containsKey(STATIC_MAP_WARNING_HEADER)) {
                    log.warn("Google Static Map warning : {}", headers.get(STATIC_MAP_WARNING_HEADER));
                }

                return res.bodyTo(byte[].class);
            });
    }

}
