package org.apis.ecommerce.domain.services;

import org.apis.ecommerce.domain.models.Category;
import org.apis.ecommerce.domain.models.Product;
import org.apis.ecommerce.domain.repositories.ICategoryRepository;
import org.apis.ecommerce.domain.repositories.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import org.apis.ecommerce.application.rest.services.IProductService;
import java.util.List;
import java.util.stream.Collectors;

import static org.apis.ecommerce.domain.enums.ProductState.REMOVED;

@Service
public class ProductService implements IProductService {
    
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private ICategoryRepository categoryRepository;

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

    public Product createProduct(Product product, Integer categoryID) throws Exception{
        Category category = categoryRepository.findById(categoryID).orElseThrow(() -> new Exception("Categoria no encontrada"));
        product.setCategory(category);
        return productRepository.save(product);
    }

    public void updateProductStock(Integer productID, Integer stock) throws Exception{
        Product product = productRepository.findById(productID).orElseThrow(() -> new Exception("Producto no encontrado"));
        product.setCurrentStock(stock);
        productRepository.save(product);
    }

    public void deleteProduct(Integer productID) throws Exception{
        Product product = productRepository.findById(productID).orElseThrow(() -> new Exception("Producto no encontrado"));
        product.setCurrentState(REMOVED);
        productRepository.save(product);
    }
}
