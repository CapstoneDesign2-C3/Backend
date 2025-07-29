package capstone.design.control_automation.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient restClient() {
        return RestClient.create();
    }

    @Bean
    public RestClient googleClient() {
        return RestClient.builder()
            .baseUrl("https://maps.googleapis.com")
            .build();
    }
}
