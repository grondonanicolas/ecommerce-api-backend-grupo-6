package org.apis.ecommerce.application.rest.controllers;

import org.apis.ecommerce.application.rest.dtos.AddProductToCartDto;
import org.apis.ecommerce.application.rest.dtos.CartResponseDto;
import org.apis.ecommerce.application.rest.dtos.ProductQuantityInCartDto;
import org.apis.ecommerce.domain.models.Cart;
import org.apis.ecommerce.application.rest.dtos.CartDetailDto;
import org.apis.ecommerce.domain.models.User;
import org.apis.ecommerce.domain.services.CartService;
import org.apis.ecommerce.domain.services.ProductQuantityRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/cart", produces = MediaType.APPLICATION_JSON_VALUE)
public class CartController {
    private final CartService cartService;
    
    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    
    @PostMapping(path = "/products", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public CartResponseDto addProductToCart(@RequestBody AddProductToCartDto addProductToCartDto, 
                                            @AuthenticationPrincipal User requestingUser) {
        cartService.addProductToCart(addProductToCartDto, requestingUser);
        return new CartResponseDto("Producto agregado");
    }
    
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public CartResponseDto clearUserCart(@AuthenticationPrincipal User requestingUser) {
        cartService.clearUserCart(requestingUser);
        return new CartResponseDto("Carrito vaciado");
    }
    
    @DeleteMapping(path = "/products/{productIdToRemove}")
    @ResponseStatus(HttpStatus.OK)
    public CartResponseDto removeProductFromUserCart(@PathVariable int productIdToRemove, 
                                                     @AuthenticationPrincipal User requestingUser) {
        cartService.removeProductFromUserCart(productIdToRemove, requestingUser);
        return new CartResponseDto("Producto removido");
    }
    
    @PostMapping(path = "/checkout")
    @ResponseStatus(HttpStatus.OK)
    public CartResponseDto checkOutUserCart(@AuthenticationPrincipal User requestingUser) {
        cartService.checkOutUserCart(requestingUser);
        return new CartResponseDto("Carrito comprado");
    }
    
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CartDetailDto getCartDetail(@AuthenticationPrincipal User requestingUser) {
        Cart userCart = cartService.getUserCart(requestingUser);
        return userCart.toDto();   
    }
    
    @PutMapping(path = "/products/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public CartResponseDto modifyProductQuantityInCart(@PathVariable(name = "productId") int productToModifyId, 
                                                       @RequestBody ProductQuantityInCartDto productQuantityInCartDto,
                                                       @AuthenticationPrincipal User requestingUser) {
        int requestedQuantity = productQuantityInCartDto.getQuantity(); 
        
        ProductQuantityRequest productQuantityRequest = ProductQuantityRequest.builder()
               .productToModifyId(productToModifyId)
               .quantity(requestedQuantity)
               .user(requestingUser)
               .build();
        
        cartService.modifyProductQuantity(productQuantityRequest);
        
        return new CartResponseDto("Se modificó la cantidad indicada del producto en el carrito");
    }
}
