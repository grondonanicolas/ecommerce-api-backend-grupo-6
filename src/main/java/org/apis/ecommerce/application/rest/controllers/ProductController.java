package org.apis.ecommerce.application.rest.controllers;

import org.apis.ecommerce.application.rest.dtos.ProductCreateDTO;
import org.apis.ecommerce.application.rest.dtos.ProductDTO;
import org.apis.ecommerce.application.rest.dtos.OutstandingDTO;
import org.apis.ecommerce.application.rest.dtos.ProductUpdateDTO;
import org.apis.ecommerce.domain.enums.ProductState;
import org.apis.ecommerce.domain.models.Product;
import org.apis.ecommerce.application.rest.services.IProductService;
import org.apis.ecommerce.domain.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping
    public String listProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Boolean recentlyViewed) {

        return "Listando productos - Categoría: " + category + ", Vistos recientemente: " + recentlyViewed;
    }

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable Integer id) throws Exception {
        Product product = productService.getProductById(id);
        return new ProductDTO(product.getId(), product.getDescription(), product.getPricePerUnit(), product.getCurrentStock(), product.getCategory().getCategory());
    }

    @PostMapping("")
    public ProductDTO createProduct(@RequestBody ProductCreateDTO product, @AuthenticationPrincipal User user) throws Exception {
        Product newProduct = new Product();
        newProduct.setDescription(product.getDescription());
        newProduct.setPricePerUnit(product.getPrice());
        newProduct.setCurrentStock(product.getStock());
        newProduct.setCurrentState(ProductState.DRAFT);
        newProduct.setUser(user);
        newProduct.setName(product.getName());
        Product productCreated = productService.createProduct(newProduct, product.getCategoryId());
        return new ProductDTO(productCreated.getId(), productCreated.getDescription(), productCreated.getPricePerUnit(), productCreated.getCurrentStock(), productCreated.getCategory().getCategory());
    }

    @PutMapping("/{id}")
    public void updateProductStock(@RequestBody ProductUpdateDTO product, @PathVariable Integer productID) throws Exception {
        productService.updateProduct(productID, product.getDescription(), product.getStock(), product.getPrice(), product.getCategoryId(), product.getState(), product.getName());
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Integer id) throws Exception {
        productService.deleteProduct(id);
    }
    @GetMapping("/category/{categoryId}")
    public List<ProductDTO> getProductsByCategory(@PathVariable Integer categoryId ) throws Exception{
        List<Product> products = productService.getProductsByCategoryId(categoryId);
        return products.stream()
                .map(product -> 
                        new ProductDTO(
                            product.getId(), 
                            product.getDescription(), 
                            product.getPricePerUnit(), 
                            product.getCurrentStock(),
                                product.getCategory().getCategory()))
                .toList();
    }

    // Outstanding
    @PostMapping("/outstanding")
    public void addProductOutstanding(@RequestBody OutstandingDTO outstandingDTO) throws Exception {
        productService.addProductOutstanding(outstandingDTO.getProductId());
    }

    @GetMapping("/outstanding")
    public List<ProductDTO> getOutstandingProducts() throws Exception {
        List<Product> products = productService.findAllOutstanding();
        return products.stream()
            .map(product -> new ProductDTO(product.getId(), product.getDescription(), product.getPricePerUnit(), product.getCurrentStock(), product.getCategory().getCategory()))
            .collect(Collectors.toList());
    }
}
