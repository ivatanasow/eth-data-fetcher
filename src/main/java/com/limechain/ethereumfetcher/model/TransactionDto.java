package com.limechain.ethereumfetcher.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TransactionDto {

    private String transactionHash;

    private int transactionStatus;

    private String blockHash;

    private int blockNumber;

    private String from;

    private String to;

    private String contractAddress;

    private int logsCount;

    private String input;

    private String value;
}
