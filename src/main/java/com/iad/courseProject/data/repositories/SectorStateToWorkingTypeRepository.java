package com.iad.courseProject.data.repositories;

import com.iad.courseProject.data.entities.SectorStateToWorkingType;
import com.iad.courseProject.data.entities.types.SectorState;
import com.iad.courseProject.data.entities.types.SectorStateToWorkingTypeId;
import com.iad.courseProject.data.entities.types.WorkingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SectorStateToWorkingTypeRepository extends JpaRepository<SectorStateToWorkingType, SectorStateToWorkingTypeId> {
    @Query("select s.workingType from SectorStateToWorkingType s where s.sectorState = :state")
    List<WorkingType> getWorkingTypeBySectorState(@Param("state") SectorState sectorState);
}
