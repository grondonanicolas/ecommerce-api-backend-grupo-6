package org.apis.ecommerce.application.rest.services;

import org.apis.ecommerce.domain.models.ProductInCart;
import org.apis.ecommerce.domain.models.Transaction;
import org.apis.ecommerce.domain.models.User;

import java.util.List;

public interface ITransactionService {
    public void create(User requestingUser, List<ProductInCart> purchasedProducts);
    public List<Transaction> getAllTransactions(User requestingUser);
}
