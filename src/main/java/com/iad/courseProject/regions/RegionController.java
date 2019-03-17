package com.iad.courseProject.regions;

import com.iad.courseProject.data.entities.Region;
import com.iad.courseProject.data.services.RegionService;
import com.iad.courseProject.data.services.UserService;
import com.iad.courseProject.jsf.SetOwnerResult;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;

@Named
public class RegionController {
    private final RegionService regionService;
    private final UserService userService;

    @Autowired
    public RegionController(RegionService regionService, UserService userService) {
        this.regionService = regionService;
        this.userService = userService;
    }

    public SetOwnerResult setOwner(Long userId, String cadastralNumber) {
        Region region = regionService.findByCadastralNumber(cadastralNumber);
        if (region == null) {
            return SetOwnerResult.REGION_NOT_FOUND;
        }
        if (region.getUser() != null) {
            return SetOwnerResult.OWNER_EXISTS;
        }
        region.setUser(userService.getRef(userId));
        regionService.save(region);
        return SetOwnerResult.OK;
    }
    public void removeOwner(Region region) {
        region.setSectors(null);
        region.setUser(null);
        regionService.save(region);
    }
}
