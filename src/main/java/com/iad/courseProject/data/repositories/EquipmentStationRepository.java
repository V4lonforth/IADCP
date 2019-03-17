package com.iad.courseProject.data.repositories;

import com.iad.courseProject.data.entities.EquipmentStation;
import org.postgresql.geometric.PGpoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EquipmentStationRepository extends JpaRepository<EquipmentStation, Long> {
    @Query("select e from EquipmentStation e order by function('getDistance', e.position, :position) asc")
    List<EquipmentStation> findAllByOrderByClosestPosition(@Param("position") PGpoint position);
}
