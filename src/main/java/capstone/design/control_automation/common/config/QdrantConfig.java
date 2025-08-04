package capstone.design.control_automation.common.config;

import io.qdrant.client.QdrantClient;
import io.qdrant.client.QdrantGrpcClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QdrantConfig {

    @Bean
    public QdrantClient setClient(){
        return new QdrantClient(QdrantGrpcClient.newBuilder("localhost").build());
    }
}
