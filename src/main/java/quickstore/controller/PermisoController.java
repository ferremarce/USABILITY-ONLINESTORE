/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quickstore.controller;

import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import quickstore.ejb.facade.PermisoDAO;

/**
 *
 * @author jmferreira1978@gmail.com
 */
@SessionScoped
@Named("PermisoController")
public class PermisoController implements Serializable {

    @Inject
    PermisoDAO permisoDAO;

    /**
     * Creates a new instance of PermisoController
     */
    public PermisoController() {
    }

    /**
     * getter PermisoDAO
     *
     * @return
     */
    public PermisoDAO getPermisoDAO() {
        return permisoDAO;
    }

    /**
     * setter PermisoDAO
     *
     * @param permisoDAO
     */
    public void setPermisoDAO(PermisoDAO permisoDAO) {
        this.permisoDAO = permisoDAO;
    }

}
