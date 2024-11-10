package org.apis.ecommerce.domain.repositories;

import org.apis.ecommerce.domain.models.Category;
import org.apis.ecommerce.domain.models.Product;
import java.util.List;
import java.util.Optional;

public interface ICategoryRepository {
    public Optional<Category> findById(Integer id );

    public Category save(Category category);
    public List<Category> findAll();
}
