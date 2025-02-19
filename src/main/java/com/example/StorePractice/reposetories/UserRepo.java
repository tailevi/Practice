package com.example.StorePractice.reposetories;


import com.example.StorePractice.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<Account,Integer> {
    Optional<Account> findByMail(String mail);
}
