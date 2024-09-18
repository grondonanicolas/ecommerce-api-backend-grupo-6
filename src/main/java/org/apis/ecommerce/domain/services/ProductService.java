package org.apis.ecommerce.domain.services;

import org.apis.ecommerce.domain.models.Product;
import org.apis.ecommerce.domain.repositories.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apis.ecommerce.rest.services.IProductService;

@Service
public class ProductService implements IProductService {
    
    @Autowired
    private IProductRepository productRepository;

    public Product getProductById(Integer id) throws Exception{
        return productRepository.findById(id).orElseThrow(() -> new Exception("An error has ocurred"));
    }
}
