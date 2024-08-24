package com.limechain.ethereumfetcher.controller;

import com.limechain.ethereumfetcher.domain.Transaction;
import com.limechain.ethereumfetcher.model.*;
import com.limechain.ethereumfetcher.service.AuthService;
import com.limechain.ethereumfetcher.service.RememberMeService;
import com.limechain.ethereumfetcher.service.TransactionService;
import com.limechain.ethereumfetcher.service.mapper.TransactionMapper;
import jakarta.validation.Valid;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/lime")
public class TransactionsController {

    private final TransactionService transactionService;

    private final AuthService authService;

    private final RememberMeService rememberMeService;

    public TransactionsController(TransactionService transactionService, AuthService authService, RememberMeService rememberMeService) {
        this.transactionService = transactionService;
        this.authService = authService;
        this.rememberMeService = rememberMeService;
    }

    @GetMapping("/eth")
    public TransactionsResponseModel getTransactions(@RequestHeader(name = "AUTH_TOKEN", required = false) String token,
                                                     @RequestParam Set<String> transactionHashes) {

        ResponseData responseData = this.transactionService.getTransactions(transactionHashes);
        List<Transaction> transactions = responseData.getTransactions();

        if (StringUtils.hasText(token)) {
            rememberMeService.remember(token, transactions);
        }

        List<TransactionDto> transactionDtos = transactions.stream().map(TransactionMapper::toTransactionDto).toList();

        TransactionsResponseModel response = new TransactionsResponseModel();
        response.setTransactions(transactionDtos);

        if (!responseData.getErrors().isEmpty()) {
            TransactionsResponseModel.Error error = new TransactionsResponseModel.Error(String.format("Unable to get data for transactions %s", responseData.getErrors().keySet()));
            response.setError(error);
        }

        return response;
    }

    //TODO: add paging, limit
    @GetMapping("/all")
    public TransactionsResponseModel getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();

        List<TransactionDto> transactionDtos = transactions.stream().map(TransactionMapper::toTransactionDto).toList();

        TransactionsResponseModel response = new TransactionsResponseModel();
        response.setTransactions(transactionDtos);

        return response;
    }


    @GetMapping("/my")
    public TransactionsResponseModel getMyTransactions(@RequestHeader("AUTH_TOKEN") String token) {
        List<Transaction> transactions = transactionService.getTransactionsByToken(token);
        List<TransactionDto> transactionDtos = transactions.stream().map(TransactionMapper::toTransactionDto).toList();

        TransactionsResponseModel response = new TransactionsResponseModel();
        response.setTransactions(transactionDtos);

        return response;
    }

    @PostMapping("/authenticate")
    public AuthResponseModel authenticate(@Valid @RequestBody AuthRequestModel authRequestModel) {
        String token = authService.authenticate(authRequestModel);

        return new AuthResponseModel(token);
    }

}
