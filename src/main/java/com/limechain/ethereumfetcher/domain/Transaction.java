package com.limechain.ethereumfetcher.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    private String transactionHash;

    private int transactionStatus;

    private String blockHash;

    private int blockNumber;

    private String fromAddress;

    private String toAddress;

    private String contractAddress;

    private int logsCount;

    private String input;

    private String value;
}


