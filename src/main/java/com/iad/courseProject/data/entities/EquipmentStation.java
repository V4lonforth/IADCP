package com.iad.courseProject.data.entities;

import com.iad.courseProject.data.entities.types.PGPointConverter;
import org.postgresql.geometric.PGpoint;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class EquipmentStation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false, columnDefinition = "point")
    @Convert(converter = PGPointConverter.class)
    private PGpoint position;

    @OneToMany(mappedBy = "station")
    private List<EquipmentUnit> equipmentUnits;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public PGpoint getPosition() {
        return position;
    }
    public void setPosition(PGpoint position) {
        this.position = position;
    }

    public List<EquipmentUnit> getEquipmentUnits() {
        return equipmentUnits;
    }
    public void setEquipmentUnits(List<EquipmentUnit> equipmentUnits) {
        this.equipmentUnits = equipmentUnits;
    }

    public EquipmentStation() {
    }

    public EquipmentStation(int capacity, PGpoint position) {
        this.capacity = capacity;
        this.position = position;
    }
}
