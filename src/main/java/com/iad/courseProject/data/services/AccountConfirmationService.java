package com.iad.courseProject.data.services;

import com.iad.courseProject.data.entities.AccountConfirmation;
//import com.iad.courseProject.data.repositories.AccountConfirmationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountConfirmationService {
    //private final AccountConfirmationRepository accountConfirmationRepository;

    //@Autowired
    //public AccountConfirmationService(AccountConfirmationRepository accountConfirmationRepository) {
    //    this.accountConfirmationRepository = accountConfirmationRepository;
    //}

    public AccountConfirmation add(AccountConfirmation accountConfirmation) {
        return null;//accountConfirmationRepository.saveAndFlush(accountConfirmation);
    }
    public AccountConfirmation getByConfirmationId(String confirmationId) {
        return null;//accountConfirmationRepository.findByConfirmationId(confirmationId);
    }
    public void delete(AccountConfirmation accountConfirmation) {
        //accountConfirmationRepository.delete(accountConfirmation);
    }
}
