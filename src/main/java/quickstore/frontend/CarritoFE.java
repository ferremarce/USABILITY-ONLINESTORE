/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quickstore.frontend;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import quickstore.ejb.entity.Articulo;
import quickstore.ejb.entity.Carrito;
import quickstore.ejb.facade.CarritoDAO;
import quickstore.util.JSFutil;

/**
 *
 * @author root
 */
@Named(value = "CarritoFE")
@SessionScoped
public class CarritoFE implements Serializable {

    /**
     * Configuraciones varias para Log y Bundle*
     */
    private static final Logger LOG = Logger.getLogger(CarritoFE.class.getName());
    ResourceBundle bundle = ResourceBundle.getBundle("quickstore.properties.bundle", JSFutil.getmyLocale());

    @Inject
    private CarritoDAO carritoDAO;
    private Carrito carrito;
    private List<Articulo> listaArticuloCarrito;

    /**
     * Creates a new instance of CarritoController
     */
    public CarritoFE() {
    }
    //********************************************
    // SETTERS Y GETTERS
    //********************************************

    public List<Articulo> getListaArticuloCarrito() {
        return listaArticuloCarrito;
    }

    public void setListaArticuloCarrito(List<Articulo> listaArticuloCarrito) {
        this.listaArticuloCarrito = listaArticuloCarrito;
    }

    //********************************************
    // METODOS DE ACCIÓN
    //********************************************
    public void doAgregarCarrito(Articulo a) {
        this.listaArticuloCarrito.add(a);
    }

    @PostConstruct
    public void init() {
        this.listaArticuloCarrito = new ArrayList<>();
    }
    //********************************************
    // METODOS DEL LISTENER
    //********************************************

}
