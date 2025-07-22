package capstone.design.control_automation.event.client;

import org.springframework.stereotype.Component;

@Component
public class EventSummaryClientImpl implements EventSummaryClient {

    @Override
    public String getSummaryByUuid(String uuid) {
        return "summary";
    }
}
