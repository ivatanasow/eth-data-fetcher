package com.limechain.ethereumfetcher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.limechain.ethereumfetcher.client.protocol.FetchTransactionsResponse;
import org.junit.jupiter.api.Test;

public class ASDF {

    String json = "{\n" +
            "    \"jsonrpc\": \"2.0\",\n" +
            "    \"id\": 0,\n" +
            "    \"result\": {\n" +
            "        \"blockHash\": \"0x1d59ff54b1eb26b013ce3cb5fc9dab3705b415a67127a003c3e61eb445bb8df2\",\n" +
            "        \"blockNumber\": \"0x5daf3b\",\n" +
            "        \"hash\": \"0x88df016429689c079f3b2f6ad39fa052532c56795b733da78a91ebe6a713944b\",\n" +
            "        \"input\": \"0x68656c6c6f21\",\n" +
            "        \"r\": \"0x1b5e176d927f8e9ab405058b2d2457392da3e20f328b16ddabcebc33eaac5fea\",\n" +
            "        \"s\": \"0x4ba69724e8f69de52f0125ad8b3c5c2cef33019bac3249e2c0a2192766d1721c\",\n" +
            "        \"v\": \"0x25\",\n" +
            "        \"gas\": \"0xc350\",\n" +
            "        \"from\": \"0xa7d9ddbe1f17865597fbd27ec712455208b6b76d\",\n" +
            "        \"transactionIndex\": \"0x41\",\n" +
            "        \"to\": \"0xf02c1c8e6114b1dbe8937a39260b5b0a374432bb\",\n" +
            "        \"type\": \"0x0\",\n" +
            "        \"value\": \"0xf3dbb76162000\",\n" +
            "        \"nonce\": \"0x15\",\n" +
            "        \"gasPrice\": \"0x4a817c800\"\n" +
            "    }\n" +
            "}";

    @Test
    public void test() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        FetchTransactionsResponse response = objectMapper.readValue(json, FetchTransactionsResponse.class);
        System.out.println(response);
    }
}