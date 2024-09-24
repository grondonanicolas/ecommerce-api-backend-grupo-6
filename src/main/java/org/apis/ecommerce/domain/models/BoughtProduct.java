package org.apis.ecommerce.domain.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_bought")
public class BoughtProduct {
    @EmbeddedId
    private BoughtProductId id;
    
    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;
    
    @ManyToOne
    @MapsId("transactionId")
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    @Column(name = "quantity")
    private int quantity;
    @Column(name = "price_per_unit")
    private double pricePerUnit;
    @Column(name = "description")
    private String description;
    @Column(name = "category")
    private String category;
}
