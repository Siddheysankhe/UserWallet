package com.example.UserWallet.controller;

import com.example.UserWallet.dtos.TransactionDto;
import com.example.UserWallet.entity.Transaction;
import com.example.UserWallet.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/transact")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/add/{id}")
    public ResponseEntity addMoney(@PathVariable("id") Long userAccountId, @RequestBody TransactionDto transactionDto) {
        //Transaction
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

}
