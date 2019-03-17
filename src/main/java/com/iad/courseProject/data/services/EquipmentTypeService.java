package com.iad.courseProject.data.services;

import com.iad.courseProject.data.entities.EquipmentType;
import com.iad.courseProject.data.entities.types.WorkingType;
import com.iad.courseProject.data.repositories.EquipmentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentTypeService {
    private final EquipmentTypeRepository equipmentTypeRepository;

    @Autowired
    public EquipmentTypeService(EquipmentTypeRepository equipmentTypeRepository) {
        this.equipmentTypeRepository = equipmentTypeRepository;
    }

    public EquipmentType save(EquipmentType equipmentType) {
        return equipmentTypeRepository.saveAndFlush(equipmentType);
    }
    public List<EquipmentType> getAll() {
        return equipmentTypeRepository.findAll();
    }
    public List<EquipmentType> getByWorkingType(WorkingType workingType) {
        return equipmentTypeRepository.getByWorkingType(workingType);
    }
    public List<EquipmentType> getByWorkingTypeOrderByWorkingSpeed(WorkingType workingType) {
        return equipmentTypeRepository.getByWorkingTypeOrderByHectaresPerHour(workingType);
    }
}
