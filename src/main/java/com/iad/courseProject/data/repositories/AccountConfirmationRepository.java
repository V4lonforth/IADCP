package com.iad.courseProject.data.repositories;

import com.iad.courseProject.data.entities.AccountConfirmation;
import com.iad.courseProject.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountConfirmationRepository extends JpaRepository<AccountConfirmation, User> {
    AccountConfirmation findByConfirmationId(String confirmationId);
}
