package org.apis.ecommerce.domain.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apis.ecommerce.application.rest.dtos.AddProductToCartDto;
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
    private final TransactionRepository transactionRepository;

    public CartService(CartRepository cartRepository, IProductRepository productRepository,
                       TransactionRepository transactionRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.transactionRepository = transactionRepository;
    }

    public void addProductToCart(AddProductToCartDto addProductToCartDto, User requestingUser) {
        int productId = addProductToCartDto.getProductId();
        int requestedQuantity = addProductToCartDto.getQuantity();
        
        Product requestedProduct = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("El producto solicitado no existe"));
        Cart userCart = getUserCart(requestingUser);  // todo: ver si refactorizo para obtener los datos desde la entidad User o no
        userCart.addRequestedProductQuantity(requestedProduct, requestedQuantity);
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

    // todo: limpiar y mejorar este método, además algo me dice que tiene algún bug escondido y seguramente se podrían asignar mejor las responsabilidades
    @Transactional
    public void checkOutUserCart(User requestingUser) {
        Cart userCart = getUserCart(requestingUser);
        userCart.validateIsNotEmpty();
        
        logger.info("Carrito pagado");

        List<ProductInCart> purchasedProducts = userCart.getSelectedProducts();
        userCart.checkOutProducts();
        
        Transaction transaction = Transaction.builder()
                .user(requestingUser)
                .boughtProducts(new ArrayList<>())
                .build();
        
        transaction = transactionRepository.save(transaction);
        
        List<BoughtProduct> boughtProducts = new ArrayList<>();
        
        for (ProductInCart purchasedProduct : purchasedProducts) {
            int transactionId = transaction.getId();
            int productId = purchasedProduct.getProductId();
            Product product = purchasedProduct.getProduct();
            int currentQuantity = purchasedProduct.getCurrentQuantity();
            double pricePerUnit = purchasedProduct.getProduct().getPricePerUnit();

            BoughtProductId boughtProductId = new BoughtProductId(productId, transactionId);

            BoughtProduct boughtProduct = BoughtProduct.builder()
                    .id(boughtProductId)
                    .product(product)
                    .transaction(transaction)
                    .quantity(currentQuantity)
                    .pricePerUnit(pricePerUnit)
                    .build();
            
            boughtProducts.add(boughtProduct);
        }
        
        transaction.addBoughtProducts(boughtProducts);
        transactionRepository.save(transaction);
    }

    public void modifyProductQuantity(ProductQuantityRequest productQuantityRequest) {
        Cart userCart = getUserCart(productQuantityRequest.getUser());
        userCart.modifyProductQuantity(productQuantityRequest);
        cartRepository.save(userCart);
    }
}
