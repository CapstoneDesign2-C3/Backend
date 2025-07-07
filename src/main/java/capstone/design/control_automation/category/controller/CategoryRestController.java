package capstone.design.control_automation.category.controller;

import capstone.design.control_automation.category.controller.dto.CategoryRequest;
import capstone.design.control_automation.category.controller.dto.CategoryResponse;
import capstone.design.control_automation.category.controller.dto.CategoryResponse.Info;
import capstone.design.control_automation.category.service.CategoryService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("api/v1/categories")
@RequiredArgsConstructor
public class CategoryRestController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Info>> findAll() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @PostMapping
    public ResponseEntity<Void> createCategory(@RequestBody CategoryRequest.Upsert upsert) {
        Long cameraId = categoryService.createCategory(upsert);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{categoryId}")
            .buildAndExpand(cameraId).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<Void> updateAlias(
        @PathVariable Long categoryId,
        @RequestBody CategoryRequest.Upsert upsert) {
        categoryService.updateCategory(categoryId, upsert);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);

        return ResponseEntity.noContent().build();
    }
}
