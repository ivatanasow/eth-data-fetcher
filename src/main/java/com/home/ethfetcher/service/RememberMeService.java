package com.home.ethfetcher.service;

import com.home.ethfetcher.domain.Transaction;
import com.home.ethfetcher.domain.UserEntity;
import com.home.ethfetcher.repo.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RememberMeService {

    private final JwtService jwtService;

    private final UserRepository userRepository;

    public RememberMeService(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    public void remember(String token, List<Transaction> transactions) {
        String username = jwtService.extractUsername(token);
        UserEntity user = userRepository.findByLogin(username).orElseThrow(() -> new UsernameNotFoundException("User " + username + " was not found in the database"));
        user.getTransactions().addAll(transactions);
        userRepository.save(user);
    }
}
