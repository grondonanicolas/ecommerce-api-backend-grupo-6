package org.apis.ecommerce.application.rest.services;

import java.util.List;

import org.apis.ecommerce.domain.enums.ProductState;
import org.apis.ecommerce.domain.models.Product;
import org.apis.ecommerce.domain.models.User;

public interface IProductService {
    public Product getProductById(Integer id) throws Exception;

    public List<Product> getProductsByCategoryId(Integer id) throws Exception;

    public void addProductOutstanding(Integer productId) throws Exception;

    public List<Product> findAllOutstanding() throws Exception;

    public Product createProduct(Product product, Integer categoryID) throws Exception;

    public void updateProduct(Integer productID, String description, Integer stock, double price, Integer categoryID, ProductState state, String name, User user) throws Exception;

    public void deleteProduct(Integer productID, User user) throws Exception;
    public List<Product> getAllByUser(User user) throws Exception;
}
