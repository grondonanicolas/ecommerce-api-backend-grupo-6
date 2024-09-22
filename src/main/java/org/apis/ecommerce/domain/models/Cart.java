package org.apis.ecommerce.domain.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Cart {
    // todo: tener en cuenta que hay que crear el carrito cuando se crea el usuario
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<ProductInCart> selectedProducts;

    public void addRequestedProductQuantity(Product requestedProduct, int requestedQuantity) {
        requestedProduct.validateHasRequiredStock(requestedQuantity);
        requestedProduct.validateThatItIsActive();
        Optional<ProductInCart> productInCart = getProductInCart(requestedProduct);
        productInCart.ifPresentOrElse(
                existingProduct -> existingProduct.addRequestedQuantity(requestedQuantity), 
                () -> addNewProductWithRequestedQuantity(requestedProduct, requestedQuantity));
    }

    private Optional<ProductInCart> getProductInCart(Product requestedProduct) {
        return selectedProducts.stream()
                .filter(productInCart -> productInCart.isSameProduct(requestedProduct))
                .findFirst();
    }

    private void addNewProductWithRequestedQuantity(Product product, int requestedQuantity) {
        int productId = product.getId();

        ProductInCart productInCart = ProductInCart.builder()
                .id(new ProductInCartId(this.id, productId))
                .cart(this)
                .product(product)
                .quantity(requestedQuantity)
                .build();

        selectedProducts.add(productInCart);
    }

}
