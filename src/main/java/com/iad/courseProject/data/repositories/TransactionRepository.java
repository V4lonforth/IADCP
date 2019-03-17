package com.iad.courseProject.data.repositories;

import com.iad.courseProject.data.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
