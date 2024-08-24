package com.limechain.ethereumfetcher.client.protocol;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FetchTransactionsResponse {
    private Integer id;
    private String jsonrpc;
    private Result result;
    private Error error;

    public boolean hasError() {
        return error != null;
    }

    public boolean hasResult() {
        return result != null;
    }

    @Getter
    @NoArgsConstructor
    public static class Result {
        private String blockHash;
        private String blockNumber;
        private String transactionIndex;
        private String nonce;
        private String hash;
        private String from;
        private String gas;
        private String gasPrice;
        private String input;
        private String r;
        private String s;
        private String to;
        private String v;
        private String value;
        private String type;

        @Override
        public String toString() {
            return "Result{" +
                    "blockHash='" + blockHash + '\'' +
                    ", blockNumber='" + blockNumber + '\'' +
                    ", transactionIndex='" + transactionIndex + '\'' +
                    ", nonce='" + nonce + '\'' +
                    ", hash='" + hash + '\'' +
                    ", from='" + from + '\'' +
                    ", gas='" + gas + '\'' +
                    ", gasPrice='" + gasPrice + '\'' +
                    ", input='" + input + '\'' +
                    ", r='" + r + '\'' +
                    ", s='" + s + '\'' +
                    ", to='" + to + '\'' +
                    ", v='" + v + '\'' +
                    ", value='" + value + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Error {
        private int code;
        private String message;

        @Override
        public String toString() {
            return "Error{" +
                    "code=" + code +
                    ", message='" + message + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "FetchTransactionsResponse{" +
                "id=" + id +
                ", jsonrpc='" + jsonrpc + '\'' +
                ", result=" + result +
                ", error=" + error +
                '}';
    }
}
