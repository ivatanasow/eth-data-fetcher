package com.home.ethfetcher.model;

import com.home.ethfetcher.domain.Transaction;
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
