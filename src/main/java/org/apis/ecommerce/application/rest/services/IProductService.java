package org.apis.ecommerce.rest.services;

import org.apis.ecommerce.domain.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductService {
    public Product getProductById(Integer id) throws Exception;
}
