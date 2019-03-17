package com.iad.courseProject.data.repositories;

import com.iad.courseProject.data.entities.EquipmentType;
import com.iad.courseProject.data.entities.types.WorkingType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipmentTypeRepository extends JpaRepository<EquipmentType, Long> {
    List<EquipmentType> getByWorkingType(WorkingType workingType);
    List<EquipmentType> getByWorkingTypeOrderByHectaresPerHour(WorkingType workingType);
}
