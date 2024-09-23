package org.apis.ecommerce.domain.repositories;

import org.apis.ecommerce.domain.models.Category;
import org.apis.ecommerce.domain.models.Product;

import java.util.Optional;

public interface ICategoryRepository {
    public Optional<Category> findById(Integer id );
}
