package com.iad.courseProject.data.repositories;

import com.iad.courseProject.data.entities.Order;
import com.iad.courseProject.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> getByUserOrderByOrderDate(User user);
}
