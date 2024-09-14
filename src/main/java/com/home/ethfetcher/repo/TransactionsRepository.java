package com.home.ethfetcher.repo;

import com.home.ethfetcher.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionsRepository extends JpaRepository<Transaction, String> {
}