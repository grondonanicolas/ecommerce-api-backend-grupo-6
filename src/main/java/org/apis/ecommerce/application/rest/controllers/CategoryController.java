package org.apis.ecommerce.application.rest.controllers;

import org.apache.coyote.Response;
import org.apis.ecommerce.application.rest.dtos.*;
import org.apis.ecommerce.application.rest.services.ICategoryService;
import org.apis.ecommerce.domain.models.Category;
import org.apis.ecommerce.domain.models.Transaction;
import org.apis.ecommerce.domain.models.User;
import org.apis.ecommerce.domain.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryCreateDTO category) {
        Category categoryCreated = categoryService.createCategory(category.getCategory());
        return ResponseEntity.ok(new CategoryDTO(
                categoryCreated.getId(),
                categoryCreated.getCategory()
        ));
    }
}
