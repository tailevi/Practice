package com.example.StorePractice.controllers;

import com.example.StorePractice.models.Account;
import com.example.StorePractice.payload.request.RegisterRequest;
import com.example.StorePractice.payload.response.GenericResponses;
import com.example.StorePractice.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<GenericResponses> register(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(accountService.registerAccount(registerRequest));
    }

}
