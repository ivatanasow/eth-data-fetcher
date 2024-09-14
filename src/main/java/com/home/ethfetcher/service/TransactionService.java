package com.home.ethfetcher.service;

import com.home.ethfetcher.domain.Transaction;
import com.home.ethfetcher.domain.UserEntity;
import com.home.ethfetcher.model.ResponseData;
import com.home.ethfetcher.repo.TransactionsRepository;
import com.home.ethfetcher.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TransactionService {

    private final TransactionFetcher transactionFetcher;

    private final TransactionsRepository transactionsRepository;

    private final UserRepository userRepository;

    private final JwtService jwtService;

    public TransactionService(TransactionFetcher transactionFetcher,
                              TransactionsRepository transactionsRepository,
                              UserRepository userRepository,
                              JwtService jwtService) {
        this.transactionFetcher = transactionFetcher;
        this.transactionsRepository = transactionsRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Cacheable
    @Transactional
    public ResponseData getTransactions(Set<String> hashes) {
        List<Transaction> transactions = transactionsRepository.findAllById(hashes);

        List<String> toFetch = findMissingHashes(hashes, transactions);
        if (toFetch.isEmpty()) {
            return new ResponseData(transactions, Collections.emptyMap());
        }

        ResponseData responseData = transactionFetcher.fetchTransactions(toFetch);
        transactionsRepository.saveAll(responseData.getTransactions());
        responseData.getTransactions().addAll(transactions);

        return responseData;
    }


    public Page<Transaction> getAllTransactions(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return transactionsRepository.findAll(pageable);
    }

    public List<Transaction> getTransactionsByToken(String token) {
        String username = jwtService.extractUsername(token);

        UserEntity user = userRepository
                .findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " was not found in the database"));

        return user.getTransactions().stream().toList();
    }

    private List<String> findMissingHashes(Set<String> hashes, List<Transaction> transactions) {
        Set<String> persistedHashes = transactions.stream().map(Transaction::getTransactionHash).collect(Collectors.toSet());
        return hashes.stream().filter(h -> !persistedHashes.contains(h)).toList();
    }

}
