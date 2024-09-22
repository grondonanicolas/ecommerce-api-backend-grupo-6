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

    private double price;

    private int stock;

    // @ManyToOne
    // @JoinColumn(name = "product_state_id", nullable = false)
    // private ProductState productState;

    // @ManyToOne
    // @JoinColumn(name = "category_id", nullable = false)
    // private Category category;

    // @ManyToOne
    // @JoinColumn(name = "user_id", nullable = false)
    // private User user;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
