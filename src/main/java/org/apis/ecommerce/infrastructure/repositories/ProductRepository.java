package org.apis.ecommerce.infrastructure.repositories;

import org.apis.ecommerce.domain.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.apis.ecommerce.domain.repositories.IProductRepository;

public interface ProductRepository extends JpaRepository<Product,Integer>, IProductRepository {

}
