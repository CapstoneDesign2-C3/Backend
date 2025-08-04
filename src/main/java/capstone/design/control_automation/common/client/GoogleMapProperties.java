package capstone.design.control_automation.common.client;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "google.map")
@Setter
@Getter
public class GoogleMapProperties {
    private String apiKey;
}
