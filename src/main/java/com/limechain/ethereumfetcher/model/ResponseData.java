package com.limechain.ethereumfetcher.model;

import com.limechain.ethereumfetcher.domain.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class ResponseData {

    private List<Transaction> transactions;

    private Map<String, Object> errors;
}
