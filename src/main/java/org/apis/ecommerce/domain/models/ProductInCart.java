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
    
    @ManyToOne(cascade = CascadeType.MERGE)
    @MapsId(value = "productId")
    @JoinColumn(name = "product_id")
    private Product product;
    
    @Column(name = "quantity")
    private int currentQuantity;
    
    public boolean isSameProduct(Product comparingProduct) {
        int thisProductId = this.product.getId();
        int comparingProductId = comparingProduct.getId();
        return thisProductId == comparingProductId;
    }

    public void addRequestedQuantity(int requestedQuantity) {
        validateHasRequiredStock(requestedQuantity);
        product.validateThatItIsActive(); 
        currentQuantity += requestedQuantity;
    }
    
    public void validateHasRequiredStock(int requestedQuantity) {
        int requiredStock = currentQuantity + requestedQuantity;
        int currentStock = product.getCurrentStock();
        
        if (requiredStock > currentStock) {
            throw new IllegalArgumentException("La cantidad solicitada es mayor al stock actual");
        }
    }

    public boolean isSameProductId(int comparingProductId) {
        int thisProductId = this.product.getId();
        return thisProductId == comparingProductId;
    }

    public void checkOut() {
        product.subtractStock(currentQuantity);
    }


    public int getProductId() {
        return product.getId();
    }
}

