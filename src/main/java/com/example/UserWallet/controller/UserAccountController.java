package com.example.UserWallet.controller;

import com.example.UserWallet.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/users")
public class UserAccountController {

    @Autowired
    private UserAccountService userAccountService;

    @GetMapping("/{id}")
    public
}
