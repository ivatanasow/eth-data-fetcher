package com.limechain.ethereumfetcher.service.mapper;

import com.limechain.ethereumfetcher.client.protocol.FetchTransactionsResponse;
import com.limechain.ethereumfetcher.domain.Transaction;
import com.limechain.ethereumfetcher.model.TransactionDto;

public class TransactionMapper {

    public static Transaction toTransactionV2(String transactionHash, FetchTransactionsResponse response) {
        if (response.hasError()) {
            return Transaction
                    .builder()
                    .transactionHash(transactionHash)
                    .transactionStatus(0)
                    .build();
        }

        if (response.getResult() == null) {
            return null;
        }

        FetchTransactionsResponse.Result result = response.getResult();
        return Transaction
                .builder()
                .transactionHash(result.getHash())
                .transactionStatus(1)
                .blockHash(result.getBlockHash())
                .blockNumber(Integer.decode(result.getBlockNumber()))
                .fromAddress(result.getFrom())
                .toAddress(result.getTo())
                //TODO: how to get contract address
                .contractAddress(null)
                //TODO: it seem like new api call should be made to fetch logs count
                .logsCount(0)
                .input(result.getInput())
                .value(result.getValue())
                .build();
    }

    public static TransactionDto toTransactionDto(Transaction transaction) {
        return TransactionDto
                .builder()
                .transactionHash(transaction.getTransactionHash())
                .transactionStatus(transaction.getTransactionStatus())
                .blockHash(transaction.getBlockHash())
                .blockNumber(transaction.getBlockNumber())
                .from(transaction.getFromAddress())
                .to(transaction.getToAddress())
                .contractAddress(transaction.getContractAddress())
                .logsCount(transaction.getLogsCount())
                .input(transaction.getInput())
                .value(transaction.getValue())
                .build();
    }
}
