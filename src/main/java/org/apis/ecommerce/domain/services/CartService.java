package org.apis.ecommerce.domain.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apis.ecommerce.application.rest.dtos.CartRequestDto;
import org.apis.ecommerce.domain.models.*;
import org.apis.ecommerce.domain.repositories.CartRepository;
import org.apis.ecommerce.domain.repositories.IProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    private static final Logger logger = LoggerFactory.getLogger(CartService.class);
    
    private final CartRepository cartRepository;
    private final IProductRepository productRepository;
    private final CheckoutRepository checkoutRepository;

    public CartService(CartRepository cartRepository, IProductRepository productRepository,
                       CheckoutRepository checkoutRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.checkoutRepository = checkoutRepository;
    }

    public void addProductToCart(CartRequestDto cartRequestDto, User requestingUser) {
        int productId = cartRequestDto.getProductId();
        int requestedQuantity = cartRequestDto.getQuantity();
        
        Product requestedProduct = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("El producto solicitado no existe"));
        Cart userCart = getUserCart(requestingUser);  // todo: ver si refactorizo para obtener los datos desde la entidad User o no
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

    // todo: limpiar y mejorar este método, además algo me dice que tiene algún bug escondido y seguramente se podrían asignar mejor las responsabilidades
    @Transactional
    public void checkOutUserCart(User requestingUser) {
        Cart userCart = getUserCart(requestingUser);
        userCart.validateIsNotEmpty();
        
        logger.info("Carrito pagado");

        List<ProductInCart> purchasedProducts = userCart.getSelectedProducts();
        userCart.checkOutProducts();
        
        Checkout checkout = Checkout.builder()
                .user(requestingUser)
                .purchasedProducts(new ArrayList<>())
                .build();
        
        checkout = checkoutRepository.save(checkout);
        
        List<CheckoutProduct> checkoutProducts = new ArrayList<>();
        
        for (ProductInCart purchasedProduct : purchasedProducts) {
            int checkoutId = checkout.getId();
            int productId = purchasedProduct.getProductId();
            Product product = purchasedProduct.getProduct();
            int currentQuantity = purchasedProduct.getCurrentQuantity();
            double pricePerUnit = purchasedProduct.getProduct().getPricePerUnit();

            CheckoutProductId checkoutProductId = new CheckoutProductId(productId, checkoutId);

            CheckoutProduct checkoutProduct = CheckoutProduct.builder()
                    .id(checkoutProductId)
                    .product(product)
                    .checkout(checkout)
                    .quantity(currentQuantity)
                    .pricePerUnit(pricePerUnit)
                    .build();
            
            checkoutProducts.add(checkoutProduct);
        }
        
        checkout.addCheckoutProducts(checkoutProducts);
        checkoutRepository.save(checkout);
    }
}
