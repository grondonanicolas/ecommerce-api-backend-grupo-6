package org.apis.ecommerce.domain.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "historic")
public class Historic {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // @ManyToMany
    // @JoinTable(name = "historic_user",
    //             joinColumns = {@JoinColumn(name="historic_id")},
    //             inverseJoinColumns = {@JoinColumn(name = "user_id")})
    // private User userId;

    @ManyToMany
    @JoinTable(name = "historic_product",
                joinColumns = @JoinColumn(name="historic_id"),
                inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Product productId;
}
