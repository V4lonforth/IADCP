package com.iad.courseProject.jsf;

import com.iad.courseProject.data.entities.Region;
import com.iad.courseProject.data.entities.Sector;
import com.iad.courseProject.data.entities.types.SectorState;
import com.iad.courseProject.data.services.RegionService;
import com.iad.courseProject.data.services.SectorService;
import com.iad.courseProject.regions.AddSectorResult;
import org.postgresql.geometric.PGpoint;
import org.postgresql.geometric.PGpolygon;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named
@ViewScoped
public class SectorCreationBean {
    private Region region;

    private List<PGpoint> points;
    private double x;
    private double y;

    private String name;

    private final RegionService regionService;
    private final SectorService sectorService;

    public List<PGpoint> getPoints() {
        return points;
    }
    public void setPoints(List<PGpoint> points) {
        this.points = points;
    }

    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }
    public void setY(double y) {
        this.y = y;
    }

    public String getParsedShape(Long regionId) {
        region = regionService.findById(regionId);
        return region.getParsedShape();
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    @Autowired
    public SectorCreationBean(RegionService regionService, SectorService sectorService) {
        this.regionService = regionService;
        this.sectorService = sectorService;
        this.points = new ArrayList<>();
    }

    public void addPoint() {
        points.add(new PGpoint(x, y));
    }
    public void removePoint() {
        if (points.size() > 0) {
            points.remove(points.size() - 1);
        }
    }
    public void clearList() {
        points.clear();
    }
    public String save() {
        PGpoint[] pointsArray = new PGpoint[points.size()];
        region.getSectors().add(new Sector(region, SectorState.CREATED, name, new PGpolygon(points.toArray(pointsArray)),
                new Timestamp(new Date().getTime()), null));
        regionService.save(region);
        return "sectorSaved";
    }

    public void deleteSector(Sector sector) {
        if (sector.getOrders() == null) {
            sectorService.delete(sector);
        } else {
            sector.setState(SectorState.DELETED);
            sectorService.save(sector);
        }
    }

    public AddSectorResult addSector() {
        PGpolygon selectedPolygon = new PGpolygon((PGpoint[]) points.toArray());
        for (Sector sector : region.getSectors()) {
            if (checkCollision(selectedPolygon, sector.getShape())) {
                return AddSectorResult.SECTORS_INTERSECT;
            }
        }
        Sector sector = new Sector(region, SectorState.CREATED, name, selectedPolygon, new Timestamp(new Date().getTime()), null);
        sectorService.save(sector);
        return AddSectorResult.SUCCESS;
    }

    public AddSectorResult changeSector(Sector selectedSector, List<PGpoint> points) {
        PGpolygon newPolygon = new PGpolygon((PGpoint[]) points.toArray());
        for (Sector sector : selectedSector.getRegion().getSectors()) {
            if (sector != selectedSector && checkCollision(newPolygon, sector.getShape())) {
                return AddSectorResult.SECTORS_INTERSECT;
            }
        }
        selectedSector.setShape(newPolygon);
        sectorService.save(selectedSector);
        return AddSectorResult.SUCCESS;
    }

    private boolean checkCollision(PGpolygon polygon1, PGpolygon polygon2) {
        return getDepth(polygon1, polygon2) < 0f && getDepth(polygon2, polygon1) < 0f;
    }
    private double getDepth(PGpolygon polygon1, PGpolygon polygon2) {
        double maxDistance = Float.NEGATIVE_INFINITY;
        for (int i = 0; i < polygon1.points.length; i++)
        {
            PGpoint normal = getNormal(polygon1, i);
            int supportPointIndex = getSupportPointIndex(polygon2, new PGpoint(-normal.x, -normal.y));
            maxDistance = Double.max(maxDistance, dot(normal,
                    new PGpoint(polygon2.points[supportPointIndex].x - polygon1.points[i].x, polygon2.points[supportPointIndex].y - polygon1.points[i].y)));
        }
        return maxDistance;
    }
    private int getSupportPointIndex(PGpolygon polygon, PGpoint direction) {
        int maxIndex = 0;
        double maxValue = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < polygon.points.length; i++)
        {
            double dot = dot(polygon.points[i], direction);
            if (dot > maxValue)
            {
                maxValue = dot;
                maxIndex = i;
            }
        }
        return maxIndex;
    }
    private PGpoint getNormal(PGpolygon polygon, int index) {
        PGpoint point = new PGpoint(polygon.points[(index + 1) % polygon.points.length].y - polygon.points[index].y,
                polygon.points[index].x - polygon.points[(index + 1) % polygon.points.length].x);
        double length = Math.sqrt(point.x * point.x + point.y * point.y);
        return new PGpoint(point.x / length, point.y / length);
    }
    private double dot(PGpoint point1, PGpoint point2) {
        return point1.x * point2.x + point1.y * point2.y;
    }
}
