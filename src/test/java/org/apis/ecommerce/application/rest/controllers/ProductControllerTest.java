package org.apis.ecommerce.application.rest.controllers;

import org.apis.ecommerce.application.rest.dtos.ProductDTO;
import org.apis.ecommerce.application.rest.dtos.OutstandingDTO;
import org.apis.ecommerce.application.rest.dtos.ProductStockDTO;
import org.apis.ecommerce.domain.models.Category;
import org.apis.ecommerce.domain.models.Product;
import org.apis.ecommerce.application.rest.services.IProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;

import static org.apis.ecommerce.domain.enums.ProductState.ACTIVE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductControllerTest {

    @Mock
    private IProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListProducts() {
        String category = "electronics";
        Boolean recentlyViewed = true;

        String response = productController.listProducts(category, recentlyViewed);

        assertEquals("Listando productos - Categoría: electronics, Vistos recientemente: true", response);
    }

    @Test
    void testGetProductById() throws Exception {
        Integer id = 1;
    Product product =
        new Product(
            1, "Remera Nike",ACTIVE, 100.0, 10, false, new Category(1, "Ropa"), LocalDateTime.now(), LocalDateTime.now());

    when(productService.getProductById(id)).thenReturn(product);

        ProductDTO result = productController.getProductById(id);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Remera Nike", result.getDescription());
        assertEquals(100.0, result.getPrice());
        assertEquals(10, result.getStock());
    }

    @Test
    void testCreateProduct() throws Exception {
        ProductDTO productDTO = new ProductDTO(null, "Nuevo Producto", 200.0, 20);
        Product newProduct =
                new Product(
                        1, "Nuevo Producto", ACTIVE,200.0, 20, false,new Category(1, "Ropa"), LocalDateTime.now(), LocalDateTime.now());

        when(productService.createProduct(any(Product.class))).thenReturn(newProduct);

        ProductDTO result = productController.createProduct(productDTO);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Nuevo Producto", result.getDescription());
        assertEquals(200.0, result.getPrice());
        assertEquals(20, result.getStock());
    }

    @Test
    void testUpdateProductStock() throws Exception {
        ProductStockDTO stockDTO = new ProductStockDTO(30);
        Integer productId = 1;

        doNothing().when(productService).updateProductStock(stockDTO.getQuantity(), productId);

        productController.updateProductStock(stockDTO, productId);

        verify(productService, times(1)).updateProductStock(stockDTO.getQuantity(), productId);
    }

    @Test
    void testDeleteProduct() throws Exception {
        Integer productId = 1;

        doNothing().when(productService).deleteProduct(productId);

        productController.deleteProduct(productId);

        verify(productService, times(1)).deleteProduct(productId);
    }

    @Test
    void testGetProductsByCategory() throws Exception {
        Integer categoryId = 1;
        List<Product> products = List.of(new Product(1, "Producto 1",ACTIVE, 100.0, 10, false,new Category(1, "Ropa"), LocalDateTime.now(), LocalDateTime.now()));

        when(productService.getProductsByCategoryId(categoryId)).thenReturn(products);

        List<ProductDTO> result = productController.getProductsByCategory(categoryId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Producto 1", result.get(0).getDescription());
    }

    @Test
    void testAddProductOutstanding() throws Exception {
        OutstandingDTO outstandingDTO = new OutstandingDTO(1);

        doNothing().when(productService).addProductOutstanding(outstandingDTO.getProductId());

        productController.addProductOutstanding(outstandingDTO);

        verify(productService, times(1)).addProductOutstanding(outstandingDTO.getProductId());
    }

    @Test
    void testGetOutstandingProducts() throws Exception {
        List<Product> products = List.of(new Product(1, "Remera Nike",ACTIVE, 100.0, 10, false,new Category(1, "Ropa"), LocalDateTime.now(), LocalDateTime.now()));

        when(productService.findAllOutstanding()).thenReturn(products);

        List<ProductDTO> result = productController.getOutstandingProducts();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Remera Nike", result.get(0).getDescription());
    }
}
