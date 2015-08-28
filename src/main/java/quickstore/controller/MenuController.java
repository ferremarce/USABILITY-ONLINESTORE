/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package quickstore.controller;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;
import quickstore.ejb.entity.Permiso;
import quickstore.ejb.entity.Usuario;
import quickstore.ejb.facade.UsuarioDAO;
import quickstore.util.JSFutil;

/**
 * Controlador para el manejo de menu
 *
 * @author jmferreira
 */
@SessionScoped
@Named(value = "MenuController")
public class MenuController implements Serializable {

    /**
     * Configuraciones varias para Log y Bundle*
     */
    private static final Logger LOG = Logger.getLogger(MenuController.class.getName());

    @Inject
    UsuarioDAO usuarioDAO;
    @Inject
    LanguageSwitcher languageSwitcher;
    private MenuModel model;

    /**
     * Metodo constructor del menu
     */
    public MenuController() {
    }

    @PostConstruct
    public void init() {
        this.montarMenu();
    }

    /**
     * Obtener el modelo que representa la estructura del Menu
     *
     * @return
     */
    public MenuModel getModel() {
        return model;
    }

    /**
     * Montar el menu desde la base de datos
     */
    public void montarMenu() {

        Usuario user = JSFutil.getUsuarioConectado();
        String nivel;
        model = new DefaultMenuModel();
        DefaultSubMenu submenu = new DefaultSubMenu();
        DefaultMenuItem item;
        try {
            if (user == null) {
                this.menuEjemplo();
                return;
            }
            List<Permiso> tr = usuarioDAO.getPermisoUsuario(user);
            for (Permiso x : tr) {
                nivel = x.getNivel();
                if (nivel.replaceAll("[^.]", "").length() == 0) { //cantidad de puntos que tiene la cadena
                    submenu = new DefaultSubMenu();
                    try {
                        submenu.setLabel(x.getNivel() + " " + JSFutil.getMyBundle().getString(x.getDescripcionPermiso()));
                        //System.out.println(JSFutil.getmyLocale()+" - "+this.bundle.getLocale()+" - "+this.bundle.getString(x.getDescripcionPermiso()));
                    } catch (Exception e) {
                        submenu.setLabel(x.getNivel() + " " + x.getDescripcionPermiso());
                    }
                    submenu.setIcon(x.getUrlImagen());
                    model.addElement(submenu);
                } else {
                    /*Agregar un item*/
                    item = new DefaultMenuItem();
                    try {
                        item.setValue(x.getNivel() + " " + JSFutil.getMyBundle().getString(x.getDescripcionPermiso()));
                    } catch (Exception e) {
                        item.setValue(x.getNivel() + " " + x.getDescripcionPermiso());
                    }
                    item.setIcon(x.getUrlImagen());
                    item.setAjax(false);
                    item.setCommand(x.getTagMenu());
                    submenu.addElement(item);
                }

            }

        } catch (Exception e) {
            System.out.println("ERROR AL OBTENER LOS PERMISOS DESDE LA BASE DE DATOS PARA MONTAR EL MENU");
            this.menuEjemplo();
        }
    }

    /**
     *
     */
    public void menuEjemplo() {
        model = new DefaultMenuModel();

        //First submenu
        DefaultSubMenu firstSubmenu = new DefaultSubMenu("Dynamic Submenu");

        DefaultMenuItem item = new DefaultMenuItem("External");
        item.setUrl("http://www.primefaces.org");
        item.setIcon("ui-icon-home");
        firstSubmenu.addElement(item);

        model.addElement(firstSubmenu);

        //Second submenu
        DefaultSubMenu secondSubmenu = new DefaultSubMenu("Dynamic Actions");

        item = new DefaultMenuItem("Save");
        item.setIcon("ui-icon-disk");
        item.setCommand("#{menuView.save}");
        secondSubmenu.addElement(item);

        item = new DefaultMenuItem("Usuario");
        item.setIcon("ui-icon-close");
        item.setCommand("#{UsuarioController.listarUsuarioForm}");
        secondSubmenu.addElement(item);

        item = new DefaultMenuItem("Redirect");
        item.setIcon("ui-icon-search");
        item.setCommand("#{menuView.redirect}");
        secondSubmenu.addElement(item);

        model.addElement(secondSubmenu);
    }
}
