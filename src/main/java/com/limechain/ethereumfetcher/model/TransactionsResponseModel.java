package com.limechain.ethereumfetcher.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class TransactionsResponseModel {

    private List<TransactionDto> transactions;

    private Error error;

    @Getter
    @AllArgsConstructor
    public static class Error {
        private String message;
    }

}
