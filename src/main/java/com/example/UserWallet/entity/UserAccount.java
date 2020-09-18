package com.example.UserWallet.entity;

import com.sun.istack.NotNull;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "user_account")
@Where(clause = "deleted=0")
public class UserAccount extends BaseEntityIntID {

    @NotNull
    @Column(name = "user_name")
    private String userName;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "userAccount", fetch = FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>();

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }
}
