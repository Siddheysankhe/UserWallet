package com.example.UserWallet.controller;

import com.example.UserWallet.dtos.TransactionDto;
import com.example.UserWallet.entity.Transaction;
import com.example.UserWallet.exceptions.UserNotFoundException;
import com.example.UserWallet.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/transact")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/add/{id}")
    public ResponseEntity addMoney(@PathVariable("id") Integer userAccountId, @RequestBody TransactionDto transactionDto) {
        TransactionDto createdTransaction;
        try {
            createdTransaction = transactionService.addMoney(userAccountId, transactionDto);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createdTransaction, HttpStatus.OK);
    }

    @PostMapping("/transfer/{toUser}/from/{fromUser}")
    public ResponseEntity transferMoney(@PathVariable("toUser") Long toUserAccountId,
                                        @PathVariable("fromUser") Long fromUserAccountId, @RequestBody TransactionDto transactionDto) {
        List<TransactionDto> createdTransactions;
        try {
            createdTransactions = transactionService.transfer(transactionDto, toUserAccountId, fromUserAccountId);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<List<TransactionDto>>(createdTransactions, HttpStatus.OK);
    }

}
