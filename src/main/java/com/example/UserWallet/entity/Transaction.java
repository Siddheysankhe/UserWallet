package com.example.UserWallet.entity;

import com.example.UserWallet.enums.TransactionTypeEnum;
import com.sun.istack.NotNull;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity(name = "transaction")
@Where(clause = "deleted=0")
public class Transaction extends BaseEntityIntID {

    @ManyToOne
    private UserAccount userAccount;

    @NotNull
    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "details")
    private String details;

    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name = "transaction_date")
    private Date transactionDate;

    @NotNull
    @Column(name = "transaction_reference")
    private Long transactionReference;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(name = "transaction_type")
    private TransactionTypeEnum transactionType;

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
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

    public TransactionTypeEnum getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionTypeEnum transactionType) {
        this.transactionType = transactionType;
    }
}
