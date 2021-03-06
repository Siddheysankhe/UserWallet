package com.example.UserWallet.controller;

import com.example.UserWallet.dtos.MoneyTransferDto;
import com.example.UserWallet.dtos.TransactionDto;
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
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createdTransaction, HttpStatus.OK);
    }

    @PostMapping("/transfer/{toUser}/from/{fromUser}")
    public ResponseEntity transferMoney(@PathVariable("toUser") Integer toUserAccountId,
                                        @PathVariable("fromUser") Integer fromUserAccountId,
                                        @RequestBody MoneyTransferDto moneyTransferDto) {
        List<TransactionDto> createdTransactions;
        try {
            createdTransactions = transactionService.transfer(moneyTransferDto, toUserAccountId, fromUserAccountId);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createdTransactions, HttpStatus.OK);
    }

    @GetMapping("/status/{referenceId}")
    private ResponseEntity getStatusOfTransaction(@PathVariable("referenceId") String referenceId) {
        List<TransactionDto> transactionDtoList;
        try {
            transactionDtoList = transactionService.getStatusOfTransaction(referenceId);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(transactionDtoList, HttpStatus.OK);
    }

}
