package com.iad.courseProject.jsf;

import com.iad.courseProject.authentication.TokenController;
import com.iad.courseProject.data.entities.Region;
import com.iad.courseProject.data.entities.Sector;
import com.iad.courseProject.data.entities.User;
import com.iad.courseProject.data.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Named
@ViewScoped
public class SelectSectorsBean {
    private Region chosenRegion;
    private Long regionId;

    private List<Region> availableRegions;

    private List<Sector> selectedSectors;
    private List<Sector> availableSectors;

    private final TokenController tokenController;
    private final UserService userService;

    public List<Sector> getSelectedSectors() {
        return selectedSectors;
    }
    public void setSelectedSectors(List<Sector> selectedSectors) {
        this.selectedSectors = selectedSectors;
    }

    public List<Sector> getAvailableSectors() {
        return availableSectors;
    }
    public void setAvailableSectors(List<Sector> availableSectors) {
        this.availableSectors = availableSectors;
    }

    public List<Region> getUserRegions(HttpServletRequest request) {
        User user = userService.getById(tokenController.getUserId(tokenController.resolveToken(request)));
        availableRegions = user.getRegions();
        return availableRegions;
    }
    public List<Region> getAvailableRegions() {
        return availableRegions;
    }
    public void setAvailableRegions(List<Region> availableRegions) {
        this.availableRegions = availableRegions;
    }

    public Region getChosenRegion() {
        return chosenRegion;
    }
    public void setChosenRegion(Region chosenRegion) {
        this.chosenRegion = chosenRegion;
    }

    public Long getRegionId() {
        return regionId;
    }
    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    @Autowired
    public SelectSectorsBean(UserService userService) {
        this.tokenController = new TokenController();
        this.userService = userService;
    }

    public String createSector(Long regionId) {
        this.regionId = regionId;
        return "sectorCreation?faces-redirect=true&includeViewParams=true";
    }
}
