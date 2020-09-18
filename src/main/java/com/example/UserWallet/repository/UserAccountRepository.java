package com.example.UserWallet.repository;

import com.example.UserWallet.entity.UserAccount;
import org.springframework.data.repository.CrudRepository;

public interface UserAccountRepository extends CrudRepository<UserAccount, Integer> {
}
