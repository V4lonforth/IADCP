package com.iad.courseProject.data.services;

import com.iad.courseProject.data.repositories.SeedInOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeedInOrderService {
    private final SeedInOrderRepository seedInOrderRepository;

    @Autowired
    public SeedInOrderService(SeedInOrderRepository seedInOrderRepository) {
        this.seedInOrderRepository = seedInOrderRepository;
    }
}
