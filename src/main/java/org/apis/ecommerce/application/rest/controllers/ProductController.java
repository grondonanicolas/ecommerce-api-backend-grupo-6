package org.apis.ecommerce.application.rest.controllers;

import jakarta.validation.Valid;
import org.apis.ecommerce.application.rest.dtos.ProductCreateDTO;
import org.apis.ecommerce.application.rest.dtos.ProductDTO;
import org.apis.ecommerce.application.rest.dtos.OutstandingDTO;
import org.apis.ecommerce.application.rest.dtos.ProductUpdateDTO;
import org.apis.ecommerce.domain.enums.ProductState;
import org.apis.ecommerce.domain.models.Product;
import org.apis.ecommerce.domain.models.Role;
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
    public List<ProductDTO> listProducts(
            @AuthenticationPrincipal User user) throws Exception {
                List<Product> products;
                if(user.getRole().name().equals(Role.ADMIN.name())){
                    products = productService.getAllByUser(user);;
                } else {
                    products = productService.getAllProducts();
                }
                return products.stream()
                        .map(product ->
                                new ProductDTO(
                                        product.getId(),
                                        product.getDescription(),
                                        product.getPricePerUnit(),
                                        product.getCurrentStock(),
                                        product.getCategory().getCategory(),
                                        product.getImage(),
                                        product.getName(),
                                        product.getCurrentState().toString()))
                        .toList();
    }

    @GetMapping("/{id}")
    public ProductDTO getProductById( @AuthenticationPrincipal User user, @PathVariable Integer id) throws Exception {
        Product product = productService.getProductById(id, user);
        return new ProductDTO(product.getId(), product.getDescription(), product.getPricePerUnit(), product.getCurrentStock(), product.getCategory().getCategory(), product.getImage(), product.getName(),product.getCurrentState().toString());
    }

    @PostMapping("")
    public ProductDTO createProduct(@RequestBody @Valid ProductCreateDTO product, @AuthenticationPrincipal User user) throws Exception {
        Product newProduct = new Product();
        newProduct.setDescription(product.getDescription());
        newProduct.setPricePerUnit(product.getPrice());
        newProduct.setCurrentStock(product.getStock());
        newProduct.setCurrentState(ProductState.DRAFT);
        newProduct.setUser(user);
        newProduct.setName(product.getName());
        newProduct.setImage(product.getImage());
        Product productCreated = productService.createProduct(newProduct, product.getCategoryId());
        return new ProductDTO(productCreated.getId(), productCreated.getDescription(), productCreated.getPricePerUnit(), productCreated.getCurrentStock(), productCreated.getCategory().getCategory(), product.getImage(), product.getName(), productCreated.getCurrentState().toString());
    }

    @PutMapping("/{id}")
    public void updateProduct(@RequestBody @Valid ProductUpdateDTO product, @PathVariable Integer id, @AuthenticationPrincipal User user) throws Exception {
        productService.updateProduct(id, product.getDescription(), product.getStock(), product.getPrice(), product.getCategoryId(), product.getState(), product.getName(), user, product.getImage());
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Integer id, @AuthenticationPrincipal User user) throws Exception {
        productService.deleteProduct(id, user);
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
                                product.getCategory().getCategory(),
                                product.getImage(),
                                product.getName(),
                                product.getCurrentState().toString()))
                .toList();
    }

    // Outstanding
    @PostMapping("/outstanding")
    public void addProductOutstanding(@Valid @RequestBody OutstandingDTO outstandingDTO) throws Exception {
        productService.addProductOutstanding(outstandingDTO.getProductId());
    }

    @GetMapping("/outstanding")
    public List<ProductDTO> getOutstandingProducts() throws Exception {
        List<Product> products = productService.findAllOutstanding();
        return products.stream()
            .map(product -> new ProductDTO(product.getId(), product.getDescription(), product.getPricePerUnit(), product.getCurrentStock(), product.getCategory().getCategory(), product.getImage(), product.getName(), product.getCurrentState().toString()))
            .collect(Collectors.toList());
    }
}
