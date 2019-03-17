package com.iad.courseProject.data.entities;

import javax.persistence.*;

@Entity
public class SeedInOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "seedTypeId", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private SeedType seedType;

    @Column(nullable = false)
    private double amount;

    @JoinColumn(name = "orderId", nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private Order order;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public SeedType getSeedType() {
        return seedType;
    }
    public void setSeedType(SeedType seedType) {
        this.seedType = seedType;
    }

    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }

    public SeedInOrder(SeedType seedType, double amount, Order order) {
        this.seedType = seedType;
        this.amount = amount;
        this.order = order;
    }
    public SeedInOrder() {
    }
}
