package com.example.StorePractice.services;

import com.example.StorePractice.reposetories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountService {
    @Autowired
    private UserRepo userRepo;
}
