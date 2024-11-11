package org.apis.ecommerce.application.rest.controllers;

import jakarta.validation.Valid;
import org.apis.ecommerce.application.rest.dtos.ProductCreateDTO;
import org.apis.ecommerce.application.rest.dtos.ProductDTO;
import org.apis.ecommerce.application.rest.dtos.OutstandingDTO;
import org.apis.ecommerce.application.rest.dtos.ProductUpdateDTO;
import org.apis.ecommerce.application.rest.dtos.PhotoDTO;
import org.apis.ecommerce.domain.models.Category;
import org.apis.ecommerce.domain.enums.ProductState;
import org.apis.ecommerce.domain.models.Product;
import org.apis.ecommerce.domain.models.Role;
import org.apis.ecommerce.domain.models.Photo;
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
                        .map(product -> new ProductDTO(
                                product.getId(),
                                product.getDescription(),
                                product.getPricePerUnit(),
                                product.getCurrentStock(),
                                product.getCategory().getCategory(),
                                product.getName(),
                                product.getCurrentState().toString(),
                                product.getPhotos().stream()
                                        .map(photo -> new PhotoDTO(
                                                photo.getPriority(),
                                                photo.getUrl()
                                        ))
                                        .collect(Collectors.toList())
                        ))
                        .toList();
    }

    @GetMapping("/{id}")
    public ProductDTO getProductById(@AuthenticationPrincipal User user, @PathVariable Integer id) throws Exception {
        Product product = productService.getProductById(id, user);
        return new ProductDTO(
                product.getId(),
                product.getDescription(),
                product.getPricePerUnit(),
                product.getCurrentStock(),
                product.getCategory().getCategory(),
                product.getName(),
                product.getCurrentState().toString(),
                product.getPhotos().stream()
                        .map(photo -> new PhotoDTO(
                                photo.getPriority(),
                                photo.getUrl()
                        ))
                        .collect(Collectors.toList())
        );
    }


    @PostMapping("")
    public ProductDTO createProduct(@RequestBody @Valid ProductCreateDTO productCreateDTO, @AuthenticationPrincipal User user) throws Exception {
        Product newProduct = new Product();
        newProduct.setDescription(productCreateDTO.getDescription());
        newProduct.setPricePerUnit(productCreateDTO.getPrice());
        newProduct.setCurrentStock(productCreateDTO.getStock());
        newProduct.setCurrentState(ProductState.DRAFT);
        newProduct.setUser(user);
        newProduct.setName(productCreateDTO.getName());

        List<Photo> photos = productCreateDTO.getPhotos().stream()
                .map(photoDTO -> {
                    Photo photo = new Photo();
                    photo.setUrl(photoDTO.getUrl());
                    photo.setPriority(photoDTO.getPriority());
                    photo.setProduct(newProduct);
                    return photo;
                })
                .collect(Collectors.toList());
        newProduct.setPhotos(photos);

        Product productCreated = productService.createProduct(newProduct, productCreateDTO.getCategoryId());
        return new ProductDTO(
                productCreated.getId(),
                productCreated.getDescription(),
                productCreated.getPricePerUnit(),
                productCreated.getCurrentStock(),
                productCreated.getCategory().getCategory(),
                productCreated.getName(),
                productCreated.getCurrentState().toString(),
                photos.stream()
                        .map(photo -> new PhotoDTO(photo.getPriority(), photo.getUrl()))
                        .collect(Collectors.toList()) // Mapear a lista de PhotoDTOs
        );
    }

    @PutMapping("/{id}")
    public void updateProduct(@RequestBody @Valid ProductUpdateDTO product, @PathVariable Integer id, @AuthenticationPrincipal User user) throws Exception {
        List<Photo> photos = (product.getPhotos() != null) ?
                product.getPhotos().stream()
                        .map(photoDTO -> {
                            Photo photo = new Photo();
                            photo.setUrl(photoDTO.getUrl());
                            photo.setPriority(photoDTO.getPriority());
                            return photo;
                        })
                        .collect(Collectors.toList())
                : null;

        productService.updateProduct(
                id,
                product.getDescription(),
                product.getStock(),
                product.getPrice(),
                product.getCategoryId(),
                product.getState(),
                product.getName(),
                user,
                photos
        );
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Integer id, @AuthenticationPrincipal User user) throws Exception {
        productService.deleteProduct(id, user);
    }

    @GetMapping("/category/{categoryId}")
    public List<ProductDTO> getProductsByCategory(@PathVariable Integer categoryId) throws Exception {
        List<Product> products = productService.getProductsByCategoryId(categoryId);
        return products.stream()
                .map(product -> new ProductDTO(
                        product.getId(),
                        product.getDescription(),
                        product.getPricePerUnit(),
                        product.getCurrentStock(),
                        product.getCategory().getCategory(),
                        product.getName(),
                        product.getCurrentState().toString(),
                        product.getPhotos().stream()
                                .map(photo -> new PhotoDTO(photo.getPriority(), photo.getUrl()))
                                .collect(Collectors.toList()) // Convertir a lista de PhotoDTOs
                ))
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
                .map(product -> new ProductDTO(
                        product.getId(),
                        product.getDescription(),
                        product.getPricePerUnit(),
                        product.getCurrentStock(),
                        product.getCategory().getCategory(),
                        product.getName(),
                        product.getCurrentState().toString(),
                        product.getPhotos().stream()
                                .map(photo -> new PhotoDTO(photo.getPriority(), photo.getUrl()))
                                .collect(Collectors.toList()) // Convertir a lista de PhotoDTOs
                ))
                .collect(Collectors.toList());
    }
}
