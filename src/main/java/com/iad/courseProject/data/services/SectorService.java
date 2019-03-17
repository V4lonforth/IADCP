package com.iad.courseProject.data.services;

import com.iad.courseProject.data.entities.Sector;
import com.iad.courseProject.data.repositories.SectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SectorService {
    private final SectorRepository sectorRepository;

    @Autowired
    public SectorService(SectorRepository sectorRepository) {
        this.sectorRepository = sectorRepository;
    }

    public Sector save(Sector sector) {
        return sectorRepository.saveAndFlush(sector);
    }
    public void delete(Sector sector) {
        sectorRepository.delete(sector);
    }
}
