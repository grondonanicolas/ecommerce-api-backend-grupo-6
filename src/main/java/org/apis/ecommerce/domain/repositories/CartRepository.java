package org.apis.ecommerce.domain.repositories;

import org.apis.ecommerce.domain.models.Cart;
import org.apis.ecommerce.domain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {
  Optional<Cart> findByUser(User user);
}