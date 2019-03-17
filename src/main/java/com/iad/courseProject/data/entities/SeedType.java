package com.iad.courseProject.data.entities;

import org.postgresql.util.PGmoney;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class SeedType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    private String description;

    @Column(nullable = false, scale = 2)
    private BigDecimal pricePerHectare;

    @Column(nullable = false)
    private float timeToGrow;

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

    public float getTimeToGrow() {
        return timeToGrow;
    }
    public void setTimeToGrow(float timeToGrow) {
        this.timeToGrow = timeToGrow;
    }

    public BigDecimal getPricePerHectare() {
        return pricePerHectare;
    }
    public void setPricePerHectare(BigDecimal pricePerHectare) {
        this.pricePerHectare = pricePerHectare;
    }
}