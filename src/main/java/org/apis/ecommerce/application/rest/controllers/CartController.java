package org.apis.ecommerce.application.rest.controllers;

import org.apis.ecommerce.application.rest.dtos.CartRequestDto;
import org.apis.ecommerce.application.rest.dtos.CartResponseDto;
import org.apis.ecommerce.domain.models.User;
import org.apis.ecommerce.domain.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

// todo: mepa que me falta agregar una api para descontar solo una cantidad del producto? analizarlo
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
    public CartResponseDto addProductToCart(@RequestBody CartRequestDto cartRequestDto) {
        // todo: remover cuando se agregue spring security
        User requestingUser = new User();
        requestingUser.setId(1);
        
        cartService.addProductToCart(cartRequestDto, requestingUser);
        return new CartResponseDto("Producto agregado");  // todo: revisar en qué idioma vamos a devolver nuestras respuestas y alinearlas
    }
}
