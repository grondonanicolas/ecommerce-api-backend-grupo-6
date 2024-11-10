package org.apis.ecommerce.domain.services;

import org.apis.ecommerce.application.rest.services.ICategoryService;
import org.apis.ecommerce.domain.models.Category;
import org.apis.ecommerce.domain.repositories.CartRepository;
import org.apis.ecommerce.domain.repositories.ICategoryRepository;
import org.apis.ecommerce.domain.repositories.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService implements ICategoryService {
    private final ICategoryRepository categoryRepository;

    public CategoryService(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category createCategory(String description){
        Category newCategory = categoryRepository.save(new Category(null, description));
        return newCategory;
    }

    public  List<Category> listCategories(){
        return categoryRepository.findAll();
    }
}
