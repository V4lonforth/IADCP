package com.iad.courseProject.data.services;

import com.iad.courseProject.data.entities.EquipmentStation;
import com.iad.courseProject.data.repositories.EquipmentStationRepository;
import org.postgresql.geometric.PGpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentStationService {
    private final EquipmentStationRepository equipmentStationRepository;

    @Autowired
    public EquipmentStationService(EquipmentStationRepository equipmentStationRepository) {
        this.equipmentStationRepository = equipmentStationRepository;
    }

    public List<EquipmentStation> findAllSortedByDistance(PGpoint pos) {
        return equipmentStationRepository.findAllByOrderByClosestPosition(pos);
    }
    public EquipmentStation save(EquipmentStation equipmentStation) {
        return equipmentStationRepository.saveAndFlush(equipmentStation);
    }
    public void delete(EquipmentStation equipmentStation) {
        equipmentStationRepository.delete(equipmentStation);
    }
}
