/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package quickstore.converter;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import quickstore.controller.SubTipoController;
import quickstore.ejb.entity.SubTipo;
import quickstore.util.JSFutil;

/**
 * Converter del SubTipoController
 * @author jmferreira
 */
@FacesConverter(value = "SubTipoConverter")
public class SubTipoConverter implements Converter {

    /**
     * 
     * @param facesContext
     * @param component
     * @param value
     * @return
     */
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        //Debe comparar con ---opciones--- que se carga en el ListItem de JSFutil
        if (value == null || value.length() == 0 || value.compareTo(JSFutil.getMyBundle().getString("lblOpciones")) == 0) {
            return null;
        }
        SubTipoController controller = (SubTipoController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "SubTipoController");
        return controller.getSubTipoDAO().find(getKey(value));
    }

    java.lang.Integer getKey(String value) {
        java.lang.Integer key;
        key = Integer.valueOf(value);
        return key;
    }

    String getStringKey(java.lang.Integer value) {
        StringBuilder sb = new StringBuilder();
        sb.append(value);
        return sb.toString();
    }

    /**
     * 
     * @param facesContext
     * @param component
     * @param object
     * @return
     */
    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof String && object.toString().compareTo("------ Opciones ------") == 0) {
            return null;
        }
        if (object instanceof SubTipo) {
            SubTipo o = (SubTipo) object;
            return getStringKey(o.getIdSubTipo());
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + SubTipoController.class.getName());
        }
    }
}
