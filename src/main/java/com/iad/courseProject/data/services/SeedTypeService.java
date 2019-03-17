package com.iad.courseProject.data.services;

import com.iad.courseProject.data.entities.SeedType;
import com.iad.courseProject.data.repositories.SeedTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeedTypeService {
    private final SeedTypeRepository seedTypeRepository;

    @Autowired
    public SeedTypeService(SeedTypeRepository seedTypeRepository) {
        this.seedTypeRepository = seedTypeRepository;
    }

    public List<SeedType> getAll() {
        return seedTypeRepository.findAll();
    }
}
