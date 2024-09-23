package org.apis.ecommerce.domain.models;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    @Query("SELECT t FROM Transaction t WHERE t.user.id = :userId")
    List<Transaction> getTransactionsForUser(@Param("userId") Integer userId);
}