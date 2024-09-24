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
    
    private int quantity;
    private double pricePerUnit;
    private String description;
    private String category;
}
