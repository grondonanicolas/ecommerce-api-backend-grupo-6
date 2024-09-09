package org.apis.ecommerce.application.rest.controllers;

import org.apis.ecommerce.application.rest.dtos.ProductoDTO;
import org.apis.ecommerce.domain.models.Product;
import org.apis.ecommerce.domain.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String listProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Boolean recentlyViewed) {

        return "Listando productos - Categoría: " + category + ", Vistos recientemente: " + recentlyViewed;
    }

    @GetMapping("/{id}")
    public ProductoDTO getProductById(@PathVariable Integer id) throws Exception {
        return productService.getProductById(id);
    }
    
}
