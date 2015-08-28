/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quickstore.controller;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.inject.Inject;
import quickstore.ejb.entity.DatosUsuario;
import quickstore.ejb.facade.DatosUsuarioDAO;
import quickstore.util.JSFutil;
import quickstore.util.JSFutil.PersistAction;

/**
 *
 * @author root
 */
@Named(value = "DatosUsuarioController")
@SessionScoped
public class DatosUsuarioController implements Serializable {

    /**
     * Configuraciones varias para Log y Bundle*
     */
    private static final Logger LOG = Logger.getLogger(DatosUsuarioController.class.getName());
    ResourceBundle bundle = ResourceBundle.getBundle("quickstore.properties.bundle", JSFutil.getmyLocale());

    @Inject
    private DatosUsuarioDAO datosUsuarioDAO;
    private DatosUsuario datosUsuario;
    private List<DatosUsuario> listaDatosUsuario;
    private String criterioBusqueda;
    private Boolean esInformatico;
    private Boolean estoyDeAcuerdo;

    /**
     * Creates a new instance of DatosUsuarioController
     */
    public DatosUsuarioController() {
    }

    public DatosUsuario getDatosUsuario() {
        return datosUsuario;
    }

    public void setDatosUsuario(DatosUsuario datosUsuario) {
        this.datosUsuario = datosUsuario;
    }

    public List<DatosUsuario> getListaDatosUsuario() {
        return listaDatosUsuario;
    }

    public void setListaDatosUsuario(List<DatosUsuario> listaDatosUsuario) {
        this.listaDatosUsuario = listaDatosUsuario;
    }

    public String getCriterioBusqueda() {
        return criterioBusqueda;
    }

    public void setCriterioBusqueda(String criterioBusqueda) {
        this.criterioBusqueda = criterioBusqueda;
    }

    public Boolean getEsInformatico() {
        return esInformatico;
    }

    public void setEsInformatico(Boolean esInformatico) {
        this.esInformatico = esInformatico;
    }

    public Boolean getEstoyDeAcuerdo() {
        return estoyDeAcuerdo;
    }

    public void setEstoyDeAcuerdo(Boolean estoyDeAcuerdo) {
        this.estoyDeAcuerdo = estoyDeAcuerdo;
    }

    //********************************************
    // METODOS DE ACCIÓN
    //********************************************
    /**
     * Crear un Registro
     *
     * @return
     */
    public String doCrearForm() {
        this.datosUsuario = new DatosUsuario();
        return "/backend/datosUsuario/RegistrarUsuario";
    }

    /**
     * Invoca al formulario de registro
     *
     * @return
     */
    public String doRegistrarForm() {
        this.esInformatico = Boolean.TRUE;
        this.estoyDeAcuerdo = Boolean.FALSE;
        this.datosUsuario = new DatosUsuario();
        return "/frontend/registro/RegistrarUsuario";
    }

    /**
     * Preparar el Formulario de Listado
     *
     * @return
     */
    public String doListarForm() {
        this.doListar();
        return "/backend/datosUsuario/ListarDatosUsuario";
    }

    /**
     * Preparar el Formulario de Visualización
     *
     * @param u
     * @return
     */
    public String doVerForm(DatosUsuario u) {
        this.datosUsuario = u;
        return "/frontend/registro/VerRegistro";
    }

    /**
     * Preparar el Formulario de Edición
     *
     * @param u
     * @return
     */
    public String doEditarForm(DatosUsuario u) {
        this.datosUsuario = u;
        return "/backend/datosUsuario/CrearDatosUsuario";
    }

    /**
     * Listar
     */
    public void doListar() {
        if (this.criterioBusqueda.length() > 0) {
            //this.listaDatosUsuario = datosUsuarioDAO.findAllbyCriterio(criterioBusqueda);
        } else {
            this.listaDatosUsuario = datosUsuarioDAO.findAll();
        }
        if (this.listaDatosUsuario.size() > 0) {
            JSFutil.addSuccessMessage(this.listaDatosUsuario.size() + " registro/s recuperado/s");
        } else {
            JSFutil.addSuccessMessage("Sin registros");
        }
    }

    /**
     * Guardar un registro
     *
     * @return
     */
    public String doGuardar() {
        if (this.datosUsuario.getIdDatosUsuario() != null) {
            persist(PersistAction.UPDATE);
        } else {
            persist(PersistAction.CREATE);
        }
        return doListarForm();
    }

    public String doGuardarRegistro() {
        persist(PersistAction.CREATE);
        Integer id = this.datosUsuario.getIdDatosUsuario();
        if (id != null) {
            return this.doVerForm(datosUsuario);
        } else {
            JSFutil.addErrorMessage("Es probable que correo especificado ya haya sido registrado por otro usuario");
            return "";
        }
    }

    private void persist(PersistAction persistAction) {
        try {
            if (persistAction.compareTo(PersistAction.CREATE) == 0) {
                datosUsuarioDAO.create(datosUsuario);
            } else if (persistAction.compareTo(PersistAction.UPDATE) == 0) {
                datosUsuarioDAO.update(datosUsuario);
                //Borramos todos sus adjuntos
            } else if (persistAction.compareTo(PersistAction.DELETE) == 0) {
                datosUsuarioDAO.remove(datosUsuario);
            }
            JSFutil.addSuccessMessage(JSFutil.getMyBundle().getString("UpdateSuccess"));
        } catch (EJBException ex) {
            String msg = "";
            Throwable cause = ex.getCause();
            if (cause != null) {
                msg = cause.getLocalizedMessage();
            }
            if (msg.length() > 0) {
                JSFutil.addErrorMessage(msg);
            } else {
                JSFutil.addErrorMessage(ex, JSFutil.getMyBundle().getString("UpdateError"));
            }
        }
    }

    /**
     * Borrar un registro
     *
     * @param u
     */
    public void doBorrar(DatosUsuario u) {
        this.datosUsuario = u;
        persist(PersistAction.DELETE);
        doListarForm();
    }
    //********************************************
    // METODOS DE LISTENER
    //********************************************

    public Integer calcularEdad() {
        Calendar cal = Calendar.getInstance();
        if (this.datosUsuario.getFechaNacimiento() != null) {
            cal.setTime(this.datosUsuario.getFechaNacimiento());
            return JSFutil.calcularEdad(cal);
        } else {
            return 0;
        }
    }

    public String verificarCorreoExistente() {
        if (datosUsuarioDAO.findAllbyEmail(this.datosUsuario.getEmail()).size() > 0) {
            String msg = " El mail ya ha sido registrado";
            return msg;
        } else {
            return "";
        }
    }

}
