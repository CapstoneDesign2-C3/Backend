package capstone.design.control_automation.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "qdrant.data")
public record QdrantProperties(String collectionName, String eventTypeName, String objectTypeName) {
}
