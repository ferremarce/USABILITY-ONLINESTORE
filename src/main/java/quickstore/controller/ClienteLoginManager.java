/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quickstore.controller;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.inject.Inject;
import quickstore.ejb.entity.Cliente;
import quickstore.ejb.facade.ClienteDAO;
import quickstore.frontend.CarritoFE;
import quickstore.util.JSFutil;

/**
 *
 * @author root
 */
@Named(value = "ClienteLoginManager")
@SessionScoped
public class ClienteLoginManager implements Serializable {

    /**
     * Configuraciones varias para Log y Bundle*
     */
    private static final Logger LOG = Logger.getLogger(ClienteLoginManager.class.getName());
    ResourceBundle bundle = ResourceBundle.getBundle("quickstore.properties.bundle", JSFutil.getmyLocale());
    private final String USER_SESSION_KEY = "cliente";

    @Inject
    private ClienteDAO clienteDAO;
    @Inject
    private CarritoFE carritoFE;
    private String cuenta;
    private String contrasenha;
    private String contrasenha2;
    private Integer intento = 1;

    /**
     * Creates a new instance of ClienteController
     */
    public ClienteLoginManager() {
    }
    //********************************************
    // SETTERS Y GETTERS
    //********************************************

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getContrasenha() {
        return contrasenha;
    }

    public void setContrasenha(String contrasenha) {
        this.contrasenha = contrasenha;
    }

    public Integer getIntento() {
        return intento;
    }

    public void setIntento(Integer intento) {
        this.intento = intento;
    }

    public String getContrasenha2() {
        return contrasenha2;
    }

    public void setContrasenha2(String contrasenha2) {
        this.contrasenha2 = contrasenha2;
    }

    //********************************************
    // METODOS DE ACCIÓN
    //********************************************
    public String doLoginTienda() {
        try {
            this.carritoFE.setDireccionSeleccionada(null);
            Cliente c = clienteDAO.getCliente(this.cuenta);
            if (c != null) {
                if (JSFutil.checkSecurePassword(this.contrasenha, c.getContrasenha())) {
                    JSFutil.addSuccessMessage("Acceso concedido");
                    JSFutil.putSessionVariable(USER_SESSION_KEY, c);
                    return "";
                } else {
//                    this.intento++;
                    JSFutil.addErrorMessage("Acceso incorrecto!... credenciales no válidas.");
                    return "";
                }
            } else {
//                this.intento++;
                JSFutil.addErrorMessage("Acceso incorrecto!... Cliente '" + cuenta + "' no existe o se encuentra deshabilitado.");
                return "";
            }
        } catch (Exception e) {
//            this.intento++;
            JSFutil.addErrorMessage("Error inesperado: " + e.getMessage());
            return "";
        }
    }

    public String doLogoutTienda() {
        JSFutil.removeSessionVariable(this.USER_SESSION_KEY);
        JSFutil.addSuccessMessage("Ha cerrado exitosamente la sesión");
        return "/frontend/index2";
    }

    public Cliente getClienteLogeado() {
        return JSFutil.getClienteConectado();
    }

    public String doCambiarContrasenhaForm() {
        this.contrasenha = "";
        this.contrasenha2 = "";
        return "/frontend/registro/CambiarContrasenha";
    }

    public String doCambiarContrasenha() {
        if (this.getContrasenha().length() < 8) {
            JSFutil.addErrorMessage("Contraseña insegura. Debe proporcionar una contraseña de al menos 8 letras/numeros");
            return "";
        }
        if (this.getContrasenha().compareTo(this.contrasenha2) != 0) {
            JSFutil.addErrorMessage("Las contraseñas no coinciden. Por favor verifique y vuelva a intentar");
            return "";
        }

        try {
            Cliente u = JSFutil.getClienteConectado();
            u.setContrasenha(JSFutil.getSecurePassword(this.contrasenha));
            clienteDAO.update(u);
            JSFutil.addSuccessMessage("Contraseña cambiada exitosamente.");
        } catch (Exception e) {
            JSFutil.addErrorMessage(e, "Ocurrió un error de persistencia.");
        }
        return "";
    }

//********************************************
// METODOS DEL LISTENER
//********************************************
}
