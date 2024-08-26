package com.limechain.ethereumfetcher.service;

import com.limechain.ethereumfetcher.client.EthereumClient;
import com.limechain.ethereumfetcher.client.protocol.FetchTransactionsRequest;
import com.limechain.ethereumfetcher.client.protocol.FetchTransactionsResponse;
import com.limechain.ethereumfetcher.client.protocol.Methods;
import com.limechain.ethereumfetcher.config.Constants;
import com.limechain.ethereumfetcher.domain.Transaction;
import com.limechain.ethereumfetcher.model.ResponseData;
import com.limechain.ethereumfetcher.service.mapper.TransactionMapper;
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
