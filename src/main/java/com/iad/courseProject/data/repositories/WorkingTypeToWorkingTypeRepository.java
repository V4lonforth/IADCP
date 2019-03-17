package com.iad.courseProject.data.repositories;

import com.iad.courseProject.data.entities.WorkingTypeToWorkingType;
import com.iad.courseProject.data.entities.types.WorkingType;
import com.iad.courseProject.data.entities.types.WorkingTypeToWorkingTypeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkingTypeToWorkingTypeRepository extends JpaRepository<WorkingTypeToWorkingType, WorkingTypeToWorkingTypeId> {
    @Query("select wt.offeringType from WorkingTypeToWorkingType wt where wt.orderedType = :type")
    List<WorkingType> getOfferingTypeByOrderedType(@Param("type") WorkingType workingType);
}
