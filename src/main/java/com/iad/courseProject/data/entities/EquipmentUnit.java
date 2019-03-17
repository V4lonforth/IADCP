package com.iad.courseProject.data.entities;

import com.iad.courseProject.data.entities.types.EquipmentState;

import javax.persistence.*;
import java.util.List;

@Entity
public class EquipmentUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "typeId", nullable = false)
    private EquipmentType type;

    @Column(nullable = false)
    private EquipmentState state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stationId", nullable = false)
    private EquipmentStation station;

    @ManyToMany(mappedBy = "equipmentUnits")
    private List<Order> orders;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public EquipmentType getType() {
        return type;
    }
    public void setType(EquipmentType type) {
        this.type = type;
    }

    public EquipmentStation getStation() {
        return station;
    }
    public void setStation(EquipmentStation station) {
        this.station = station;
    }

    public EquipmentState getState() {
        return state;
    }
    public void setState(EquipmentState state) {
        this.state = state;
    }

    public List<Order> getOrders() {
        return orders;
    }
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public EquipmentUnit(EquipmentType type, EquipmentState state, EquipmentStation station) {
        this.type = type;
        this.state = state;
        this.station = station;
    }

    public EquipmentUnit() {
    }
}
