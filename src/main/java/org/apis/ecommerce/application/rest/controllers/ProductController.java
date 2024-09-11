package org.apis.ecommerce.application.rest.controllers;

import java.util.List;

import org.apis.ecommerce.application.rest.dtos.ProductoDTO;
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

    @GetMapping("/{productId}")
    public ProductoDTO getProductById(@PathVariable Integer productId) throws Exception {
        return productService.getProductById(productId);
    }    

    @GetMapping("/category/{categoryId}")
    public List<ProductoDTO> getProductsByCategory(@PathVariable Integer categoryId ) throws Exception{
        return productService.getProductsByCategoryId(categoryId);
    }
}
