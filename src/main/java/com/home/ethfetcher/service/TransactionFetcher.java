package com.home.ethfetcher.service;

import com.home.ethfetcher.client.EthereumClient;
import com.home.ethfetcher.client.protocol.FetchTransactionsRequest;
import com.home.ethfetcher.client.protocol.FetchTransactionsResponse;
import com.home.ethfetcher.client.protocol.Methods;
import com.home.ethfetcher.config.Constants;
import com.home.ethfetcher.domain.Transaction;
import com.home.ethfetcher.model.ResponseData;
import com.home.ethfetcher.service.mapper.TransactionMapper;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TransactionFetcher {

    private final EthereumClient ethereumClient;

    private final ExecutorService executorService;

    public TransactionFetcher(EthereumClient ethereumClient, ExecutorService executorService) {
        this.ethereumClient = ethereumClient;
        this.executorService = executorService;
    }

    public ResponseData fetchTransactions(List<String> transactionHashes) {
        Map<String, Object> errors = new ConcurrentHashMap<>();

        List<CompletableFuture<Transaction>> futures = transactionHashes
                .stream()
                .map(hash -> CompletableFuture.supplyAsync(() -> {
                    try {
                        FetchTransactionsRequest requestBody = new FetchTransactionsRequest(1, Constants.JSON_PRC_VERSION, Methods.GET_TX_BY_HASH, List.of(hash));
                        FetchTransactionsResponse response = ethereumClient.fetchTransactions(requestBody);
                        return TransactionMapper.toTransaction(hash, response);
                    } catch (FeignException e) {
                        String errorMsg = String.format("Unable to get transaction data for transaction [%s]", hash);
                        log.error(errorMsg, hash, e);
                        errors.put(hash, errorMsg);
                        return null;
                    }
                }, executorService)).toList();

        List<Transaction> transactions = futures.stream()
                .map(CompletableFuture::join)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return new ResponseData(transactions, errors);
    }
}
