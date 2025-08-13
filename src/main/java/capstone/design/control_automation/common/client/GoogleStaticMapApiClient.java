package capstone.design.control_automation.common.client;

import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
public class GoogleStaticMapApiClient {

    private final RestClient googleClient;

    public GoogleStaticMapApiClient(@Qualifier("googleClient") RestClient googleClient) {
        this.googleClient = googleClient;
    }

    public ResponseEntity<byte[]> exchange(URI uri) {
        return googleClient.get()
            .uri(uri)
            .retrieve()
            .toEntity(byte[].class);
    }

}
