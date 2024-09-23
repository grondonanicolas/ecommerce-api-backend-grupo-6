package org.apis.ecommerce.domain.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "historic")
public class Historic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ManyToMany
    @JoinTable(name="historic_product",
                joinColumns = @JoinColumn(name="historic_id"),
                inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;
}
