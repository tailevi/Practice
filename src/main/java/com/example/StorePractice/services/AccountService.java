package com.example.StorePractice.services;

import com.example.StorePractice.models.Account;
import com.example.StorePractice.models.Role;
import com.example.StorePractice.payload.request.RegisterRequest;
import com.example.StorePractice.payload.response.GenericResponses;
import com.example.StorePractice.reposetories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountService {
    @Autowired
    private UserRepo userRepo;


    public GenericResponses registerAccount(RegisterRequest account){

        if(userRepo.existsByMail(account.getMail()))
        {
            return GenericResponses.builder()
                    .message("Account Mail is already in use")
                    .build();
        }

        Account newAccount = Account.builder()
                .firstName(account.getFirstName())
                .mail(account.getMail())
                .password(account.getPassword())
                .lastName(account.getLastName())
                .role(Role.USER)
                .build();
        userRepo.save(newAccount);
        return GenericResponses.builder()
                .message("Account Made")
                .build();
    }
}
