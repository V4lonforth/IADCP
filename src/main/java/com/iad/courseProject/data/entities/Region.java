package com.iad.courseProject.data.entities;

import com.iad.courseProject.data.entities.types.PGPointConverter;
import com.iad.courseProject.data.entities.types.PGPolygonConverter;
import com.iad.courseProject.data.entities.types.RegionState;
import com.iad.courseProject.data.services.RegionCustomizer;
import org.eclipse.persistence.annotations.Customizer;
import org.postgresql.geometric.PGpoint;
import org.postgresql.geometric.PGpolygon;

import javax.persistence.*;
import java.util.List;

@Entity
@Customizer(RegionCustomizer.class)
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "polygon")
    @Convert(converter = PGPolygonConverter.class)
    private PGpolygon shape;

    @Column(insertable = false)
    private double area;

    @Column(columnDefinition = "point", insertable = false)
    @Convert(converter = PGPointConverter.class)
    private PGpoint center;

    @Column(nullable = false)
    private RegionState state;

    @Column(nullable = false, unique = true)
    private String cadastralNumber;

    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL)
    private List<Sector> sectors;

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

    public RegionState getState() {
        return state;
    }
    public void setState(RegionState state) {
        this.state = state;
    }

    public String getCadastralNumber() {
        return cadastralNumber;
    }
    public void setCadastralNumber(String cadastralNumber) {
        this.cadastralNumber = cadastralNumber;
    }

    public List<Sector> getSectors() {
        return sectors;
    }
    public void setSectors(List<Sector> sectors) {
        this.sectors = sectors;
    }

    public double getArea() {
        return area;
    }

    public PGpoint getCenter() {
        return center;
    }

    public Region(String name, PGpolygon shape, RegionState state, String cadastralNumber) {
        this.name = name;
        this.shape = shape;
        this.state = state;
        this.cadastralNumber = cadastralNumber;
    }

    public Region() {
    }

}
