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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import org.primefaces.model.DualListModel;
import quickstore.ejb.entity.Permiso;
import quickstore.ejb.entity.PermisoRol;
import quickstore.ejb.entity.Rol;
import quickstore.ejb.facade.PermisoDAO;
import quickstore.ejb.facade.PermisoRolDAO;
import quickstore.ejb.facade.RolDAO;
import quickstore.util.JSFutil;

/**
 *
 * @author jmferreira1978@gmail.com
 */
@SessionScoped
@Named("RolController")
public class RolController implements Serializable {

    /**
     * Configuraciones varias para Log y Bundle*
     */
    private static final Logger LOG = Logger.getLogger(RolController.class.getName());
    ResourceBundle bundle = ResourceBundle.getBundle("quickstore.properties.bundle", JSFutil.getmyLocale());
    
    @Inject
    RolDAO rolDAO;
    @Inject
    PermisoDAO permisoDAO;
    @Inject
    PermisoRolDAO permisoRolDAO;
    private List<Rol> listaRol;
    private DualListModel<Permiso> dualPermiso;
    private Rol rol;

    /**
     * Creates a new instance of RolController
     */
    public RolController() {
    }

    /**
     * getter rolDAO
     *
     * @return
     */
    public RolDAO getRolDAO() {
        return rolDAO;
    }

    /**
     * setter rolDAO
     *
     * @param rolDAO
     */
    public void setRolDAO(RolDAO rolDAO) {
        this.rolDAO = rolDAO;
    }

    /**
     * getter Rol
     *
     * @return
     */
    public Rol getRol() {
        return rol;
    }

    /**
     * setter Rol
     *
     * @param rol
     */
    public void setRol(Rol rol) {
        this.rol = rol;
    }

    /**
     * getter ListaRol
     *
     * @return
     */
    public List<Rol> getListaRol() {
        return listaRol;
    }

    /**
     * setter ListaRol
     *
     * @param listaRol
     */
    public void setListaRol(List<Rol> listaRol) {
        this.listaRol = listaRol;
    }

    /**
     * getter dualPermiso
     *
     * @return
     */
    public DualListModel<Permiso> getDualPermiso() {
        return dualPermiso;
    }

    /**
     * setter dualPermiso
     *
     * @param dualPermiso
     */
    public void setDualPermiso(DualListModel<Permiso> dualPermiso) {
        this.dualPermiso = dualPermiso;
    }

    /**
     * Preparar el Formulario de Listado de Rols
     *
     * @return
     */
    public String doListarForm() {
        this.listaRol = rolDAO.findAll();
        return "/backend/rol/ListarRol";
    }

    /**
     * Preparar el Formulario de Visualización de Rols
     *
     * @param u
     * @return
     */
    public String doVerForm(Rol u) {
        this.rol = u;
        return "/backend/rol/VerRol";
    }

    /**
     * Preparar el Formulario de Edición de Rols
     *
     * @param u
     * @return
     */
    public String doEditarForm(Rol u) {
        this.rol = u;
        return "/backend/rol/CrearRol";
    }

    /**
     * Crear un Registro de Rol
     *
     * @return
     */
    public String doCrearForm() {
        this.rol = new Rol();
        return "/backend/rol/CrearRol";
    }

    /**
     * Preparar el formulario de permisos por rol
     *
     * @param r
     * @return
     */
    public String doVerPermisoForm(Rol r) {
        this.rol = r;
        this.doCargarPermisos(r);
        return "/backend/rol/verPermisoRol";
    }

    /**
     * Guardar un registro
     *
     * @return
     */
    public String doGuardar() {
        try {
            rolDAO.update(rol);
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
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
            JSFutil.addErrorMessage(ex, this.bundle.getString("UpdateError"));
        }
        return doListarForm();
        
    }

    /**
     * Borrar un registro
     *
     * @param u
     */
    public void doBorrar(Rol u) {
        try {
            rolDAO.remove(u);
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
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
            JSFutil.addErrorMessage(ex, this.bundle.getString("UpdateError"));
        }
        doListarForm();
    }

    /**
     * Cargar los permisos de un Rol en una Lista Dual
     *
     * @param r
     */
    public void doCargarPermisos(Rol r) {
        List<Permiso> source;
        List<Permiso> target = new ArrayList<>();
        source = permisoDAO.findAllSorted("nivel", "ASC");
        for (Permiso p : rolDAO.getPermisoRol(r)) {
            target.add(p);
            for (int i = 0; i < source.size(); i++) {
                if (source.get(i).getIdPermiso().compareTo(p.getIdPermiso()) == 0) {
                    source.remove(i);
                    break;
                }
            }
        }
        this.dualPermiso = new DualListModel<>(source, target);
    }

    /**
     * Actualiza los Permisos de un Rol
     *
     * @return
     */
    public String doUpdatePermiso() {
        try {
            rolDAO.borrarTodoPermisoRol(this.rol.getIdRol());
            for (int i = 0; i < this.dualPermiso.getTarget().size(); i++) {
                PermisoRol pr = new PermisoRol();
                pr.setFechaAsignacion(JSFutil.getFechaHoraActual());
                pr.setIdPermiso(dualPermiso.getTarget().get(i));
                pr.setIdRol(rol);
                permisoRolDAO.create(pr);
            }
            JSFutil.addSuccessMessage(this.bundle.getString("UpdateSuccess"));
        } catch (Exception e) {
            JSFutil.addErrorMessage(e, this.bundle.getString("UpdateError"));
        }
        return "/backend/rol/ListarRol";
    }

    /**
     * Obtiene una coleccion de Rol desde una lista
     *
     * @param habilitado
     * @return
     */
    public SelectItem[] getRolItemsAvailableSelectOne(boolean habilitado) {
        return JSFutil.getSelectItems(rolDAO.findAll(), habilitado);
    }

    /**
     * Obtiene una coleccion de Rol desde una lista con opcion de seleccion
     *
     * @return
     */
    public SelectItem[] getRolSet() {
        return this.getRolItemsAvailableSelectOne(true);
    }

    public SelectItem[] getRolDiarioSesionesSet() {
        return JSFutil.getSelectItems(rolDAO.getRolDiarioSesiones(Boolean.TRUE), Boolean.TRUE);
    }
    
}
