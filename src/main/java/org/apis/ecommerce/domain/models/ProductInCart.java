package org.apis.ecommerce.domain.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_cart")
public class ProductInCart {
    @EmbeddedId
    private ProductInCartId id;
    
    @ManyToOne
    @MapsId(value = "cartId")
    @JoinColumn(name = "cart_id")
    private Cart cart;
    
    @ManyToOne
    @MapsId(value = "productId")
    @JoinColumn(name = "product_id")
    private Product product;
    
    private int quantity;
    
    public boolean isSameProduct(Product comparingProduct) {
        int thisProductId = this.product.getId();
        int comparingProductId = comparingProduct.getId();
        return thisProductId == comparingProductId;
    }

    public void addRequestedQuantity(int requestedQuantity) {
        product.validateHasRequiredStock(requestedQuantity);
        product.validateThatItIsActive();
        quantity += requestedQuantity;
    }
}

