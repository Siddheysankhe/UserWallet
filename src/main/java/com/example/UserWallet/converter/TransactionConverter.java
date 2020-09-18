package com.example.UserWallet.converter;

import com.example.UserWallet.dtos.TransactionDto;
import com.example.UserWallet.entity.Transaction;
import org.springframework.stereotype.Service;

@Service
public class TransactionConverter implements Converter<Transaction, TransactionDto> {

    @Override
    public Transaction convertModelToEntity(TransactionDto model) {
        return null;
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
}
