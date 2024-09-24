package org.apis.ecommerce.domain.services;

import org.apis.ecommerce.application.rest.services.ITransactionService;
import org.apis.ecommerce.domain.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService implements ITransactionService {
    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void create(User requestingUser, List<ProductInCart> purchasedProducts) {
        Transaction transaction = Transaction.builder()
                .user(requestingUser)
                .boughtProducts(new ArrayList<>())
                .build();

        transaction = transactionRepository.save(transaction);

        List<BoughtProduct> boughtProducts = new ArrayList<>();

        for (ProductInCart purchasedProduct : purchasedProducts) {
            int productId = purchasedProduct.getProductId();
            int transactionId = transaction.getId();
            BoughtProductId boughtProductId = new BoughtProductId(productId, transactionId);
            Product product = purchasedProduct.getProduct();

            BoughtProduct boughtProduct = BoughtProduct.builder()
                    .id(boughtProductId)
                    .product(product)
                    .transaction(transaction)
                    .quantity(purchasedProduct.getCurrentQuantity())
                    .pricePerUnit(purchasedProduct.getProduct().getPricePerUnit())
                    .description(product.getDescription())
                    .category(product.getCategoryName())
                    .build();

            boughtProducts.add(boughtProduct);
        }

        transaction.addBoughtProducts(boughtProducts);
        transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactions(User requestingUser) {
        return transactionRepository.getTransactionsForUser(requestingUser.getId());
    }
}
