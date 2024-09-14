package com.home.ethfetcher.service;

import com.home.ethfetcher.domain.Transaction;
import com.home.ethfetcher.domain.UserEntity;
import com.home.ethfetcher.model.ResponseData;
import com.home.ethfetcher.repo.TransactionsRepository;
import com.home.ethfetcher.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionFetcher transactionFetcher;

    @Mock
    private TransactionsRepository transactionsRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private TransactionService transactionService;

    private Set<String> sampleHashes;

    private List<Transaction> sampleTransactions;

    @BeforeEach
    void setUp() {
        sampleHashes = new HashSet<>(List.of("0x88df016429689c079f3b2f6ad39fa052532c56795b733da78a91ebe6a713944b"));
        Transaction dummyTransaction = Transaction
                .builder()
                .transactionHash("0x88df016429689c079f3b2f6ad39fa052532c56795b733da78a91ebe6a713944b")
                .transactionStatus(1)
                .blockHash("0x1d59ff54b1eb26b013ce3cb5fc9dab3705b415a67127a003c3e61eb445bb8df2")
                .input("0x68656c6c6f21")
                .fromAddress("0xa7d9ddbe1f17865597fbd27ec712455208b6b76d")
                .toAddress("0xf02c1c8e6114b1dbe8937a39260b5b0a374432bb")
                .value("0xf3dbb76162000")
                .blockNumber(Integer.decode("0x5daf3b"))
                .build();

        sampleTransactions = List.of(dummyTransaction);
    }

    @Test
    void getTransactions_shouldReturnCachedDataWhenAllHashesPresent() {
        when(transactionsRepository.findAllById(sampleHashes)).thenReturn(sampleTransactions);

        ResponseData result = transactionService.getTransactions(sampleHashes);

        assertEquals(sampleTransactions, result.getTransactions());
        verify(transactionFetcher, never()).fetchTransactions(any());
        verify(transactionsRepository, never()).saveAll(any());
    }

    @Test
    void getTransactions_shouldFetchMissingData() {
        Set<String> hashes = new HashSet<>(List.of("hash1", "hash2"));
        List<Transaction> partialTransactions = Collections.singletonList(
                Transaction.builder().transactionHash("hash1").build()
        );
        List<String> missingHashes = Collections.singletonList("hash2");

        when(transactionsRepository.findAllById(hashes)).thenReturn(partialTransactions);
        List<Transaction> fetchedTransactions = new LinkedList<>();
        fetchedTransactions.add(Transaction.builder().transactionHash("hash2").build());
        when(transactionFetcher.fetchTransactions(missingHashes))
                .thenReturn(new ResponseData(fetchedTransactions, Collections.emptyMap()));

        ResponseData result = transactionService.getTransactions(hashes);

        assertEquals(2, result.getTransactions().size());
        verify(transactionFetcher, times(1)).fetchTransactions(missingHashes);
        verify(transactionsRepository, times(1)).saveAll(any());
    }

    @Test
    void getAllTransactions_shouldReturnAllTransactions() {
        when(transactionsRepository.findAll()).thenReturn(sampleTransactions);

        List<Transaction> result = transactionService.getAllTransactions();

        assertEquals(sampleTransactions, result);
    }

    @Test
    void getTransactionsByToken_shouldReturnUserTransactions() {
        String token = "sampleToken";
        String username = "sampleUser";

        UserEntity user = new UserEntity();
        user.setLogin(username);
        user.setTransactions(new HashSet<>(sampleTransactions));

        when(jwtService.extractUsername(token)).thenReturn(username);
        when(userRepository.findByLogin(username)).thenReturn(Optional.of(user));

        List<Transaction> result = transactionService.getTransactionsByToken(token);

        assertEquals(sampleTransactions, result);
    }

    @Test
    void getTransactionsByToken_shouldThrowExceptionWhenUserNotFound() {
        String token = "sampleToken";
        String username = "nonExistentUser";

        when(jwtService.extractUsername(token)).thenReturn(username);
        when(userRepository.findByLogin(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            transactionService.getTransactionsByToken(token);
        });
    }
}
