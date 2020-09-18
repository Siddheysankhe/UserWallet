package com.example.UserWallet.converter;

import com.example.UserWallet.dtos.TransactionDto;
import com.example.UserWallet.entity.Transaction;
import com.example.UserWallet.entity.UserAccount;
import com.example.UserWallet.enums.TransactionTypeEnum;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class TransactionConverter implements Converter<Transaction, TransactionDto> {

    @Override
    public Transaction convertModelToEntity(TransactionDto model) {
        Transaction transaction = new Transaction();
        UserAccount userAccount = new UserAccount();
        userAccount.setId(model.getUserAccountId());
        transaction.setUserAccount(userAccount);
        transaction.setAmount(model.getAmount());
        transaction.setDetails(model.getDetails());
        transaction.setTransactionDate(getTransactionDate(model.getTransactionDate()));
        transaction.setTransactionReference(getTransactionReference(model.getTransactionReference()));
        transaction.setTransactionType(model.getTransactionTypeEnum());

        return transaction;
    }

    @Override
    public TransactionDto convertEntityToModel(Transaction entity) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setId(entity.getId());
        transactionDto.setUserAccountId(entity.getUserAccount().getId());
        transactionDto.setAmount(entity.getAmount());
        transactionDto.setDetails(entity.getDetails());
        transactionDto.setTransactionDate(entity.getTransactionDate());
        transactionDto.setTransactionTypeEnum(entity.getTransactionType());
        transactionDto.setTransactionReference(entity.getTransactionReference());
        return transactionDto;
    }

    /**
     *
     * @param transactionDate
     * @return
     */
    private Date getTransactionDate(Date transactionDate) {
        if (transactionDate == null) {
            return new Date();
        }

        return transactionDate;
    }

    /**
     *
     * @param transactionReference
     * @return
     */
    private String getTransactionReference(String transactionReference) {
        if (transactionReference == null) {
            transactionReference = UUID.randomUUID().toString();
        }
        return transactionReference;
    }
}
