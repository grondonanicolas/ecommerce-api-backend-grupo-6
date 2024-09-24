package org.apis.ecommerce.infrastructure.repositories;

import org.apis.ecommerce.domain.models.Product;
import org.apis.ecommerce.domain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.apis.ecommerce.domain.repositories.IProductRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Integer>, IProductRepository {

    @Query(value = "SELECT u FROM Product u WHERE u.isOutstanding = true")
    public List<Product> findAllOutstanding();

    @Query(value = "SELECT u FROM Product u WHERE u.user = :user AND u.currentState IN(DRAFT, ACTIVE)")
    public List<Product> findByUser(@Param("user") User user);
}
