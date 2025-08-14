package capstone.design.control_automation.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "google.static-map.style")
public record GoogleStaticMapStyleProperties(
    String width,
    String height,
    String color,
    Integer weight
) {

    public String mapSize() {
        return width + "x" + height;
    }
}
