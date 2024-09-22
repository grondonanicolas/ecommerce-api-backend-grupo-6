package org.apis.ecommerce.domain.services;

import jakarta.persistence.EntityNotFoundException;
import org.apis.ecommerce.application.rest.dtos.CartRequestDto;
import org.apis.ecommerce.domain.models.Cart;
import org.apis.ecommerce.domain.models.Product;
import org.apis.ecommerce.domain.models.User;
import org.apis.ecommerce.domain.repositories.CartRepository;
import org.apis.ecommerce.domain.repositories.IProductRepository;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final IProductRepository productRepository;

    public CartService(CartRepository cartRepository, IProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public void addProductToCart(CartRequestDto cartRequestDto, User requestingUser) {
        int productId = cartRequestDto.getProductId();
        int requestedQuantity = cartRequestDto.getQuantity();
        
        Product requestedProduct = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("El producto solicitado no existe"));
        Cart userCart = getUserCart(requestingUser);
        userCart.addRequestedProductQuantity(requestedProduct, requestedQuantity);
        cartRepository.save(userCart);
    }

    private Cart getUserCart(User requestingUser) {
        return cartRepository.findByUser(requestingUser).orElseThrow(() -> new EntityNotFoundException("El carrito del usuario solicitante no existe"));
    }

    public void clearUserCart(User requestingUser) {
        Cart userCart = getUserCart(requestingUser);
        userCart.clear();
        cartRepository.save(userCart);
    }

    public void removeProductFromUserCart(int productIdToRemove, User requestingUser) {
        Cart userCart = getUserCart(requestingUser);
        userCart.removeProduct(productIdToRemove);
        cartRepository.save(userCart);
    }
}
