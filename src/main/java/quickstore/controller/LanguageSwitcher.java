/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quickstore.controller;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import quickstore.util.JSFutil;

/**
 *
 * @author root
 */
@Named(value = "LanguageSwitcher")
@SessionScoped
public class LanguageSwitcher implements Serializable {

    private static final Logger LOG = Logger.getLogger(ArticuloController.class.getName());
    ResourceBundle bundle = ResourceBundle.getBundle("quickstore.properties.bundle", JSFutil.getmyLocale());

    private Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();

    /**
     * Creates a new instance of LanguageController
     */
    public LanguageSwitcher() {
    }

    public void changeLanguage(String language) {
//        FacesContext context = FacesContext.getCurrentInstance();
//        String language = context.getExternalContext().getRequestParameterMap().get("idioma");
        locale = new Locale(language);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }
}
