package org.apis.ecommerce.application.rest.controllers;

import org.apis.ecommerce.application.rest.dtos.ProductCreateDTO;
import org.apis.ecommerce.application.rest.dtos.ProductDTO;
import org.apis.ecommerce.application.rest.dtos.OutstandingDTO;
import org.apis.ecommerce.application.rest.dtos.ProductUpdateDTO;
import org.apis.ecommerce.domain.enums.ProductState;
import org.apis.ecommerce.domain.models.Category;
import org.apis.ecommerce.domain.models.Product;
import org.apis.ecommerce.application.rest.services.IProductService;
import org.apis.ecommerce.domain.models.User;
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
    void testGetProductById() throws Exception {
        Integer id = 1;
    Product product =
        new Product(
            1, "Remera Nike",ACTIVE, 100.0, 10, false, new Category(1, "Ropa"), LocalDateTime.now(), LocalDateTime.now(), new User(), "nombre", "image_url");

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
    ProductCreateDTO productDTO =
        new ProductCreateDTO( "Nuevo Producto", "nombre", 200.0, 20, 1, "image_url");
        Product newProduct =
                new Product(
                        1, "Nuevo Producto", ACTIVE,200.0, 20, false,new Category(1, "Ropa"), LocalDateTime.now(), LocalDateTime.now(),new User(), "nombre", "image_url");

        when(productService.createProduct(any(Product.class), eq(1))).thenReturn(newProduct);

        ProductDTO result = productController.createProduct(productDTO, User.builder().build());

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Nuevo Producto", result.getDescription());
        assertEquals(200.0, result.getPrice());
        assertEquals(20, result.getStock());
    }

    @Test
    void testUpdateProductStock() throws Exception {
        // Datos para el DTO de actualización de producto
        Integer productId = 1;
        String description = "New description";
        Integer stock = 30;
        double price = 100.0;
        Integer categoryId = 2;
        ProductState state = ProductState.ACTIVE;
        User user =  new User();
        ProductUpdateDTO productUpdateDTO = new ProductUpdateDTO(description, "nombre", price, stock, categoryId,state, "image_url");

    doNothing()
        .when(productService)
        .updateProduct(
            productId, description, stock, price, categoryId, state, "nombre", user, "image_url");

        productController.updateProduct(productUpdateDTO, productId, user);

        verify(productService, times(1)).updateProduct(productId, description, stock, price, categoryId, state, "nombre",user , "image_url");
    }

    @Test
    void testDeleteProduct() throws Exception {
        Integer productId = 1;
        User user = new User();
        doNothing().when(productService).deleteProduct(productId, user);

        productController.deleteProduct(productId, user);

        verify(productService, times(1)).deleteProduct(productId, user);
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
        List<Product> products = List.of(new Product(1, "Remera Nike",ACTIVE, 100.0, 10, false,new Category(1, "Ropa"), LocalDateTime.now(), LocalDateTime.now(), new User(), "nombre", "image_url"));

        when(productService.findAllOutstanding()).thenReturn(products);

        List<ProductDTO> result = productController.getOutstandingProducts();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Remera Nike", result.get(0).getDescription());
    }
}
