package com.iad.courseProject.orders;

import com.iad.courseProject.GroupedEquipmentUnit;
import com.iad.courseProject.data.entities.*;
import com.iad.courseProject.data.entities.types.SectorState;
import com.iad.courseProject.data.entities.types.WorkingType;
import com.iad.courseProject.data.services.*;
import org.postgresql.geometric.PGpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Named;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

@Named
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class OrderController {
    private final OrderService orderService;
    private final EquipmentStationService equipmentStationService;
    private final EquipmentUnitService equipmentUnitService;
    private final SectorStateToWorkingTypeService sectorStateToWorkingTypeService;
    private final WorkingTypeToWorkingTypeService workingTypeToWorkingTypeService;
    private final EquipmentTypeService equipmentTypeService;
    private final Random random;
    private Map<SectorState, List<WorkingType>> sectorStateToWorkingTypes;
    private Map<WorkingType, List<WorkingType>> orderedTypeToOfferingTypes;
    private Order order;
    private OrderCreationStatus orderCreationStatus;

    private final double FAST_WORKING_TIME = 4;
    private final double SLOW_WORKING_TIME = 16;

    public OrderCreationStatus getOrderCreationStatus() {
        return orderCreationStatus;
    }

    @Autowired
    public OrderController(OrderService orderService, EquipmentStationService equipmentStationService, EquipmentUnitService equipmentUnitService, SectorStateToWorkingTypeService sectorStateToWorkingTypeService, WorkingTypeToWorkingTypeService workingTypeToWorkingTypeService, EquipmentTypeService equipmentTypeService) {
        this.orderService = orderService;
        this.equipmentStationService = equipmentStationService;
        this.equipmentUnitService = equipmentUnitService;
        this.sectorStateToWorkingTypeService = sectorStateToWorkingTypeService;
        this.workingTypeToWorkingTypeService = workingTypeToWorkingTypeService;
        this.equipmentTypeService = equipmentTypeService;
        sectorStateToWorkingTypes = new HashMap<>();
        orderedTypeToOfferingTypes = new HashMap<>();
        refreshSectorStateToWorkingTypes();
        refreshOrderedTypeToOfferingTypes();
        order = new Order();
        orderCreationStatus = OrderCreationStatus.SECTOR_SELECTION;
        random = new Random();
    }

    public void refreshSectorStateToWorkingTypes() {
        sectorStateToWorkingTypes.clear();
        for (SectorState sectorState : SectorState.values()) {
            List<WorkingType> workingTypes = sectorStateToWorkingTypeService.getWorkingTypesBySectorState(sectorState);
            sectorStateToWorkingTypes.put(sectorState, workingTypes);
        }
    }

    public void refreshOrderedTypeToOfferingTypes() {
        orderedTypeToOfferingTypes.clear();
        for (WorkingType workingType : WorkingType.values()) {
            List<WorkingType> workingTypes = workingTypeToWorkingTypeService.getOfferingTypeByOrderedType(workingType);
            orderedTypeToOfferingTypes.put(workingType, workingTypes);
        }
    }

    private double getDistance(PGpoint point1, PGpoint point2) {
        double lat1 = Math.toRadians(point1.x);
        double lat2 = Math.toRadians(point2.x);
        return Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(Math.toRadians(point1.y - point2.y)));
    }
    private double getSectorsArea(List<Sector> sectors) {
        double area = 0;
        for (Sector sector : sectors) {
            area += sector.getArea();
        }
        return area;
    }

    private double getWorkingSpeed(List<EquipmentUnit> equipmentUnits) {
        double speed = 0;
        for (EquipmentUnit equipmentUnit : equipmentUnits) {
            speed += equipmentUnit.getType().getHectaresPerHour();
        }
        return speed;
    }

    public BigDecimal getSeedPrice(SeedType seedType) {
        return seedType.getPricePerHectare().multiply(new BigDecimal(getSectorsArea(order.getSectors())));
    }

    private double getGroupedUnitsWorkingSpeed(List<GroupedEquipmentUnit> groupedEquipmentUnits) {
        double speed = 0;
        for (GroupedEquipmentUnit groupedEquipmentUnit : groupedEquipmentUnits) {
            speed += groupedEquipmentUnit.getCount() * groupedEquipmentUnit.getEquipmentType().getHectaresPerHour();
        }
        return speed;
    }

    public BigDecimal getWorkingPrice() {
        BigDecimal price = new BigDecimal(0);
        double area = getSectorsArea(order.getSectors());
        double speed = getWorkingSpeed(order.getEquipmentUnits());
        double time = area / speed;
        for (EquipmentUnit equipmentUnit : order.getEquipmentUnits()) {
            price = price
                    .add(equipmentUnit.getType().getBasePrice())
                    .add(new BigDecimal(area / time).multiply(equipmentUnit.getType().getPricePerHectare()));
        }
        if (order.getSeed() != null) {
            price = price.add(order.getSeed().getSeedType().getPricePerHectare().multiply(new BigDecimal(order.getSeed().getAmount())));
        }
        return price;
    }

    public void selectSectors(List<Sector> sectors) {
        order.setSectors(sectors);
        orderCreationStatus = OrderCreationStatus.WORKING_TYPE_SELECTION;
    }

    public SectorState checkOrderType(WorkingType workingType) {
        for (Sector sector : order.getSectors()) {
            if (!sectorStateToWorkingTypes.get(sector.getState()).contains(workingType)) {
                return sector.getState();
            }
        }
        return null;
    }

    public void selectOrderType(WorkingType workingType) {
        order.setWorkingType(workingType);
        if (workingType == WorkingType.PLOW) {
            orderCreationStatus = OrderCreationStatus.SEED_SELECTION;
        } else {
            orderCreationStatus = OrderCreationStatus.EQUIPMENT_SELECTION;
        }
    }

    public List<EquipmentType> findEquipmentTypes() {
        return equipmentTypeService.getByWorkingType(order.getWorkingType());
    }

    private GroupedEquipmentUnit getOfferingUnits(GroupedEquipmentUnit unitsAtStation, double requiredSpeed) {
        GroupedEquipmentUnit groupedEquipmentUnit;
        if (unitsAtStation.getEquipmentType().getHectaresPerHour() * unitsAtStation.getCount() < requiredSpeed) {
            groupedEquipmentUnit = unitsAtStation;
        } else {
            long count = Math.round(requiredSpeed / unitsAtStation.getEquipmentType().getHectaresPerHour());
            groupedEquipmentUnit = new GroupedEquipmentUnit(unitsAtStation.getEquipmentType(), count);
        }
        return groupedEquipmentUnit;
    }
    public List<List<GroupedEquipmentUnit>> offerEquipmentUnits() {
        List<EquipmentStation> equipmentStations = equipmentStationService.findAllSortedByDistance(order.getSectors().get(0).getRegion().getCenter());
        List<List<GroupedEquipmentUnit>> equipmentUnitsSets = new ArrayList<>();
        double requiredSpeed = getSectorsArea(order.getSectors()) / SLOW_WORKING_TIME;
        List<GroupedEquipmentUnit> equipmentUnitsAtStation = equipmentUnitService.getGroupedEquipmentByStationOrderByWorkingSpeed(equipmentStations.get(0));
        List<GroupedEquipmentUnit> offeringEquipmentUnit = new ArrayList<>();
        for (int i = 0; i < equipmentUnitsAtStation.size(); i++) {
            GroupedEquipmentUnit groupedEquipmentUnit = getOfferingUnits(equipmentUnitsAtStation.get(i), requiredSpeed);
            requiredSpeed -= groupedEquipmentUnit.getCount() * groupedEquipmentUnit.getEquipmentType().getHectaresPerHour();
            if (requiredSpeed <= 0) {
                equipmentUnitsSets.add(offeringEquipmentUnit);
                break;
            }
        }
        requiredSpeed = getSectorsArea(order.getSectors()) / FAST_WORKING_TIME;
        offeringEquipmentUnit = new ArrayList<>();

        for (int i = equipmentUnitsAtStation.size() - 1; i >= 0; i--) {
            GroupedEquipmentUnit groupedEquipmentUnit = getOfferingUnits(equipmentUnitsAtStation.get(i), requiredSpeed);
            requiredSpeed -= groupedEquipmentUnit.getCount() * groupedEquipmentUnit.getEquipmentType().getHectaresPerHour();
            if (requiredSpeed <= 0) {
                equipmentUnitsSets.add(offeringEquipmentUnit);
                break;
            }
        }
        return equipmentUnitsSets;
    }

    public List<GroupedEquipmentUnit> selectEquipmentUnits(List<GroupedEquipmentUnit> groupedEquipmentUnits) {
        PGpoint regionPosition = order.getSectors().get(0).getRegion().getCenter();
        List<EquipmentStation> equipmentStations = equipmentStationService.findAllSortedByDistance(regionPosition);
        List<EquipmentUnit> equipmentUnits = new ArrayList<>();
        double maxTime = 0d;
        double workingTime = getSectorsArea(order.getSectors()) / getGroupedUnitsWorkingSpeed(groupedEquipmentUnits) * 3600000;
        for (EquipmentStation equipmentStation : equipmentStations) {
            for (int i = 0; i < groupedEquipmentUnits.size(); i++) {
                double time = getDistance(equipmentStation.getPosition(), regionPosition) /
                        (groupedEquipmentUnits.get(i).getEquipmentType().getTransportationSpeed()) * 3600;
                maxTime = Double.max(maxTime, time);
                List<EquipmentUnit> unitsAtStation = equipmentUnitService.getByStationAndTypeAndDate(equipmentStation,
                        groupedEquipmentUnits.get(i).getEquipmentType(),
                        new Timestamp(order.getFulfillmentDate().getTime() - (long)time),
                        new Timestamp(order.getFulfillmentDate().getTime() + (long)(time + workingTime)));
                if (unitsAtStation.size() >= groupedEquipmentUnits.get(i).getCount()) {
                    equipmentUnits.addAll((int)(unitsAtStation.size() - groupedEquipmentUnits.get(i).getCount()), unitsAtStation);
                    groupedEquipmentUnits.get(i).setCount(0L);
                } else {
                    equipmentUnits.addAll(unitsAtStation);
                    groupedEquipmentUnits.get(i).setCount(groupedEquipmentUnits.get(i).getCount() - unitsAtStation.size());
                }
                if (groupedEquipmentUnits.get(i).getCount() == 0) {
                    groupedEquipmentUnits.remove(i);
                    i--;
                }
            }
        }
        if (groupedEquipmentUnits.size() > 0) {
            return groupedEquipmentUnits;
        }
        order.setStartDate(new Timestamp(order.getFulfillmentDate().getTime() - (long)maxTime));
        order.setFinishDate(new Timestamp(order.getFulfillmentDate().getTime() + (long)(maxTime + workingTime)));
        order.setEquipmentUnits(equipmentUnits);
        orderCreationStatus = OrderCreationStatus.PAYMENT;
        return null;
    }

    public void selectDate(Date date) {
        order.setFulfillmentDate(new Timestamp(date.getTime()));
        orderCreationStatus = OrderCreationStatus.DATE_SELECTION;
    }

    public void selectSeed(SeedType seedType) {
        order.setSeed(new SeedInOrder(seedType, getSectorsArea(order.getSectors()), order));
        orderCreationStatus = OrderCreationStatus.EQUIPMENT_SELECTION;
    }

    public void saveOrder() {
        orderService.add(order);
        order = new Order();
        orderCreationStatus = OrderCreationStatus.SECTOR_SELECTION;
    }

    public WorkingType getOfferingType() {
        List<WorkingType> offeringTypes = orderedTypeToOfferingTypes.get(order.getWorkingType());
        return offeringTypes.get(random.nextInt(offeringTypes.size()));
    }
}
