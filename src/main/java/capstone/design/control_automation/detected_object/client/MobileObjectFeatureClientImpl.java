package capstone.design.control_automation.detected_object.client;

import org.springframework.stereotype.Component;

@Component
public class MobileObjectFeatureClientImpl implements MobileObjectFeatureClient {

    @Override
    public String getFeatureByUuid(String uuid) {
        return "feature";
    }
}
