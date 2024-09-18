package org.apis.ecommerce.domain.repositories;

import org.apis.ecommerce.domain.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IProductRepository {
    public Optional<Product> findById(Integer id );
}
