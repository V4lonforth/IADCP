package com.iad.courseProject.data.services;

import com.iad.courseProject.GroupedEquipmentUnit;
import com.iad.courseProject.data.entities.EquipmentStation;
import com.iad.courseProject.data.entities.EquipmentType;
import com.iad.courseProject.data.entities.EquipmentUnit;
import com.iad.courseProject.data.repositories.EquipmentUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class EquipmentUnitService {
    private final EquipmentUnitRepository equipmentUnitRepository;

    @Autowired
    public EquipmentUnitService(EquipmentUnitRepository equipmentUnitRepository) {
        this.equipmentUnitRepository = equipmentUnitRepository;
    }

    public List<EquipmentUnit> getByStationAndTypeAndDate(EquipmentStation equipmentStation, EquipmentType equipmentType, Timestamp start, Timestamp end) {
        return equipmentUnitRepository.getByStationAndTypeAndDates(equipmentStation, equipmentType, start, end);
    }
    public List<GroupedEquipmentUnit> getGroupedEquipmentByStationOrderByWorkingSpeed(EquipmentStation station) {
        return equipmentUnitRepository.getGroupedEquipmentByStationOrderByWorkingSpeed(station);
    }
    public EquipmentUnit save(EquipmentUnit equipmentUnit) {
        return equipmentUnitRepository.saveAndFlush(equipmentUnit);
    }
}
