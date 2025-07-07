package capstone.design.control_automation.category.controller.dto;

public class CategoryRequest {

    public record Upsert(
        String name,
        String engName,
        Boolean isMobile
    ) {

    }
}
