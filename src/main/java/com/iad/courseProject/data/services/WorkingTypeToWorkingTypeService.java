package com.iad.courseProject.data.services;

import com.iad.courseProject.data.entities.types.WorkingType;
import com.iad.courseProject.data.repositories.WorkingTypeToWorkingTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkingTypeToWorkingTypeService {
    private final WorkingTypeToWorkingTypeRepository workingTypeToWorkingTypeRepository;

    @Autowired
    public WorkingTypeToWorkingTypeService(WorkingTypeToWorkingTypeRepository workingTypeToWorkingTypeRepository) {
        this.workingTypeToWorkingTypeRepository = workingTypeToWorkingTypeRepository;
    }

    public List<WorkingType> getOfferingTypeByOrderedType(WorkingType workingType) {
        return workingTypeToWorkingTypeRepository.getOfferingTypeByOrderedType(workingType);
    }
}
