package com.iad.courseProject.data.services;

import com.iad.courseProject.data.entities.User;
import com.iad.courseProject.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {
        return userRepository.saveAndFlush(user);
    }
    public User getRef(Long id) {
        return userRepository.getOne(id);
    }
    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    public User getByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }
    public User getByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }
}