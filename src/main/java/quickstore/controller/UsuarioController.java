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
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import quickstore.ejb.entity.Usuario;
import quickstore.ejb.facade.UsuarioDAO;
import quickstore.util.JSFutil;


/**
 *
 * @author jmferreira1978@gmail.com
 */
@SessionScoped
@Named("UsuarioController")
public class UsuarioController implements Serializable {

    /**
     * Configuraciones varias para Log y Bundle*
     */
    private static final Logger LOG = Logger.getLogger(UsuarioController.class.getName());
    ResourceBundle bundle = ResourceBundle.getBundle("quickstore.properties.bundle", JSFutil.getmyLocale());

    @EJB
    UsuarioDAO usuarioDAO;
    private List<Usuario> listaUsuario;
    private Usuario usuario;
    private String tmpPasswd;

    /**
     * Creates a new instance of UsuarioController
     */
    public UsuarioController() {
    }

    /**
     * getter tmpPasswd
     *
     * @return
     */
    public String getTmpPasswd() {
        return tmpPasswd;
    }

    /**
     * setter tmpPasswd
     *
     * @param tmpPasswd
     */
    public void setTmpPasswd(String tmpPasswd) {
        this.tmpPasswd = tmpPasswd;
    }

    /**
     * getter Usuario
     *
     * @return
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * setter Usuario
     *
     * @param usuario
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * getter ListaUsuario
     *
     * @return
     */
    public List<Usuario> getListaUsuario() {
        return listaUsuario;
    }

    /**
     * setter ListaUsuario
     *
     * @param listaUsuario
     */
    public void setListaUsuario(List<Usuario> listaUsuario) {
        this.listaUsuario = listaUsuario;
    }

    /**
     * Preparar el Formulario de Listado
     *
     * @return
     */
    public String doListarForm() {
        this.listaUsuario = usuarioDAO.findAll();
        return "/usuario/ListarUsuario";
    }

    /**
     * Preparar el Formulario de Visualización
     *
     * @param u
     * @return
     */
    public String doVerForm(Usuario u) {
        this.usuario = u;
        return "/usuario/VerUsuario";
    }

    /**
     * Preparar el Formulario de Edición
     *
     * @param u
     * @return
     */
    public String doEditarForm(Usuario u) {
        this.usuario = u;
        return "/usuario/CrearUsuario";
    }

    /**
     * Preparar el Formulario de Creacion
     *
     * @return
     */
    public String doCrearForm() {
        this.tmpPasswd = "";
        this.usuario = new Usuario();
        return "/usuario/CrearUsuario";
    }

    /**
     * Guardar un registro
     *
     * @return
     */
    public String doGuardar() {
        try {
            if (!this.tmpPasswd.isEmpty()) {
                usuario.setContrasenha(JSFutil.getSecurePassword(tmpPasswd));
            }
            usuarioDAO.update(usuario);
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
    public void doBorrar(Usuario u) {
        try {
            usuarioDAO.remove(u);
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

    public HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

    }
}
