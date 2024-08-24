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

@Slf4j
@Service
public class TransactionFetcher {

    private final EthereumClient ethereumClient;

    public TransactionFetcher(EthereumClient ethereumClient) {
        this.ethereumClient = ethereumClient;
    }

    //TODO: make calls async
    public ResponseData fetchTransactions(List<String> transactionHashes) {
        List<Transaction> transactions = new LinkedList<>();
        Map<String, Object> errors = new HashMap<>();

        for (String hash : transactionHashes) {
            try {
                FetchTransactionsRequest requestBody = new FetchTransactionsRequest(1, Constants.JSON_PRC_VERSION, Methods.GET_TX_BY_HASH, List.of(hash));
                FetchTransactionsResponse response = ethereumClient.fetchTransactions(requestBody);
                Transaction transaction = TransactionMapper.toTransactionV2(hash, response);
                if (transaction != null) {
                    transactions.add(transaction);
                }
            } catch (FeignException e) {
                String errorMsg = String.format("Unable to get transaction data for transaction [%s]", hash);
                log.error(errorMsg, hash, e);
                errors.put(hash, errorMsg);
            }
        }

        return new ResponseData(transactions, errors);
    }
}
