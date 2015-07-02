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
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.primefaces.event.FlowEvent;
import quickstore.ejb.entity.Articulo;
import quickstore.ejb.entity.Carrito;
import quickstore.ejb.entity.OrdenCarrito;
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
    private List<OrdenCarrito> listaOrdenCarrito;
    private Boolean aceptaPolitica;

    /**
     * Creates a new instance of CarritoController
     */
    public CarritoFE() {
    }
    //********************************************
    // SETTERS Y GETTERS
    //********************************************

    public List<OrdenCarrito> getListaOrdenCarrito() {
        return listaOrdenCarrito;
    }

    public void setListaOrdenCarrito(List<OrdenCarrito> listaOrdenCarrito) {
        this.listaOrdenCarrito = listaOrdenCarrito;
    }

    public Boolean getAceptaPolitica() {
        return aceptaPolitica;
    }

    public void setAceptaPolitica(Boolean aceptaPolitica) {
        this.aceptaPolitica = aceptaPolitica;
    }

    //********************************************
    // METODOS DE ACCIÓN
    //********************************************
    public void doAgregarCarrito(Articulo a) {
        OrdenCarrito ocarrito = new OrdenCarrito();
        ocarrito.setCantidad(1);
        ocarrito.setIdArticulo(a);
        Boolean existe = Boolean.FALSE;
        for (int i = 0; i < this.listaOrdenCarrito.size(); i++) {

            if (this.listaOrdenCarrito.get(i).getIdArticulo().getIdArticulo().compareTo(a.getIdArticulo()) == 0) {
                this.listaOrdenCarrito.get(i).setCantidad(this.listaOrdenCarrito.get(i).getCantidad() + 1);
                existe = Boolean.TRUE;
            }
        }
        if (!existe) {
            this.listaOrdenCarrito.add(ocarrito);
        }
        JSFutil.addSuccessMessage("Artículo agregado exitosamente al Carrito");
    }

    public String doProcesarCarritoFrom() {
        this.aceptaPolitica = null;
        return "/frontend/carrito/ProcesarCarrito";
    }

    @PostConstruct
    public void init() {
        if (this.listaOrdenCarrito == null) {
            this.listaOrdenCarrito = new ArrayList<>();
        }
    }
    //********************************************
    // METODOS DEL LISTENER
    //********************************************

    public Float calcularTotal() {
        Float total = Float.valueOf("0");
        for (OrdenCarrito oc : this.listaOrdenCarrito) {
            total += oc.getCantidad() * this.calcularPrecioFinal(oc.getIdArticulo());
        }
        return total;
    }

    public Float calcularPrecioFinal(Articulo a) {
        if (a.getEsPromocion()) {
            return a.getPrecio() - a.getPrecio() * a.getPorcentajeDescuento() / 100;
        } else {
            return a.getPrecio();
        }
    }

    public void doSacarArticuloOrdenCompra(OrdenCarrito orden) {
        boolean remove = this.listaOrdenCarrito.remove(orden);
        if (remove) {
            JSFutil.addSuccessMessage(this.bundle.getString("UpdateSuccess"));
        } else {
            JSFutil.addSuccessMessage(this.bundle.getString("UpdateError"));
        }
    }

    public String onFlowProcess(FlowEvent event) {
        switch (event.getOldStep().toLowerCase()) {
            case "carrito":
                if (this.listaOrdenCarrito.size() > 0) {
                    return event.getNewStep();
                } else {
                    JSFutil.addErrorMessage("El Carrito está vacío");
                    return "carrito";
                }
            case "entrega":
                if (this.aceptaPolitica.compareTo(Boolean.FALSE)==0 && event.getNewStep().toLowerCase().compareTo("pago")==0){
                    JSFutil.addErrorMessage("Debe aceptar las políticas de privacidad");
                    return "entrega";
                }else{
                    return event.getNewStep();
                }
            case "pago":
                    return event.getNewStep();
            default:
                JSFutil.addErrorMessage("El Asistente no se ha inicializado correctamente");
                return "";
        }
    }
}
