package com.iad.courseProject;

import com.iad.courseProject.data.entities.EquipmentType;

public class GroupedEquipmentUnit {
    private EquipmentType equipmentType;
    private Long count;

    public GroupedEquipmentUnit(EquipmentType equipmentType, Long count) {
        this.equipmentType = equipmentType;
        this.count = count;
    }

    public EquipmentType getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(EquipmentType equipmentType) {
        this.equipmentType = equipmentType;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
