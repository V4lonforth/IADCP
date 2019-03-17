package com.iad.courseProject.data.entities;

import com.iad.courseProject.data.entities.types.SectorState;
import com.iad.courseProject.data.entities.types.SectorStateToWorkingTypeId;
import com.iad.courseProject.data.entities.types.WorkingType;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(SectorStateToWorkingTypeId.class)
public class SectorStateToWorkingType {
    @Id
    private SectorState sectorState;
    @Id
    private WorkingType workingType;

    public SectorState getSectorState() {
        return sectorState;
    }
    public void setSectorState(SectorState sectorState) {
        this.sectorState = sectorState;
    }

    public WorkingType getWorkingType() {
        return workingType;
    }
    public void setWorkingType(WorkingType workingType) {
        this.workingType = workingType;
    }

    public SectorStateToWorkingType() {
    }

    public SectorStateToWorkingType(SectorState sectorState, WorkingType workingType) {
        this.sectorState = sectorState;
        this.workingType = workingType;
    }
}
