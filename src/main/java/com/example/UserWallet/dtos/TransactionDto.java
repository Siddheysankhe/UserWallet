package com.example.UserWallet.dtos;

import com.example.UserWallet.enums.TransactionTypeEnum;

import java.math.BigDecimal;
import java.util.Date;

public class TransactionDto {

    private Integer id;
    private Integer userAccountId;
    private BigDecimal amount;
    private String details;
    private Date transactionDate;
    private Long transactionReference;
    private TransactionTypeEnum transactionTypeEnum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(Integer userAccountId) {
        this.userAccountId = userAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Long getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(Long transactionReference) {
        this.transactionReference = transactionReference;
    }

    public TransactionTypeEnum getTransactionTypeEnum() {
        return transactionTypeEnum;
    }

    public void setTransactionTypeEnum(TransactionTypeEnum transactionTypeEnum) {
        this.transactionTypeEnum = transactionTypeEnum;
    }
}
