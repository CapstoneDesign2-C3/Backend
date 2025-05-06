package capstone.design.control_automation.event.init;

import capstone.design.control_automation.event.entity.EmergencyStatus;
import capstone.design.control_automation.event.entity.Event;
import capstone.design.control_automation.event.repository.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class EventInitializer  implements CommandLineRunner {
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public void run(String... args) {
        if (eventRepository.count() == 0) {
            for (EmergencyStatus status : EmergencyStatus.values()) {
                Event event = new Event(status);
                eventRepository.save(event);
            }
        }
    }
}
