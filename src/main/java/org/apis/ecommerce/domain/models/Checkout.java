package org.apis.ecommerce.domain.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class Checkout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_id")
    private List<CheckoutProduct> purchasedProducts;

    public void addCheckoutProducts(List<CheckoutProduct> checkoutProducts) {
        purchasedProducts.addAll(checkoutProducts);
    }
}
