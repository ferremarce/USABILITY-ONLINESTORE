/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quickstore.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import quickstore.ejb.facade.TipoDAO;
import quickstore.util.JSFutil;

/**
 *
 * @author jmferreira1978@gmail.com
 */
@SessionScoped
@Named("TipoController")
public class TipoController implements Serializable {

    /**
     * Configuraciones varias para Log y Bundle*
     */
    private static final Logger LOG = Logger.getLogger(TipoController.class.getName());
    ResourceBundle bundle = ResourceBundle.getBundle("quickstore.properties.bundle", JSFutil.getmyLocale());

    @Inject
    TipoDAO tipoDAO;

    /**
     * Creates a new instance of TipoController
     */
    public TipoController() {
    }

    /**
     * getter tipoDAO
     *
     * @return
     */
    public TipoDAO getTipoDAO() {
        return tipoDAO;
    }

    /**
     * setter tipoDAO
     *
     * @param tipoDAO
     */
    public void setTipoDAO(TipoDAO tipoDAO) {
        this.tipoDAO = tipoDAO;
    }

    public SelectItem[] getTipoSet() {
        if (JSFutil.getUsuarioConectado().getIdRol().getIdRol().compareTo(4) == 0) { //Rol Tablero de Control
            List<Integer> inList = new ArrayList<>();
            inList.add(2);//Tipo de Adjunto Tablero
            return JSFutil.getSelectItems(tipoDAO.findAllIn("idTipo", inList), true);
        } else {
            return JSFutil.getSelectItems(tipoDAO.findAll(), true);
        }
    }

}
