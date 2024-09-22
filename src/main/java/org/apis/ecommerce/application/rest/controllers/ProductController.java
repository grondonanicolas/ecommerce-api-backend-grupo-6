package org.apis.ecommerce.application.rest.controllers;

import org.apis.ecommerce.application.rest.dtos.ProductDTO;
import org.apis.ecommerce.application.rest.dtos.OutstandingDTO;
import org.apis.ecommerce.domain.models.Outstanding;
import org.apis.ecommerce.domain.models.Product;
import org.apis.ecommerce.application.rest.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
        return new ProductDTO(product.getId(), product.getDescription(), product.getPricePerUnit(), product.getCurrentStock());
    }

    @GetMapping("/category/{categoryId}")
    public List<ProductDTO> getProductsByCategory(@PathVariable Integer categoryId ) throws Exception{
        List<Product> products = productService.getProductsByCategoryId(categoryId);
        return products.stream()
                .map(product -> 
                        new ProductDTO(
                            product.getId(), 
                            product.getDescription(), 
                            product.getPrice(), 
                            product.getStock()))
                .toList();
    }

    // Outstanding
    @PostMapping("/outstanding")
    public void addProductOutstanding(@RequestBody OutstandingDTO outstandingDTO) throws Exception {
        productService.addProductOutstanding(outstandingDTO.getProductId());
    }

    @GetMapping("/outstanding")
    public List<ProductDTO> getOutstandingProducts() throws Exception {
        List<Product> products = productService.getOutstandingProducts();
        return products.stream()
            .map(product -> new ProductDTO(product.getId(), product.getDescription(), product.getPrice(), product.getStock()))
            .collect(Collectors.toList());
    }
}
