package org.apis.ecommerce.domain.repositories;

import org.apis.ecommerce.domain.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Integer> {

}
