package capstone.design.control_automation.category.service;

import capstone.design.control_automation.category.controller.dto.CategoryRequest.Upsert;
import capstone.design.control_automation.category.controller.dto.CategoryResponse;
import capstone.design.control_automation.category.controller.dto.CategoryResponse.Info;
import capstone.design.control_automation.category.entity.Category;
import capstone.design.control_automation.category.repository.CategoryJpaRepository;
import capstone.design.control_automation.common.exception.ErrorCode;
import capstone.design.control_automation.common.exception.ErrorException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryJpaRepository categoryJpaRepository;

    public List<Info> findAll() {
        return categoryJpaRepository.findAll()
            .stream().map(CategoryResponse.Info::from).toList();
    }

    @Transactional
    public Long createCategory(Upsert upsert) {
        Category category = new Category(
            upsert.name(),
            upsert.engName(),
            upsert.isMobile()
        );

        categoryJpaRepository.save(category);

        return category.getId();
    }

    @Transactional
    public void updateCategory(Long categoryId, Upsert upsert) {
        Category category = categoryJpaRepository.findById(categoryId)
            .orElseThrow(() -> new ErrorException(ErrorCode.CATEGORY_NOT_FOUND));

        category.updateInfo(upsert.name(), upsert.engName(), upsert.isMobile());
    }

    public void deleteCategory(Long categoryId) {
        categoryJpaRepository.deleteById(categoryId);
    }
}
