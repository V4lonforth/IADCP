package com.iad.courseProject.data.services;

import com.iad.courseProject.data.entities.SectorStateToWorkingType;
import com.iad.courseProject.data.entities.types.SectorState;
import com.iad.courseProject.data.entities.types.WorkingType;
import com.iad.courseProject.data.repositories.SectorStateToWorkingTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectorStateToWorkingTypeService {
    private final SectorStateToWorkingTypeRepository sectorStateToWorkingTypeRepository;

    @Autowired
    public SectorStateToWorkingTypeService(SectorStateToWorkingTypeRepository sectorStateToWorkingTypeRepository) {
        this.sectorStateToWorkingTypeRepository = sectorStateToWorkingTypeRepository;
    }

    public List<WorkingType> getWorkingTypesBySectorState(SectorState sectorState) {
        return sectorStateToWorkingTypeRepository.getWorkingTypeBySectorState(sectorState);
    }
    public SectorStateToWorkingType save(SectorStateToWorkingType sectorStateToWorkingType) {
        return sectorStateToWorkingTypeRepository.save(sectorStateToWorkingType);
    }
    public void delete(SectorStateToWorkingType sectorStateToWorkingType) {
        sectorStateToWorkingTypeRepository.delete(sectorStateToWorkingType);
    }
}
