package com.home.ethfetcher.client;

import com.home.ethfetcher.client.protocol.FetchTransactionsRequest;
import com.home.ethfetcher.client.protocol.FetchTransactionsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "ethereum-client", url = "${application.ethereum-node-url}")
public interface EthereumClient {

    @PostMapping
    FetchTransactionsResponse fetchTransactions(FetchTransactionsRequest requestBody);

}
