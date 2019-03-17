package com.iad.courseProject.data.services;

import com.iad.courseProject.data.entities.Region;
import com.iad.courseProject.data.repositories.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegionService {
    private final RegionRepository regionRepository;

    @Autowired
    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    public Region save(Region region) {
        return regionRepository.saveAndFlush(region);
    }
    public Region findByCadastralNumber(String cadastralNumber) {
        return regionRepository.findDistinctByCadastralNumber(cadastralNumber);
    }
    public Region findById(Long id) {
        return regionRepository.findById(id).get();
    }
}
