package com.example.UserWallet.dtos;

import com.example.UserWallet.enums.TransactionTypeEnum;

public class MoneyTransferDto {

    private TransactionDto transactionDto;
    private TransactionTypeEnum commissionAndChargesParty;

    public TransactionDto getTransactionDto() {
        return transactionDto;
    }

    public void setTransactionDto(TransactionDto transactionDto) {
        this.transactionDto = transactionDto;
    }

    public TransactionTypeEnum getCommissionAndChargesParty() {
        return commissionAndChargesParty;
    }

    public void setCommissionAndChargesParty(TransactionTypeEnum commissionAndChargesParty) {
        this.commissionAndChargesParty = commissionAndChargesParty;
    }
}
