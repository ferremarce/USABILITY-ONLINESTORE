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
import quickstore.ejb.entity.Preference;
import quickstore.ejb.facade.PreferenceDAO;
import quickstore.util.JSFutil;

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

    public PreferenceController() {
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

    public List<Preference> getListaPreference() {
        return listaPreference;
    }

    public void setListaPreference(List<Preference> listaPreference) {
        this.listaPreference = listaPreference;
    }

    //********************************************
    // METODOS DE ACCIÓN
    //********************************************
    //********************************************
    // METODOS DEL LISTENER
    //********************************************
}
