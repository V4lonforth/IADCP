package com.iad.courseProject.regions;

import com.iad.courseProject.data.entities.Region;
import com.iad.courseProject.data.entities.Sector;
import com.iad.courseProject.data.entities.types.SectorState;
import com.iad.courseProject.data.services.RegionService;
import com.iad.courseProject.data.services.SectorService;
import org.postgresql.geometric.PGpoint;
import org.postgresql.geometric.PGpolygon;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Named
public class SectorController {
    private final SectorService sectorService;

    @Autowired
    public SectorController(RegionService regionService, SectorService sectorService) {
        this.sectorService = sectorService;
    }

}
