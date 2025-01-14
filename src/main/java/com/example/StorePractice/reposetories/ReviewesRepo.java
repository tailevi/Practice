package com.example.StorePractice.reposetories;


import com.example.StorePractice.models.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewesRepo extends JpaRepository<Reviews,Long> {
}
