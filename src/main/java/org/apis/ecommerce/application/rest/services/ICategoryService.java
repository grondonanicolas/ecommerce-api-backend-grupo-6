package org.apis.ecommerce.application.rest.services;

import java.util.List;
import org.apis.ecommerce.domain.models.Category;

public interface ICategoryService {
    public Category createCategory(String description) ;
    public List<Category> listCategories();
}
