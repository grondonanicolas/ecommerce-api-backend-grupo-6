package org.apis.ecommerce.domain.services;

import jakarta.persistence.EntityNotFoundException;
import org.apis.ecommerce.domain.enums.ProductState;
import org.apis.ecommerce.domain.models.Category;
import org.apis.ecommerce.domain.models.Product;
import org.apis.ecommerce.domain.models.User;
import org.apis.ecommerce.domain.repositories.ICategoryRepository;
import org.apis.ecommerce.domain.repositories.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apis.ecommerce.application.rest.services.IProductService;
import java.util.List;

import static org.apis.ecommerce.domain.enums.ProductState.REMOVED;

@Service
public class ProductService implements IProductService {
    
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private ICategoryRepository categoryRepository;

    public Product getProductById(Integer id) throws Exception{
        return productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("An error has ocurred"));
    }

    public List<Product> getProductsByCategoryId(Integer categoryId) {
        categoryRepository.findById(categoryId).orElseThrow(() -> new EntityNotFoundException("Categoria no encontrada"));
       return productRepository.getProductsByCategoryId(categoryId);
    }

    public void addProductOutstanding(Integer productId) throws Exception {
        Product product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));

        if (!ProductState.ACTIVE.equals(product.getCurrentState())) {
            throw new IllegalArgumentException("El producto seleccionado no está activo");
        }
        
        product.setOutstanding(true);
        productRepository.save(product);
    }

    public List<Product> findAllOutstanding() {
        List<Product> outstandingList = productRepository.findAllOutstanding();
        return outstandingList.stream()
                .filter(outstanding -> ProductState.ACTIVE.equals(outstanding.getCurrentState()))
                .toList();
    }

    public Product createProduct(Product product, Integer categoryID) throws Exception{
        Category category = categoryRepository.findById(categoryID).orElseThrow(() -> new EntityNotFoundException("Categoria no encontrada"));
        product.setCategory(category);
        return productRepository.save(product);
    }

    public void updateProduct(Integer productID, String description, Integer stock, double price, Integer categoryID, ProductState state, String name) throws Exception{
    Product product =
        productRepository
            .findById(productID)
            .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));

        if (stock != null && stock != 0 && !stock.equals(product.getCurrentStock())) {
            product.setCurrentStock(stock);
        }

        if (name != null && !name.equals(product.getName())) {
            product.setName(name);
        }

        if (description != null && !description.trim().isEmpty() && !description.equals(product.getDescription())) {
            product.setDescription(description);
        }

        if (price != 0 && price != product.getPricePerUnit()) {
            product.setPricePerUnit(price);
        }

        if (categoryID != null && !categoryID.equals(product.getCategory().getId())) { // Suponiendo que Category tiene un método getId()
            Category category = categoryRepository.findById(categoryID)
                    .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada"));
            product.setCategory(category);
        }

        if (state != null && !state.equals(product.getCurrentState())) {
            product.setCurrentState(state);
        }

        productRepository.save(product);

    }

    public void deleteProduct(Integer productID) throws Exception{
        Product product = productRepository.findById(productID).orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));
        product.setCurrentState(REMOVED);
        productRepository.save(product);
    }
    public List<Product> getAllByUser(User user) throws Exception{
        List<Product>  products = productRepository.findByUser(user);
        return products;
    }
}
