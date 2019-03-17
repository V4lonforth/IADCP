package com.iad.courseProject;

import com.iad.courseProject.data.entities.*;
import com.iad.courseProject.data.entities.types.*;
import com.iad.courseProject.data.services.*;
import org.postgresql.geometric.PGpoint;
import org.postgresql.geometric.PGpolygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class InsertData {
    private final UserService userService;
    private final RegionService regionService;
    private final EquipmentTypeService equipmentTypeService;
    private final EquipmentStationService equipmentStationService;
    private final EquipmentUnitService equipmentUnitService;

    @Autowired
    public InsertData(UserService userService, RegionService regionService, EquipmentTypeService equipmentTypeService, EquipmentStationService equipmentStationService, EquipmentUnitService equipmentUnitService) {
        this.userService = userService;
        this.regionService = regionService;
        this.equipmentTypeService = equipmentTypeService;
        this.equipmentStationService = equipmentStationService;
        this.equipmentUnitService = equipmentUnitService;
    }

    @RequestMapping("/insertData")
    public void insert() {
        User user = new User("v4lonforth", "v4lonforth@gmail.com", "1234", AccessLevel.USER, AccountStatus.OK);
        List<Region> regions = new ArrayList<>();
        Region region = new Region("Регион1", new PGpolygon(new PGpoint[] {
                new PGpoint(53.404746d, 56.038912d),
                new PGpoint(53.408978d, 56.040570d),
                new PGpoint(53.408901d, 56.044476d),
                new PGpoint(53.404731d, 56.043316d)
        }), RegionState.CREATED, "123");
        regions.add(region);
        regionService.save(region);

        region = new Region("Регион2", new PGpolygon(new PGpoint[] {
                new PGpoint(53.399020d, 56.046218d),
                new PGpoint(53.403073d, 56.045732d),
                new PGpoint(53.405714d, 56.051256d),
                new PGpoint(53.397536d, 56.052165d),
                new PGpoint(53.397392d, 56.048403d)
        }), RegionState.CREATED, "321");
        regions.add(region);

        user.setRegions(regions);
        regionService.save(region);
        userService.save(user);

        EquipmentType equipmentType1 = new EquipmentType(WorkingType.PLOW, "Пахатель1", "Пашет", 30, 3.1d, new BigDecimal(2500), new BigDecimal(500), null);
        EquipmentType equipmentType2 = new EquipmentType(WorkingType.PLOW, "Пахатель2", "Пашет быстро", 33, 3.6d, new BigDecimal(3000), new BigDecimal(600), null);
        equipmentTypeService.save(equipmentType1);
        equipmentTypeService.save(equipmentType2);

        EquipmentStation equipmentStation1 = new EquipmentStation(2000, new PGpoint(53.947275d, 56.344371d));
        EquipmentStation equipmentStation2 = new EquipmentStation(2000, new PGpoint(53.047275d, 56.144371d));
        equipmentStationService.save(equipmentStation1);
        equipmentStationService.save(equipmentStation2);

        EquipmentUnit unit = new EquipmentUnit(equipmentType1, EquipmentState.AVAILABLE, equipmentStation1);
        equipmentUnitService.save(unit);
        unit = new EquipmentUnit(equipmentType1, EquipmentState.AVAILABLE, equipmentStation2);
        equipmentUnitService.save(unit);
        unit = new EquipmentUnit(equipmentType2, EquipmentState.AVAILABLE, equipmentStation1);
        equipmentUnitService.save(unit);
        unit = new EquipmentUnit(equipmentType1, EquipmentState.AVAILABLE, equipmentStation1);
        equipmentUnitService.save(unit);
        unit = new EquipmentUnit(equipmentType2, EquipmentState.AVAILABLE, equipmentStation2);
        equipmentUnitService.save(unit);
        unit = new EquipmentUnit(equipmentType1, EquipmentState.AVAILABLE, equipmentStation2);
        equipmentUnitService.save(unit);
        unit = new EquipmentUnit(equipmentType2, EquipmentState.AVAILABLE, equipmentStation1);
        equipmentUnitService.save(unit);
        unit = new EquipmentUnit(equipmentType1, EquipmentState.AVAILABLE, equipmentStation2);
        equipmentUnitService.save(unit);
    }
}
