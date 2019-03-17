package com.iad.courseProject.data.entities;

import com.iad.courseProject.data.entities.types.WorkingType;
import com.iad.courseProject.data.entities.types.WorkingTypeToWorkingTypeId;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(WorkingTypeToWorkingTypeId.class)
public class WorkingTypeToWorkingType {
    @Id
    private WorkingType orderedType;
    @Id
    private WorkingType offeringType;

    public WorkingType getOrderedType() {
        return orderedType;
    }
    public void setOrderedType(WorkingType orderedType) {
        this.orderedType = orderedType;
    }

    public WorkingType getOfferingType() {
        return offeringType;
    }
    public void setOfferingType(WorkingType offeringType) {
        this.offeringType = offeringType;
    }
}
