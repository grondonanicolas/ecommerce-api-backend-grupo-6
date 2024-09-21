package org.apis.ecommerce.domain.services;

import org.apis.ecommerce.domain.models.Outstanding;
import org.apis.ecommerce.domain.models.Product;
import org.apis.ecommerce.domain.repositories.IOutstandingRepository;
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

    @Autowired
    private IOutstandingRepository outstandingRepository;

    public Product getProductById(Integer id) throws Exception{
        return productRepository.findById(id).orElseThrow(() -> new Exception("An error has ocurred"));
    }

    public List<Product> getProductsByCategoryId(Integer categoryId) {
       return productRepository.getProductsByCategoryId(categoryId);
    }

    public void addProductOutstanding(Integer productId) throws Exception {
        Product product = productRepository.findById(productId).orElseThrow(() -> new Exception("Producto no encontrado"));
        Outstanding outstanding = new Outstanding(product);
        outstandingRepository.save(outstanding);
    }

    public List<Product> getOutstandingProducts() {
        List<Outstanding> outstandingList = outstandingRepository.findAll();
        return outstandingList.stream()
                              .map(Outstanding::getProduct)
                              .collect(Collectors.toList());
    }
}
