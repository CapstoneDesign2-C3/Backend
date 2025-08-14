package capstone.design.control_automation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("capstone.design.control_automation.common.properties")
public class ControlAutomationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ControlAutomationApplication.class, args);
    }

}
