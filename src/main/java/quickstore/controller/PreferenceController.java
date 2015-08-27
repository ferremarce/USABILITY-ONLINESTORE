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
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.inject.Inject;
import quickstore.ejb.entity.Preference;
import quickstore.ejb.entity.Usuario;
import quickstore.ejb.facade.PreferenceDAO;
import quickstore.util.JSFutil;
import quickstore.util.JSFutil.PersistAction;

/**
 *
 * @author root
 */
@Named(value = "PreferenceController")
@SessionScoped
public class PreferenceController implements Serializable {

    /**
     * Configuraciones varias para Log y Bundle*
     */
    private static final Logger LOG = Logger.getLogger(PreferenceController.class.getName());
    ResourceBundle bundle = ResourceBundle.getBundle("quickstore.properties.bundle", JSFutil.getmyLocale());

    @Inject
    private PreferenceDAO preferenceDAO;
    private Preference preference;
    private List<Preference> listaPreference;
    private Map<String, String> themes;
    private Map<String, String> sizes;

    public PreferenceController() {
        themes = new TreeMap<>();
        themes.put("Aristo", "aristo");
        themes.put("Black-Tie", "black-tie");
        themes.put("Blitzer", "blitzer");
        themes.put("Bluesky", "bluesky");
        themes.put("Casablanca", "casablanca");
        themes.put("Cupertino", "cupertino");
        themes.put("Dark-Hive", "dark-hive");
        themes.put("Dot-Luv", "dot-luv");
        themes.put("Eggplant", "eggplant");
        themes.put("Excite-Bike", "excite-bike");
        themes.put("Flick", "flick");
        themes.put("Glass-X", "glass-x");
        themes.put("Hot-Sneaks", "hot-sneaks");
        themes.put("Humanity", "humanity");
        themes.put("Le-Frog", "le-frog");
        themes.put("Midnight", "midnight");
        themes.put("Mint-Choc", "mint-choc");
        themes.put("Overcast", "overcast");
        themes.put("Pepper-Grinder", "pepper-grinder");
        themes.put("Redmond", "redmond");
        themes.put("Rocket", "rocket");
        themes.put("Sam", "sam");
        themes.put("Smoothness", "smoothness");
        themes.put("South-Street", "south-street");
        themes.put("Start", "start");
        themes.put("Sunny", "sunny");
        themes.put("Swanky-Purse", "swanky-purse");
        themes.put("Trontastic", "trontastic");
        themes.put("UI-Darkness", "ui-darkness");
        themes.put("UI-Lightness", "ui-lightness");
        themes.put("Vader", "vader");
        sizes = new TreeMap<>();
        sizes.put("8px", "8px");
        sizes.put("9px", "9px");
        sizes.put("10px", "10px");
        sizes.put("11px", "11px");
        sizes.put("12px", "12px");
        sizes.put("13px", "13px");
        sizes.put("14px", "14px");
        sizes.put("15px", "15px");
        sizes.put("16px", "16px");
        sizes.put("17px", "17px");
        sizes.put("18px", "18px");

    }
    //********************************************
    // SETTERS Y GETTERS
    //********************************************

    public Preference getPreference() {
        return preference;
    }

    public void setPreference(Preference preference) {
        this.preference = preference;
    }

    public Map<String, String> getThemes() {
        return themes;
    }

    public void setThemes(Map<String, String> themes) {
        this.themes = themes;
    }

    public List<Preference> getListaPreference() {
        return listaPreference;
    }

    public Map<String, String> getSizes() {
        return sizes;
    }

    public void setSizes(Map<String, String> sizes) {
        this.sizes = sizes;
    }

    public void setListaPreference(List<Preference> listaPreference) {
        this.listaPreference = listaPreference;
    }

    //********************************************
    // METODOS DE ACCIÓN
    //********************************************
    public String doCrearForm() {
        this.preference = new Preference();
        return "/backend/preference/CrearPreference";
    }

    public String doListarForm() {
        this.listaPreference = preferenceDAO.findAll();
        return "/backend/preference/ListarPreference";
    }

    public String doGuardar() {
        if (this.preference.getIdPreference() != null) {
            persist(PersistAction.UPDATE);
        } else {
            persist(PersistAction.CREATE);
        }
        return this.doListarForm();
    }

    private void persist(PersistAction persistAction) {
        try {
            if (persistAction.compareTo(PersistAction.CREATE) == 0) {

                preferenceDAO.create(this.preference);
            } else if (persistAction.compareTo(PersistAction.UPDATE) == 0) {
                Integer id = this.preference.getIdPreference();
                preferenceDAO.update(this.preference);
            } else if (persistAction.compareTo(PersistAction.DELETE) == 0) {
                preferenceDAO.remove(this.preference);
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

    public String doEditarForm(Preference u) {
        this.preference = preferenceDAO.find(u.getIdPreference());
        return "/backend/preference/CrearPreference";
    }

    public void doBorrar(Preference u) {
        this.preference = u;
        persist(PersistAction.DELETE);
        doListarForm();
    }

    public String obtenerTema() {
        try {
            Preference p = preferenceDAO.find(JSFutil.getUsuarioConectado().getIdPreference().getIdPreference());
            if (p == null) {
                return "aristo";
            } else {
                return p.getTema();
            }
        } catch (Exception ex) {
            Logger.getLogger(PreferenceController.class.getName()).log(Level.SEVERE, null, ex);
            return "aristo";
        }
    }
    //********************************************
    // METODOS DEL LISTENER
    //********************************************
}
