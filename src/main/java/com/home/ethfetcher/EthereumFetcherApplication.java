package com.home.ethfetcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
	public class EthereumFetcherApplication {

	public static void main(String[] args) {
		SpringApplication.run(EthereumFetcherApplication.class, args);
	}

}
