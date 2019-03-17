package com.iad.courseProject.data.entities;

import com.iad.courseProject.data.entities.types.OrderStatus;
import com.iad.courseProject.data.entities.types.WorkingType;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "UserOrder")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User user;

    @Column(nullable = false)
    private WorkingType workingType;

    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false)
    private Timestamp orderDate;

    @Column(nullable = false)
    private Timestamp fulfillmentDate;

    @Column(nullable = false)
    private Timestamp startDate;
    @Column(nullable = false)
    private Timestamp finishDate;

    @Column(nullable = false)
    private String orderLinkId;

    @OneToOne(mappedBy = "order")
    private SeedInOrder seed;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "SectorsInOrder",
            joinColumns = @JoinColumn(name = "orderId"),
            inverseJoinColumns = @JoinColumn(name = "sectorId"))
    private List<Sector> sectors;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "EquipmentInOrder",
            joinColumns = @JoinColumn(name = "orderId"),
            inverseJoinColumns = @JoinColumn(name = "equipmentUnitId"))
    private List<EquipmentUnit> equipmentUnits;

    @OneToOne(mappedBy = "order")
    private Transaction transaction;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public Timestamp getFulfillmentDate() {
        return fulfillmentDate;
    }
    public void setFulfillmentDate(Timestamp fulfillmentDate) {
        this.fulfillmentDate = fulfillmentDate;
    }

    public Timestamp getStartDate() {
        return startDate;
    }
    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getFinishDate() {
        return finishDate;
    }
    public void setFinishDate(Timestamp finishDate) {
        this.finishDate = finishDate;
    }

    public List<Sector> getSectors() {
        return sectors;
    }
    public void setSectors(List<Sector> sectors) {
        this.sectors = sectors;
    }

    public List<EquipmentUnit> getEquipmentUnits() {
        return equipmentUnits;
    }
    public void setEquipmentUnits(List<EquipmentUnit> equipmentUnits) {
        this.equipmentUnits = equipmentUnits;
    }

    public Transaction getTransaction() {
        return transaction;
    }
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public WorkingType getWorkingType() {
        return workingType;
    }
    public void setWorkingType(WorkingType workingType) {
        this.workingType = workingType;
    }

    public OrderStatus getStatus() {
        return status;
    }
    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getOrderLinkId() {
        return orderLinkId;
    }
    public void setOrderLinkId(String orderLinkId) {
        this.orderLinkId = orderLinkId;
    }

    public SeedInOrder getSeed() {
        return seed;
    }
    public void setSeed(SeedInOrder seed) {
        this.seed = seed;
    }

    public Order(User user, WorkingType workingType, Timestamp orderDate, Timestamp fulfillmentDate, List<Sector> sectors, List<EquipmentUnit> equipmentUnits) {
        this.user = user;
        this.workingType = workingType;
        this.orderDate = orderDate;
        this.fulfillmentDate = fulfillmentDate;
        this.sectors = sectors;
        this.equipmentUnits = equipmentUnits;
    }

    public Order() {
    }
}
