package capstone.design.control_automation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ControlAutomationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ControlAutomationApplication.class, args);
    }

}
