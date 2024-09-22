package org.apis.ecommerce.domain.repositories;

import org.apis.ecommerce.domain.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface IProductRepository {
    public Optional<Product> findById(Integer id );

    public List<Product> getProductsByCategoryId(Integer categoryId);

    public Product save(Product product);

    public List<Product> findAllOutstanding();

}
