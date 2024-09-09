package org.apis.ecommerce.domain.services;

import org.apis.ecommerce.application.rest.dtos.ProductoDTO;
import org.apis.ecommerce.domain.models.Product;
import org.apis.ecommerce.domain.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;

    public ProductoDTO getProductById(Integer id) throws Exception{
        Product product = productRepository.findById(id).orElseThrow(() -> new Exception("An error has ocurred"));
        return new ProductoDTO(product.getId(),product.getDescription(), product.getPrecio(),product.getStock());
    }
}