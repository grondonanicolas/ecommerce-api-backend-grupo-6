package org.apis.ecommerce.application.rest.services;

import java.util.List;

import org.apis.ecommerce.domain.models.Product;

public interface IProductService {
    public Product getProductById(Integer id) throws Exception;

    public List<Product> getProductsByCategoryId(Integer id) throws Exception;

    public void addProductOutstanding(Integer productId) throws Exception;

    public List<Product> findAllOutstanding() throws Exception;

    public Product createProduct(Product product) throws Exception;

    public void updateProductStock(Integer productID, Integer stock) throws Exception;

    public void deleteProduct(Integer productID) throws Exception;
}
