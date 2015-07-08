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
import quickstore.ejb.facade.ClienteDAO;
import quickstore.ejb.facade.DireccionClienteDAO;
import quickstore.ejb.facade.DireccionDAO;
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
    private final String USER_SESSION_KEY = "cliente";

    @Inject
    private ClienteDAO clienteDAO;
    @Inject
    private DireccionDAO direccionDAO;
    @Inject
    private DireccionClienteDAO direccionClienteDAO;
    @Inject
    private ClienteLoginManager clienteLoginManager;
    private Cliente cliente;
    private List<Cliente> listaCliente;
    private Boolean estoyDeAcuerdo;
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

    public Boolean getEstoyDeAcuerdo() {
        return estoyDeAcuerdo;
    }

    public void setEstoyDeAcuerdo(Boolean estoyDeAcuerdo) {
        this.estoyDeAcuerdo = estoyDeAcuerdo;
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
    public String verificarCorreoExistente() {
        if (clienteDAO.getCliente(this.cliente.getEmail()) != null) {
            String msg = " El mail ya ha sido registrado";
            return msg;
        } else {
            return "";
        }
    }

    public String doProcesarDatosEntrega() {
        if (JSFutil.getClienteConectado() == null) {
            this.cliente = new Cliente();
            this.direccion = new Direccion();
        } else {
            this.cliente = JSFutil.getClienteConectado();
        }
        return "/frontend/carrito/ProcesarDatosEntrega";
    }

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

    public void doGuardarDireccion() {
        try {
            direccionDAO.create(direccion);
            DireccionCliente dc = new DireccionCliente();
            dc.setIdCliente(JSFutil.getClienteConectado());
            dc.setIdDireccion(direccion);
            direccionClienteDAO.create(dc);
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
//********************************************
// METODOS DEL LISTENER
//********************************************

    public List<DireccionCliente> doListaDireccionCliente() {
        if (JSFutil.getClienteConectado() != null) {
            return direccionClienteDAO.getAllDireccionCliente(JSFutil.getClienteConectado().getIdCliente());
        } else {
            return new ArrayList<>();
        }
    }
}
