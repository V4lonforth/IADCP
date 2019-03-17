package com.iad.courseProject.data.repositories;

import com.iad.courseProject.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    User getUserByEmail(String email);
    User getUserByUsername(String username);
}
