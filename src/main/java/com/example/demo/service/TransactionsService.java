package com.example.demo.service;

import com.example.demo.model.Transaction;
import com.example.demo.model.User;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class TransactionsService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.SERIALIZABLE)
    public void pay(String login){
        User user = userRepository.findByLogin(login).get();
        BigDecimal bigDecimal = new BigDecimal(0);
        for (Transaction t: user.getTransactions()){
            bigDecimal = bigDecimal.add(t.getValue().setScale(2,RoundingMode.HALF_EVEN));
        }
        if (bigDecimal.compareTo(BigDecimal.ZERO.add(BigDecimal.valueOf(1.1)))>=0){
            Transaction transaction = Transaction.builder()
                    .user(user)
                    .value(BigDecimal.valueOf(-1.1))
                    .currency("USD")
                    .build();
            transactionRepository.save(transaction);
        }
        else {
            throw new RuntimeException("Not enouth money");
        }
    }

    @Transactional(readOnly = true)
    public BigDecimal balance(String login){
        User user = userRepository.findByLogin(login).get();
        BigDecimal bigDecimal = new BigDecimal(0);
        for (Transaction t: user.getTransactions()){
            bigDecimal = bigDecimal.add(t.getValue().setScale(2,RoundingMode.HALF_EVEN));
        }
        return bigDecimal;
    }
}
