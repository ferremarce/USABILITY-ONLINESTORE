/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quickstore.controller;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.inject.Inject;
import quickstore.ejb.entity.Cliente;
import quickstore.ejb.entity.Direccion;
import quickstore.ejb.entity.DireccionCliente;
import quickstore.ejb.entity.MetodoPagoCliente;
import quickstore.ejb.facade.ClienteDAO;
import quickstore.ejb.facade.DireccionClienteDAO;
import quickstore.ejb.facade.DireccionDAO;
import quickstore.ejb.facade.MetodoPagoClienteDAO;
import quickstore.util.JSFutil;
import quickstore.util.JSFutil.PersistAction;

/**
 *
 * @author root
 */
@Named(value = "ClienteController")
@SessionScoped
public class ClienteController implements Serializable {

    /**
     * Configuraciones varias para Log y Bundle*
     */
    private static final Logger LOG = Logger.getLogger(ClienteLoginManager.class.getName());
    ResourceBundle bundle = ResourceBundle.getBundle("quickstore.properties.bundle", JSFutil.getmyLocale());

    @Inject
    private ClienteDAO clienteDAO;
    @Inject
    private DireccionDAO direccionDAO;
    @Inject
    private DireccionClienteDAO direccionClienteDAO;
    @Inject
    private ClienteLoginManager clienteLoginManager;
    @Inject
    private MetodoPagoClienteDAO metodoPagoClienteDAO;

    private Cliente cliente;
    private List<Cliente> listaCliente;
    private Direccion direccion;
    private List<DireccionCliente> listaDireccionCliente;

    /**
     * Creates a new instance of ClienteController
     */
    public ClienteController() {
    }

    //********************************************
    // SETTERS Y GETTERS
    //********************************************
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    //********************************************
    // METODOS DE ACCIÓN
    //********************************************
    /**
     * Guardar un registro
     *
     * @return
     */
    public String doGuardar() {
        if (this.cliente.getIdCliente() != null) {
            persist(PersistAction.UPDATE);
        } else {
            persist(PersistAction.CREATE);
        }
        return "";
    }

    private void persist(PersistAction persistAction) {
        try {
            if (persistAction.compareTo(PersistAction.CREATE) == 0) {
                String plainPass = cliente.getContrasenha();
                cliente.setContrasenha(JSFutil.getSecurePassword(cliente.getContrasenha()));
                clienteDAO.create(cliente);
                if (direccion != null) {
                    direccionDAO.create(direccion);
                    DireccionCliente dc = new DireccionCliente();
                    dc.setIdCliente(cliente);
                    dc.setIdDireccion(direccion);
                    direccionClienteDAO.create(dc);
                    //Hacer login con la cuenta recientemente creada
                    clienteLoginManager.setCuenta(cliente.getEmail());
                    clienteLoginManager.setContrasenha(plainPass);
                    clienteLoginManager.doLoginTienda();
                }
            } else if (persistAction.compareTo(PersistAction.UPDATE) == 0) {
                Integer id = this.cliente.getIdCliente();
                clienteDAO.update(cliente);
            } else if (persistAction.compareTo(PersistAction.DELETE) == 0) {
                clienteDAO.remove(cliente);
            }
            JSFutil.addSuccessMessage(this.bundle.getString("UpdateSuccess"));
        } catch (EJBException ex) {
            String msg = "";
            Throwable cause = ex.getCause();
            if (cause != null) {
                msg = cause.getLocalizedMessage();
            }
            if (msg.length() > 0) {
                JSFutil.addErrorMessage(msg);
            } else {
                JSFutil.addErrorMessage(ex, this.bundle.getString("UpdateError"));
            }
        }
    }

    public List<DireccionCliente> doListaDireccionCliente() {
        if (JSFutil.getClienteConectado() != null) {
            return direccionClienteDAO.getAllDireccionCliente(JSFutil.getClienteConectado().getIdCliente());
        } else {
            return new ArrayList<>();
        }
    }
//********************************************
// METODOS DEL LISTENER
//********************************************

    public List<MetodoPagoCliente> doListaTarjetaCliente() {
        if (JSFutil.getClienteConectado() != null) {
            return metodoPagoClienteDAO.findAll();
        } else {
            return new ArrayList<>();
        }
    }
}
