package com.limechain.ethereumfetcher.client.protocol;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FetchTransactionsRequest {

    private int id;

    private String jsonrpc;

    private String method;

    private List<String> params;
}
