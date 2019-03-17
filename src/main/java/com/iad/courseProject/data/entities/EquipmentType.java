package com.iad.courseProject.data.entities;

import com.iad.courseProject.data.entities.types.WorkingType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;

@Entity
public class EquipmentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private WorkingType workingType;

    @Column(nullable = false)
    private String name;
    private String description;

    @Column(nullable = false)
    private double transportationSpeed;

    @Column(nullable = false)
    private double hectaresPerHour;

    @Column(nullable = false, scale = 2)
    private BigDecimal pricePerHectare;

    @Column(nullable = false, scale = 2)
    private BigDecimal basePrice;

    private String imageUrl;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public double getTransportationSpeed() {
        return transportationSpeed;
    }
    public void setTransportationSpeed(double transportationSpeed) {
        this.transportationSpeed = transportationSpeed;
    }

    public double getHectaresPerHour() {
        return hectaresPerHour;
    }
    public void setHectaresPerHour(double hectaresPerHour) {
        this.hectaresPerHour = hectaresPerHour;
    }

    public WorkingType getWorkingType() {
        return workingType;
    }
    public void setWorkingType(WorkingType workingType) {
        this.workingType = workingType;
    }

    public BigDecimal getPricePerHectare() {
        return pricePerHectare;
    }
    public void setPricePerHectare(BigDecimal pricePerHectare) {
        this.pricePerHectare = pricePerHectare;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }
    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public EquipmentType(WorkingType workingType, String name, String description, double transportationSpeed, double hectaresPerHour, BigDecimal pricePerHectare, BigDecimal basePrice, String imageUrl) {
        this.workingType = workingType;
        this.name = name;
        this.description = description;
        this.transportationSpeed = transportationSpeed;
        this.hectaresPerHour = hectaresPerHour;
        this.pricePerHectare = pricePerHectare;
        this.basePrice = basePrice;
        this.imageUrl = imageUrl;
    }

    public EquipmentType() {
    }
}