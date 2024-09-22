package org.apis.ecommerce.domain.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apis.ecommerce.domain.enums.ProductState;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String description;
    
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "product_state_id", columnDefinition = "INT")
    private ProductState currentState;

    @Column(name = "price_per_unit")
    private double pricePerUnit;

    @Column(name = "stock")
    private int currentStock;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void validateHasRequiredStock(int requestedQuantity) {
        if (requestedQuantity > currentStock) {
            throw new IllegalArgumentException("La cantidad solicitada es mayor al stock actual");
        }
    }

    public void validateThatItIsActive() {
        if (!currentState.equals(ProductState.ACTIVE)) {
            throw new IllegalStateException("El producto no está activo");
        }
    }
}
