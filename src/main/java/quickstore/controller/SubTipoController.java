/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quickstore.controller;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import quickstore.ejb.entity.SubTipo;
import quickstore.ejb.entity.Tipo;
import quickstore.ejb.facade.SubTipoDAO;
import quickstore.util.JSFutil;

/**
 *
 * @author jmferreira1978@gmail.com
 */
@SessionScoped
@Named("SubTipoController")
public class SubTipoController implements Serializable {

    /**
     * Configuraciones varias para Log y Bundle*
     */
    private static final Logger LOG = Logger.getLogger(SubTipoController.class.getName());
    ResourceBundle bundle = ResourceBundle.getBundle("quickstore.properties.bundle", JSFutil.getmyLocale());

    @Inject
    SubTipoDAO subTipoDAO;
    private SubTipo subTipo;
    private Tipo tipo;
    private List<SubTipo> listaSubTipo;

    /**
     * Creates a new instance of SubTipoController
     */
    public SubTipoController() {
        tipo = new Tipo();
        subTipo = new SubTipo();
    }

    /**
     * getter tipo
     *
     * @return
     */
    public Tipo getTipo() {
        return tipo;
    }

    /**
     * setter tipo
     *
     * @param tipo
     */
    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    /**
     * getter subTipoDAO
     *
     * @return
     */
    public SubTipoDAO getSubTipoDAO() {
        return subTipoDAO;
    }

    /**
     * setter subTipoDAO
     *
     * @param subTipoDAO
     */
    public void setSubTipoDAO(SubTipoDAO subTipoDAO) {
        this.subTipoDAO = subTipoDAO;
    }

    /**
     * getter subTipo
     *
     * @return
     */
    public SubTipo getSubTipo() {
        return subTipo;
    }

    /**
     * setter subTipo
     *
     * @param subTipo
     */
    public void setSubTipo(SubTipo subTipo) {
        this.subTipo = subTipo;
    }

    /**
     * getter listaSubTipo
     *
     * @return
     */
    public List<SubTipo> getListaSubTipo() {
        return listaSubTipo;
    }

    /**
     * setter listaSubTipo
     *
     * @param listaSubTipo
     */
    public void setListaSubTipo(List<SubTipo> listaSubTipo) {
        this.listaSubTipo = listaSubTipo;
    }

    /**
     * Preparar el Formulario de Listado
     *
     * @return
     */
    public String doListarForm() {
        if (this.tipo != null) {
            this.listaSubTipo = subTipoDAO.findAllbyTipo(this.tipo.getIdTipo());
        }
        return "/backend/tipo/ListarSubTipo";
    }

    /**
     * Obtiene una coleccion de Categoria Articulo desde una lista con opcion de
     * seleccion
     *
     * @return
     */
    public SelectItem[] getIdCategoriaArticuloSet() {
        return JSFutil.getSelectItems(subTipoDAO.findAllbyTipo(1), Boolean.TRUE);
    }

    public List<SubTipo> getIdCategoriaArticuloList() {
        return subTipoDAO.findAllbyTipo(1);
    }

    public SelectItem[] getIdPaisSet() {
       return JSFutil.getSelectItems(subTipoDAO.findAllbyTipo(2), Boolean.TRUE);
    }

    /**
     * Actualizar DataTable de SubTipos conforme al tipo seleccionado
     */
    public void actualizarDataTable() {
        this.listaSubTipo = subTipoDAO.findAllbyTipo(this.tipo.getIdTipo());
        if (this.listaSubTipo.isEmpty()) {
            JSFutil.addErrorMessage("No hay resultados...");
        } else {
            JSFutil.addSuccessMessage(listaSubTipo.size() + " registros recuperados");
        }
    }

    /**
     * Guardar un registro
     *
     * @return
     */
    public String doGuardar() {
        if (this.subTipo.getDescripcionSubTipo().isEmpty()) {
            JSFutil.addErrorMessage("Debe especificar una descripción...");
            return "";
        }
        try {
            this.subTipo.setIdTipo(tipo);
            subTipoDAO.update(this.subTipo);
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
        this.subTipo = new SubTipo();
        return doListarForm();
    }

    /**
     * Actualizar el Subtipo despues de editar la celda
     *
     * @param st
     */
    public void onCellEdit(SubTipo st) {
        try {
            subTipoDAO.update(st);
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
        this.doListarForm();
    }

    public void doBorrar(Integer u) {
        try {
            subTipoDAO.remove(subTipoDAO.find(u));
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
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
            JSFutil.addErrorMessage(ex, JSFutil.getMyBundle().getString("UpdateError"));
        }
        doListarForm();
    }
}
