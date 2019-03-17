package com.iad.courseProject.jsf.orderController;

import com.iad.courseProject.GroupedEquipmentUnit;
import com.iad.courseProject.data.entities.SeedInOrder;
import com.iad.courseProject.data.entities.SeedType;
import com.iad.courseProject.data.services.SeedTypeService;
import com.iad.courseProject.orders.OrderController;
import com.iad.courseProject.data.entities.EquipmentType;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Named
@ViewScoped
public class EquipmentSelectionBean {
    private final OrderController orderController;
    private final SeedTypeService seedTypeService;

    private List<GroupedEquipmentUnit> chosenEquipment;
    private Date chosenDate;
    private SeedType chosenSeed;

    private BigDecimal getPrice() {
        return orderController.getWorkingPrice();
    }

    public List<GroupedEquipmentUnit> getChosenEquipment() {
        return chosenEquipment;
    }
    public void setChosenEquipment(List<GroupedEquipmentUnit> chosenEquipment) {
        this.chosenEquipment = chosenEquipment;
    }

    public Date getChosenDate() {
        return chosenDate;
    }
    public void setChosenDate(Date chosenDate) {
        this.chosenDate = chosenDate;
    }

    public SeedType getChosenSeed() {
        return chosenSeed;
    }
    public void setChosenSeed(SeedType chosenSeed) {
        this.chosenSeed = chosenSeed;
    }

    public List<SeedType> getAvailableSeedTypes() {
        return seedTypeService.getAll();
    }
    public BigDecimal getSeedPrice() {
        return orderController.getSeedPrice(chosenSeed);
    }

    public List<EquipmentType> getEquipmentTypes() {
        return orderController.findEquipmentTypes();
    }
    public List<List<GroupedEquipmentUnit>> getOfferingUnitSets() {
        return orderController.offerEquipmentUnits();
    }

    @Autowired
    public EquipmentSelectionBean(OrderController orderController, SeedTypeService seedTypeService) {
        this.orderController = orderController;
        this.seedTypeService = seedTypeService;
    }

    public String setOrderInfo() {
        orderController.selectDate(chosenDate);
        orderController.selectSeed(chosenSeed);
        List<GroupedEquipmentUnit> notFoundEquipment = orderController.selectEquipmentUnits(chosenEquipment);
        if (notFoundEquipment == null) {
            return "success";
        } else {
            return "error";
        }
    }
}
