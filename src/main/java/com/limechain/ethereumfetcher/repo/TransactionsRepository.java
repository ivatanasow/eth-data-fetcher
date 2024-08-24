package com.limechain.ethereumfetcher.repo;

import com.limechain.ethereumfetcher.domain.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface TransactionsRepository extends CrudRepository<Transaction, String> {

    @Query("SELECT t FROM Transaction t WHERE t.transactionHash IN :transactionHashes")
    List<Transaction> findAllById(Set<String> transactionHashes);

    @Query("SELECT t FROM Transaction t")
    List<Transaction> findAll();
}