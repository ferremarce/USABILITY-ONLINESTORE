/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quickstore.login;

import java.io.Serializable;
import java.util.TimeZone;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import quickstore.ejb.entity.Usuario;
import quickstore.ejb.facade.UsuarioDAO;
import quickstore.util.JSFutil;


/**
 *
 * @author jmferreira1978@gmail.com
 */
@SessionScoped
@Named("LoginManager")
public class LoginManager implements Serializable {

    @Inject
    UsuarioDAO usuarioDAO;
    private final String USER_SESSION_KEY = "user";
    private String cuenta;
    private String contrasenha;
    private String contrasenha2;
    private Integer intento = 1;

    /**
     * Creates a new instance of LoginManager
     */
    public LoginManager() {
    }

    public Integer getIntento() {
        return intento;
    }

    public void setIntento(Integer intento) {
        this.intento = intento;
    }

    /**
     * getter Cuenta
     *
     * @return
     */
    public String getCuenta() {
        return cuenta;
    }

    /**
     * setter Cuenta
     *
     * @param cuenta
     */
    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    /**
     * getter Contrasenha
     *
     * @return
     */
    public String getContrasenha() {
        return contrasenha;
    }

    /**
     * setter Contrasenha
     *
     * @param contrasenha
     */
    public void setContrasenha(String contrasenha) {
        this.contrasenha = contrasenha;
    }

    /**
     * getter contrasenha2
     *
     * @return
     */
    public String getContrasenha2() {
        return contrasenha2;
    }

    /**
     * setter contrasenha2
     *
     * @param contrasenha2
     */
    public void setContrasenha2(String contrasenha2) {
        this.contrasenha2 = contrasenha2;
    }

    /**
     * Ejecutar la acción de login
     *
     * @return
     */
    public String doLogin() {
        try {
            Usuario usuario = usuarioDAO.getUsuario(cuenta);
            if (usuario != null && usuario.getEsActivo() && this.puedeLogearse(usuario)) {
                if (JSFutil.checkSecurePassword(this.contrasenha, usuario.getContrasenha())) {
                    JSFutil.addSuccessMessage("Acceso concedido");
                    JSFutil.putSessionVariable(USER_SESSION_KEY, usuario);
                    return "index";
                } else {
                    this.intento++;
                    JSFutil.addErrorMessage("Acceso incorrecto!... credenciales no válidas.");
                    return null;
                }
            } else {
                this.intento++;
                JSFutil.addErrorMessage("Acceso incorrecto!... Usuario '" + cuenta + "' no existe o se encuentra deshabilitado.");
                return null;
            }
        } catch (Exception e) {
            this.intento++;
            return null;
        }
    }

    private Boolean puedeLogearse(Usuario u) {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String ip = JSFutil.getClientIpAddr(request);
        if (u.getLoginExterno()) {
            return Boolean.TRUE;
        }
        if (JSFutil.esValidoIPIntranet(ip)) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }

    }

    /**
     * Invalidar la sesión y hacer logout
     *
     * @return
     */
    public String doLogout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "/login";
    }

    /**
     * getter del usuario conectado actualmente
     *
     * @return
     */
    public Usuario getUsuarioLogeado() {
        return JSFutil.getUsuarioConectado();
    }

    public String doCambiarContrasenhaForm() {
        this.contrasenha = "";
        this.contrasenha2 = "";
        return "/usuario/CambiarContrasenha";
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
            Usuario u = JSFutil.getUsuarioConectado();
            u.setContrasenha(JSFutil.getSecurePassword(this.contrasenha));
            usuarioDAO.update(u);
            JSFutil.addSuccessMessage("Contraseña cambiada exitosamente.");
        } catch (Exception e) {
            JSFutil.addErrorMessage(e, "Ocurrió un error de persistencia.");
        }
        return "";
    }
    
     /**
     * getter timeZone
     *
     * @return
     */
    public TimeZone getMyTimeZone() {
        return JSFutil.getMyTimeZone();
    }
}
