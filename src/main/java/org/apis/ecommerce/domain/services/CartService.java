package org.apis.ecommerce.domain.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apis.ecommerce.application.rest.dtos.AddProductToCartDto;
import org.apis.ecommerce.application.rest.services.ICartService;
import org.apis.ecommerce.domain.models.*;
import org.apis.ecommerce.domain.repositories.CartRepository;
import org.apis.ecommerce.domain.repositories.IProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService implements ICartService {
    private static final Logger logger = LoggerFactory.getLogger(CartService.class);
    
    private final CartRepository cartRepository;
    private final IProductRepository productRepository;
    private final TransactionService transactionService;

    public CartService(CartRepository cartRepository, IProductRepository productRepository, TransactionService transactionService) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.transactionService = transactionService;
    }

    public void addProductToCart(AddProductToCartDto addProductToCartDto, User requestingUser) {
        int productId = addProductToCartDto.getProductId();
        int requestedQuantity = addProductToCartDto.getQuantity();
        
        Product requestedProduct = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("El producto solicitado no existe"));
        Cart userCart = getUserCart(requestingUser);
        userCart.addNewProductWithRequestedQuantity(requestedProduct, requestedQuantity);
        cartRepository.save(userCart);
    }

    public Cart getUserCart(User requestingUser) {
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

    @Transactional
    public void checkOutUserCart(User requestingUser) {
        Cart userCart = getUserCart(requestingUser);
        userCart.validateIsNotEmpty();
        
        logger.info("Carrito pagado");

        List<ProductInCart> purchasedProducts = userCart.getSelectedProducts();
        userCart.checkOutProducts();

        transactionService.create(requestingUser, purchasedProducts);
    }

    public void modifyProductQuantity(ProductQuantityParameters productQuantityParameters) {
        Cart userCart = getUserCart(productQuantityParameters.getUser());
        userCart.modifyProductQuantity(productQuantityParameters);
        cartRepository.save(userCart);
    }
}
