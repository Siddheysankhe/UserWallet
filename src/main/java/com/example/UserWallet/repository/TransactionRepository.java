package com.example.UserWallet.repository;

import com.example.UserWallet.entity.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {

    @Query(nativeQuery = true, value = "select * from transaction where user_account_id = ?")
    List<Transaction> getAllByUserAccount(Integer userId);

    List<Transaction> getAllByTransactionReference(String referenceId);
}
