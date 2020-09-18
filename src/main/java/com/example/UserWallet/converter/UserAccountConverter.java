package com.example.UserWallet.converter;

import com.example.UserWallet.dtos.UserAccountDto;
import com.example.UserWallet.entity.Transaction;
import com.example.UserWallet.entity.UserAccount;
import com.example.UserWallet.enums.TransactionTypeEnum;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserAccountConverter implements Converter<UserAccount, UserAccountDto>{

    @Override
    public UserAccount convertModelToEntity(UserAccountDto model) {
        return null;
    }

    @Override
    public UserAccountDto convertEntityToModel(UserAccount entity) {
        UserAccountDto userAccountDto = new UserAccountDto();
        userAccountDto.setId(entity.getId());
        userAccountDto.setUserName(entity.getUserName());
        userAccountDto.setEmail(entity.getEmail());
        userAccountDto.setDateCreated(entity.getCreatedAt());

        BigDecimal balance = BigDecimal.ZERO;
        for (Transaction transaction : entity.getTransactions()) {
            if (transaction.getTransactionType() == TransactionTypeEnum.CREDIT) {
                balance = balance.add(transaction.getAmount());
            } else if (transaction.getTransactionType() == TransactionTypeEnum.DEBIT) {
                balance = balance.subtract(transaction.getAmount());
            }
        }
        userAccountDto.setBalance(balance);

        return userAccountDto;
    }
}
