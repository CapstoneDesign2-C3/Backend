package capstone.design.control_automation.domain.document;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

public class CameraInfo {

    @Field(type = FieldType.Keyword)
    private String camera_id;

    @GeoPointField
    private GeoPoint coordinates;
}
