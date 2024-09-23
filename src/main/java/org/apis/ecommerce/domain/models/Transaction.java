package org.apis.ecommerce.domain.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_id")
    private List<BoughtProduct> boughtProducts;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    public void addBoughtProducts(List<BoughtProduct> boughtProducts) {
        this.boughtProducts.addAll(boughtProducts);
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }
}