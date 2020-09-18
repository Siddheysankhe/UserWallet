package com.example.UserWallet.service;

import com.example.UserWallet.converter.TransactionConverter;
import com.example.UserWallet.dtos.TransactionDto;
import com.example.UserWallet.dtos.UserAccountDto;
import com.example.UserWallet.entity.Transaction;
import com.example.UserWallet.entity.UserAccount;
import com.example.UserWallet.enums.TransactionTypeEnum;
import com.example.UserWallet.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionConverter transactionConverter;

    @Autowired
    private UserAccountService userAccountService;

    @Transactional(rollbackFor = RuntimeException.class)
    public Transaction createTransaction(Transaction transaction) throws Exception {

        BigDecimal balance = getBalanceForUser(transaction.getUserAccount().getId());

        if (transaction.getTransactionType() == TransactionTypeEnum.DEBIT) {
            if (balance.compareTo(transaction.getAmount()) < 0) {
                throw new Exception(String.format("Your wallet balance is %.2f hence cannot perform a DEBIT transaction of %.2f ",
                        balance.doubleValue(), transaction.getAmount().doubleValue()));
            }
        }
        transaction = transactionRepository.save(transaction);
        return transaction;
    }

    /**
     *
     * @param userId
     * @return
     * @throws Exception
     */
    private BigDecimal getBalanceForUser(Integer userId) throws Exception {
        List<Transaction> transactionsForUser = transactionRepository.getAllByUserAccount(userId);

        BigDecimal balance = BigDecimal.ZERO;
        for (Transaction transaction : transactionsForUser) {
            if (transaction.getTransactionType() == TransactionTypeEnum.CREDIT) {
                balance = balance.add(transaction.getAmount());
            } else if (transaction.getTransactionType() == TransactionTypeEnum.DEBIT) {
                balance = balance.subtract(transaction.getAmount());
            }
        }

        return balance;
    }

    /**
     *
     * @param userAccountId
     * @param transactionDto
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public TransactionDto addMoney(Integer userAccountId, TransactionDto transactionDto) throws Exception {
        UserAccountDto userAccount = userAccountService.getUser(userAccountId);
        transactionDto.setUserAccountId(userAccount.getId());

        if (transactionDto.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new Exception("Cannot Add Negative amount in wallet");
        }

        Transaction transaction = transactionConverter.convertModelToEntity(transactionDto);
        transaction = createTransaction(transaction);

        return transactionConverter.convertEntityToModel(transaction);
    }
}
