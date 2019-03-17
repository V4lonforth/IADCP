package com.iad.courseProject.jsf.orderController;

import com.iad.courseProject.orders.OrderController;
import com.iad.courseProject.authentication.TokenController;
import com.iad.courseProject.data.entities.Region;
import com.iad.courseProject.data.entities.Sector;
import com.iad.courseProject.data.entities.User;
import com.iad.courseProject.data.entities.types.WorkingType;
import com.iad.courseProject.data.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Named
@ViewScoped
public class SectorsSelectionBean {
    private final OrderController orderController;
    private final UserService userService;
    private final TokenController tokenController;

    private List<Sector> chosenSectors;
    private Region chosenRegion;
    private WorkingType chosenWorkingType;

    private boolean confirmationRequired;

    public Region getChosenRegion() {
        return chosenRegion;
    }
    public void setChosenRegion(Region chosenRegion) {
        this.chosenRegion = chosenRegion;
    }

    public List<Sector> getChosenSectors() {
        return chosenSectors;
    }
    public void setChosenSectors(List<Sector> chosenSectors) {
        this.chosenSectors = chosenSectors;
    }

    public WorkingType getChosenWorkingType() {
        return chosenWorkingType;
    }
    public void setChosenWorkingType(WorkingType chosenWorkingType) {
        this.chosenWorkingType = chosenWorkingType;
    }

    public boolean isConfirmationRequired() {
        return confirmationRequired;
    }
    public void setConfirmationRequired(boolean confirmationRequired) {
        this.confirmationRequired = confirmationRequired;
    }

    public WorkingType[] getWorkingTypes() {
        return WorkingType.values();
    }

    @Autowired
    public SectorsSelectionBean(OrderController orderController, UserService userService) {
        this.orderController = orderController;
        this.userService = userService;
        tokenController = new TokenController();
        confirmationRequired = false;
    }

    public List<Region> getAvailableRegions() {
        String token = tokenController.resolveToken((HttpServletRequest) (FacesContext.getCurrentInstance().getExternalContext().getRequest()));
        User user = userService.getById(tokenController.getUserId(token));
        return user.getRegions();
    }

    public String selectWorkingType() {
        orderController.selectSectors(chosenSectors);
        if (orderController.checkOrderType(chosenWorkingType) == null) {
            return confirmWorkingType();
        } else {
            confirmationRequired = true;
            return "";
        }
    }
    public void cancelConfirmation() {
        confirmationRequired = false;
    }
    public String confirmWorkingType() {
        orderController.selectOrderType(chosenWorkingType);
        return "success";
    }
}
