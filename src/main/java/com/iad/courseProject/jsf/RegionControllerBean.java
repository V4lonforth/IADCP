package com.iad.courseProject.jsf;

import com.iad.courseProject.authentication.TokenController;
import com.iad.courseProject.regions.RegionController;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named
@RequestScoped
public class RegionControllerBean {
    private String cadastralNumber;

    private final RegionController regionController;
    private TokenController tokenController;

    public String getCadastralNumber() {
        return cadastralNumber;
    }
    public void setCadastralNumber(String cadastralNumber) {
        this.cadastralNumber = cadastralNumber;
    }

    @Autowired
    public RegionControllerBean(RegionController regionController) {
        this.regionController = regionController;
        tokenController = new TokenController();
    }

    public String setRegionOwner() {
        String token = tokenController.resolveToken((HttpServletRequest) (FacesContext.getCurrentInstance().getExternalContext().getRequest()));
        return regionController.setOwner(tokenController.getUserId(token), cadastralNumber).toString();
    }
}
