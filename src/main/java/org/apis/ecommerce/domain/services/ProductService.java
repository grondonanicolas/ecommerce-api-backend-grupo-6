package org.apis.ecommerce.domain.services;

import org.apis.ecommerce.domain.models.Product;
import org.apis.ecommerce.domain.repositories.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import org.apis.ecommerce.application.rest.services.IProductService;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService {
    
    @Autowired
    private IProductRepository productRepository;


    public Product getProductById(Integer id) throws Exception{
        return productRepository.findById(id).orElseThrow(() -> new Exception("An error has ocurred"));
    }

    public List<Product> getProductsByCategoryId(Integer categoryId) {
       return productRepository.getProductsByCategoryId(categoryId);
    }

    public void addProductOutstanding(Integer productId) throws Exception {
        Product product = productRepository.findById(productId).orElseThrow(() -> new Exception("Producto no encontrado"));
        product.setOutstanding(true);
        productRepository.save(product);
    }

    public List<Product> findAllOutstanding() {
        List<Product> outstandingList = productRepository.findAllOutstanding();
        return outstandingList;
    }
}
