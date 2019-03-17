package com.iad.courseProject.data.repositories;

import com.iad.courseProject.GroupedEquipmentUnit;
import com.iad.courseProject.data.entities.EquipmentStation;
import com.iad.courseProject.data.entities.EquipmentType;
import com.iad.courseProject.data.entities.EquipmentUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface EquipmentUnitRepository extends JpaRepository<EquipmentUnit, Long> {
    @Query("select e from EquipmentUnit e " +
            "where e.station = :equipmentStation " +
            "and e.type = :equipmentType " +
            "and (select o from e.orders o where :startDate < o.finishDate " +
                    "and :finishDate > o.startDate) = null")
    List<EquipmentUnit> getByStationAndTypeAndDates(@Param("equipmentStation") EquipmentStation equipmentStation,
                                                    @Param("equipmentType") EquipmentType equipmentType,
                                                    @Param("startDate") Timestamp startDate,
                                                    @Param("finishDate") Timestamp finishDate);
    @Query("select new com.iad.courseProject.GroupedEquipmentUnit(e.type, count(e)) " +
            "from EquipmentUnit e " +
            "where e.station = :station " +
            "group by e.type " +
            "order by e.type.hectaresPerHour")
    List<GroupedEquipmentUnit> getGroupedEquipmentByStationOrderByWorkingSpeed(@Param("station") EquipmentStation equipmentStation);
}
