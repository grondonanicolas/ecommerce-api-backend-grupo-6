package org.apis.ecommerce.infrastructure.repositories;

import org.apis.ecommerce.domain.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.apis.ecommerce.domain.repositories.IProductRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer>, IProductRepository {

    @Query(value = "SELECT u FROM Product u WHERE u.isOutstanding = true")
    public List<Product> findAllOutstanding();
}
