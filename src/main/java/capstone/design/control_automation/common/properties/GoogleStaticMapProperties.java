package capstone.design.control_automation.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "google.map")
public record GoogleStaticMapProperties(
    String apiKey
) {

}
