package com.iad.courseProject.data.services;

import com.iad.courseProject.data.entities.Order;
import com.iad.courseProject.data.entities.User;
import com.iad.courseProject.data.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order add(Order order) {
        return orderRepository.saveAndFlush(order);
    }
    public void delete(Order order) {
        orderRepository.delete(order);
    }
    public List<Order> getByUserOrderByOrderDate(User user) {
        return orderRepository.getByUserOrderByOrderDate(user);
    }
}
