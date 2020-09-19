package com.example.UserWallet.controller;

import com.example.UserWallet.dtos.TransactionDto;
import com.example.UserWallet.dtos.UserAccountDto;
import com.example.UserWallet.exceptions.UserNotFoundException;
import com.example.UserWallet.service.TransactionService;
import com.example.UserWallet.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/users")
public class UserAccountController {

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/{id}")
    public ResponseEntity getUser(@PathVariable("id") Integer id) {
        UserAccountDto userAccountDto;
        try {
            userAccountDto = userAccountService.getUser(id);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userAccountDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody UserAccountDto userAccountDto) {
        UserAccountDto userAccount;
        try {
            userAccount = userAccountService.createUser(userAccountDto);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userAccount, HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity updateUser(@RequestBody UserAccountDto userAccountDto) {
        UserAccountDto userAccount;
        try {
            userAccount = userAccountService.updateUser(userAccountDto);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userAccount, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/passbook")
    public ResponseEntity getUserPassbook(@PathVariable("id") Integer id) {
        List<TransactionDto> passbook;
        try {
            passbook = transactionService.getUserPassbook(id);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(passbook, HttpStatus.OK);
    }
}
