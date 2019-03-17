package com.iad.courseProject.data.entities.types;

public class WorkingTypeToWorkingTypeId {
    private WorkingType orderedType;
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
