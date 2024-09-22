package org.apis.ecommerce.domain.models;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckoutRepository extends JpaRepository<Checkout, Integer> {
}