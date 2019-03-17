package com.iad.courseProject.data.entities;

import com.iad.courseProject.data.entities.types.PGPolygonConverter;
import com.iad.courseProject.data.entities.types.SectorState;
import org.postgresql.geometric.PGpolygon;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
public class Sector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "regionId")
    private Region region;

    @Column(nullable = false)
    private SectorState state;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "polygon")
    @Convert(converter = PGPolygonConverter.class)
    private PGpolygon shape;

    @Column(insertable = false)
    private double area;

    private SeedType crop;

    @Column(nullable = false)
    private Timestamp creationDate;
    private Timestamp removingDate;

    @ManyToMany(mappedBy = "sectors")
    private List<Order> orders;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Region getRegion() {
        return region;
    }
    public void setRegion(Region region) {
        this.region = region;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public PGpolygon getShape() {
        return shape;
    }
    public void setShape(PGpolygon shape) {
        this.shape = shape;
    }

    public String getParsedShape() {
        StringBuilder parsedShape = new StringBuilder();
        parsedShape.append("[");
        for (int i = 0; i < shape.points.length; i++){
            parsedShape.append("{x:").append(shape.points[i].x).append(",y:").append(shape.points[i].y).append("}");
            if (i < shape.points.length - 1) {
                parsedShape.append(",");
            }
        }
        parsedShape.append("]");
        return parsedShape.toString();
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Timestamp getRemovingDate() {
        return removingDate;
    }
    public void setRemovingDate(Timestamp removingDate) {
        this.removingDate = removingDate;
    }

    public List<Order> getOrders() {
        return orders;
    }
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public double getArea() {
        return area;
    }

    public SectorState getState() {
        return state;
    }
    public void setState(SectorState state) {
        this.state = state;
    }

    public SeedType getCrop() {
        return crop;
    }
    public void setCrop(SeedType crop) {
        this.crop = crop;
    }

    public String getFormattedState() {
        switch (state) {
            case CREATED:
                return "Участок создан";
            case PLOWED:
                return "Участок вспахан";
            case SOWN:
                return "Участок засеян, кульура: " + crop.getName();
            case HARVESTED:
                return "Культура собрана";
            default:
                return "";
        }
    }

    public Sector(Region region, SectorState state, String name, PGpolygon shape, Timestamp creationDate, Timestamp removingDate) {
        this.region = region;
        this.state = state;
        this.name = name;
        this.shape = shape;
        this.creationDate = creationDate;
        this.removingDate = removingDate;
    }
    public Sector() {
    }
}
