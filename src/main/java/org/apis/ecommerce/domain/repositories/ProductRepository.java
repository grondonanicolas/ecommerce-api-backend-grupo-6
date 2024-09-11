package org.apis.ecommerce.domain.repositories;

import java.util.List;

import org.apis.ecommerce.domain.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    List<Product> getProductsByCategoryId(Integer categoryId);
}
