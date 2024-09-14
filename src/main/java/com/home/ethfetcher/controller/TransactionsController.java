package com.home.ethfetcher.controller;

import com.home.ethfetcher.domain.Transaction;
import com.home.ethfetcher.model.*;
import com.home.ethfetcher.service.AuthService;
import com.home.ethfetcher.service.RememberMeService;
import com.home.ethfetcher.service.TransactionService;
import com.home.ethfetcher.service.mapper.TransactionMapper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/app")
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

    @GetMapping("/all")
    public Page<TransactionDto> getAllTransactions(@RequestParam(value = "page", defaultValue = "0") int page,
                                                   @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<Transaction> transactions = transactionService.getAllTransactions(page, size);

        List<TransactionDto> transactionDtos = transactions.stream().map(TransactionMapper::toTransactionDto).toList();

        return new PageImpl<>(transactionDtos, PageRequest.of(page, size), transactions.getTotalElements());
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
