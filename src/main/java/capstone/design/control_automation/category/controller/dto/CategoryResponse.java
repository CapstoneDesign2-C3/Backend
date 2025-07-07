package capstone.design.control_automation.category.controller.dto;

import capstone.design.control_automation.category.entity.Category;

public class CategoryResponse {

    public record Info(
        String name,
        String engName
    ) {

        public static Info from(Category category) {
            return new Info(category.getName(), category.getEngName());
        }
    }
}
