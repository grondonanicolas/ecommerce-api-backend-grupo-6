package org.apis.ecommerce.domain.services;

import org.apis.ecommerce.domain.enums.ProductState;
import org.apis.ecommerce.domain.models.Category;
import org.apis.ecommerce.domain.models.Product;
import org.apis.ecommerce.domain.models.User;
import org.apis.ecommerce.domain.repositories.ICategoryRepository;
import org.apis.ecommerce.domain.repositories.IProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.apis.ecommerce.domain.enums.ProductState.ACTIVE;
import static org.apis.ecommerce.domain.enums.ProductState.REMOVED;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private IProductRepository productRepository;

    @Mock
    private ICategoryRepository categoryRepository;
    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetProductById() throws Exception {
        Integer id = 1;
        Product product = new Product();
        product.setId(id);
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void testGetProductById_NotFound() {
        Integer id = 1;
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            productService.getProductById(id);
        });

        assertEquals("An error has ocurred", exception.getMessage());
    }

    @Test
    void testGetProductsByCategoryId() {
        Integer categoryId = 1;
        List<Product> products = List.of(new Product(1, "Producto 1",ACTIVE, 100.0, 10, false, new Category(1, "Ropa"), LocalDateTime.now(), LocalDateTime.now(), new User(), "nombre"));

        when(productRepository.getProductsByCategoryId(categoryId)).thenReturn(products);

        List<Product> result = productService.getProductsByCategoryId(categoryId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Producto 1", result.get(0).getDescription());
    }

    @Test
    void testAddProductOutstanding() throws Exception {
        Integer productId = 1;
        Product product = new Product();
        product.setId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        productService.addProductOutstanding(productId);

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testAddProductOutstanding_ProductNotFound() {
        Integer productId = 1;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            productService.addProductOutstanding(productId);
        });

        assertEquals("Producto no encontrado", exception.getMessage());
    }

    @Test
    void testGetOutstandingProducts() {
    Product outstandingProducts =
        new Product(
            1,
            "Remera Nike",
            ACTIVE,
            100.0,
            10,
            true,
            new Category(1, "Ropa"),
            LocalDateTime.now(),
            LocalDateTime.now(),
            new User(),
                "nombre");
        List<Product> outstandingList = List.of(outstandingProducts);

        when(productRepository.findAllOutstanding()).thenReturn(outstandingList);

        List<Product> result = productService.findAllOutstanding();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Remera Nike", result.get(0).getDescription());
    }

    @Test
    void testCreateProduct() throws Exception {
        Product product = new Product(null, "Nuevo Producto",ACTIVE, 200.0, 20, false,new Category(1, "Ropa"), LocalDateTime.now(), LocalDateTime.now(), new User(), "nombre");
    Product savedProduct =
        new Product(
            1,
            "Nuevo Producto",
            ACTIVE,
            200.0,
            20,
            false,
            new Category(1, "Ropa"),
            LocalDateTime.now(),
            LocalDateTime.now(),
            new User(), "nombre");
        Category category = new Category(1, "Ropa");
        savedProduct.setCategory(category);
        when(productRepository.save(product)).thenReturn(savedProduct);
        when(categoryRepository.findById(1)).thenReturn(Optional.of(category));

        Product result = productService.createProduct(product, 1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Nuevo Producto", result.getDescription());
        assertEquals(200.0, result.getPricePerUnit());
        assertEquals(20, result.getCurrentStock());
    }

    @Test
    void testUpdateProductStock() throws Exception {
        Integer productId = 1;
        String description = "New description";
        Integer stock = 30;
        double price = 100.0;
        Integer categoryID = 2;
        ProductState state = ProductState.ACTIVE;

        Product product = new Product();
        product.setId(productId);
        product.setCurrentStock(20);
        product.setDescription("Old description");
        product.setPricePerUnit(80.0);
        product.setCategory(new Category(1, "Old Category"));
        product.setCurrentState(ProductState.DRAFT);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Category newCategory = new Category(categoryID, "New Category");
        when(categoryRepository.findById(categoryID)).thenReturn(Optional.of(newCategory));

        productService.updateProduct(productId, description, stock, price, categoryID, state, "nombre");

        verify(productRepository, times(1)).save(product);
        assertEquals(stock, product.getCurrentStock());
        assertEquals(description, product.getDescription());
        assertEquals(price, product.getPricePerUnit());
        assertEquals(newCategory, product.getCategory());
        assertEquals(state, product.getCurrentState());
    }
    @Test
    void testDeleteProduct() throws Exception {
        Integer productId = 1;
        Product product = new Product();
        product.setId(productId);


        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        productService.deleteProduct(productId);

        verify(productRepository, times(1)).save(product);
        assertEquals(REMOVED, product.getCurrentState());
    }
}