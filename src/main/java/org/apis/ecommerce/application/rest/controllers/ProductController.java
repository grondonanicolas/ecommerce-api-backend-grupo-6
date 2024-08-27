package org.apis.ecommerce.application.rest.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    @GetMapping
    public String listProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Boolean recentlyViewed) {

        return "Listando productos - Categoría: " + category + ", Vistos recientemente: " + recentlyViewed;
    }


    @GetMapping("/{productId}")
    public String getProductDetail(@PathVariable Long id) {

        return "Detalles del producto con ID: " + id;
    }
    
}
