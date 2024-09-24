package org.apis.ecommerce.application.rest.controllers;

import jakarta.validation.Valid;
import org.apis.ecommerce.application.rest.dtos.CategoryCreateDTO;
import org.apis.ecommerce.application.rest.dtos.CategoryDTO;
import org.apis.ecommerce.application.rest.services.ICategoryService;
import org.apis.ecommerce.domain.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private final ICategoryService categoryService;

    @Autowired
    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody @Valid CategoryCreateDTO category) {
        Category categoryCreated = categoryService.createCategory(category.getCategory());
        return ResponseEntity.ok(new CategoryDTO(
                categoryCreated.getId(),
                categoryCreated.getCategory()
        ));
    }
}
