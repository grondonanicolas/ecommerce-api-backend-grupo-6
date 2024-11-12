package org.apis.ecommerce.application.rest.controllers;

import org.apis.ecommerce.application.rest.dtos.TransactionDTO;
import org.apis.ecommerce.application.rest.dtos.UserProfileDTO;
import org.apis.ecommerce.application.rest.services.ITransactionService;
import org.apis.ecommerce.domain.models.Transaction;
import org.apis.ecommerce.domain.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final ITransactionService transactionService;

    @Autowired
    public ProfileController(ITransactionService transactionService) {
        this.transactionService = transactionService;
    }
    
    @GetMapping
    public UserProfileDTO getUserProfile(@AuthenticationPrincipal User requestingUser) {
        return new UserProfileDTO(
                requestingUser.getFirstName(),
                requestingUser.getLastName(),
                requestingUser.getEmail()
        );
    }

    @GetMapping("/checkouts")
    public List<TransactionDTO> getUserCheckouts(@AuthenticationPrincipal User requestingUser) {

        List<Transaction> transactionList = transactionService.getAllTransactions(requestingUser);
        List <TransactionDTO> transactionDTOList = new ArrayList<>();
        for (Transaction transaction : transactionList) {
            transactionDTOList.add(new TransactionDTO(transaction));
        }

        return transactionDTOList;
    }
}
