/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quickstore.frontend;

import quickstore.controller.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Inject;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import quickstore.ejb.entity.Articulo;
import quickstore.ejb.entity.ArticuloAdjunto;
import quickstore.ejb.facade.ArticuloAdjuntoDAO;
import quickstore.ejb.facade.ArticuloDAO;
import quickstore.util.JSFutil;

/**
 *
 * @author root
 */
@Named(value = "ArticuloFE")
@SessionScoped
public class ArticuloFE implements Serializable {

    /**
     * Configuraciones varias para Log y Bundle*
     */
    private static final Logger LOG = Logger.getLogger(UsuarioController.class.getName());
    ResourceBundle bundle = ResourceBundle.getBundle("quickstore.properties.bundle", JSFutil.getmyLocale());

    @Inject
    private ArticuloDAO articuloDAO;
    @Inject
    private ArticuloAdjuntoDAO articuloAdjuntoDAO;
    private Articulo articulo;
    private List<Articulo> listaArticulo;
    private List<Articulo> listaArticuloFiltrado;
    private List<ArticuloAdjunto> listaAdjuntoArticulo;
    private String criterioBusqueda;
    @Inject
    private ClickCounter clickCounter;

    /**
     * Creates a new instance of ArticuloController
     */
    public ArticuloFE() {
        this.criterioBusqueda = "";
    }

    //********************************************
    // SETTERS Y GETTERS
    //********************************************
    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public List<Articulo> getListaArticulo() {
        return listaArticulo;
    }

    public void setListaArticulo(List<Articulo> listaArticulo) {
        this.listaArticulo = listaArticulo;
    }

    public List<ArticuloAdjunto> getListaAdjuntoArticulo() {
        return listaAdjuntoArticulo;
    }

    public void setListaAdjuntoArticulo(List<ArticuloAdjunto> listaAdjuntoArticulo) {
        this.listaAdjuntoArticulo = listaAdjuntoArticulo;
    }

    public List<Articulo> getListaArticuloFiltrado() {
        return listaArticuloFiltrado;
    }

    public void setListaArticuloFiltrado(List<Articulo> listaArticuloFiltrado) {
        this.listaArticuloFiltrado = listaArticuloFiltrado;
    }

    public String getCriterioBusqueda() {
        return criterioBusqueda;
    }

    public void setCriterioBusqueda(String criterioBusqueda) {
        this.criterioBusqueda = criterioBusqueda;
    }

    public ClickCounter getClickCounter() {
        return clickCounter;
    }

    public void setClickCounter(ClickCounter clickCounter) {
        this.clickCounter = clickCounter;
    }

    public void init() {
        this.listaArticulo = articuloDAO.findAll();
    }

    //********************************************
    // METODOS DE ACCIÓN
    //********************************************
    /**
     * Listar
     * @return 
     */
    public String doListar() {
        if (this.criterioBusqueda.length() > 0) {
            this.listaArticulo = articuloDAO.findAllbyCriterio(criterioBusqueda);
        } else {
            this.listaArticulo = articuloDAO.findAll();
        }
        if (this.listaArticulo.size() > 0) {
            JSFutil.addSuccessMessage(this.listaArticulo.size() + " registro/s recuperado/s");
        } else {
            JSFutil.addSuccessMessage("Sin registros");
        }
        return "/frontend/index2?faces-redirect=true";
    }

    public String doListarCategoriaForm() {
        FacesContext context = FacesContext.getCurrentInstance();
        String idCategoria = context.getExternalContext().getRequestParameterMap().get("idCategoria");
        this.listaArticulo = articuloDAO.findAllbyCategoria(Integer.parseInt(idCategoria));
        if (this.listaArticulo.size() > 0) {
            JSFutil.addSuccessMessage(this.listaArticulo.size() + " registro/s recuperado/s");
        } else {
            JSFutil.addSuccessMessage("Sin registros");
        }
        return "/frontend/index2";
    }

    public String doVerDetalleArticulo(Articulo u) {
        this.articulo = u;
        this.listaAdjuntoArticulo = u.getArticuloAdjuntoList();
        return "/frontend/articulo/VerDetalleArticulo";
    }

    //********************************************
// METODOS DEL LISTENER
//********************************************
    
    /**
     * Render de la imagen desde el DAO
     *
     * @return
     */
    public StreamedContent imagenToDisplayFromId() {
        FacesContext context = FacesContext.getCurrentInstance();
        String id = context.getExternalContext().getRequestParameterMap().get("id");
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        } else {
            ArticuloAdjunto adj = articuloAdjuntoDAO.find(Integer.parseInt(id));
            StreamedContent file;
            if (adj != null) {
                InputStream stream = new ByteArrayInputStream(adj.getArchivo());
                file = new DefaultStreamedContent(stream, adj.getTipoArchivo(), adj.getNombreArchivo());
            } else {
                file = new DefaultStreamedContent();
            }
            return file;
        }
    }

    /**
     * Descargar un adjunto desde la BD
     *
     * @param adj
     * @return
     */
    public StreamedContent download(ArticuloAdjunto adj) {

        if (adj.getNombreArchivo() != null) {
            InputStream stream = new ByteArrayInputStream(adj.getArchivo());
            StreamedContent file = new DefaultStreamedContent(stream, adj.getTipoArchivo(), adj.getNombreArchivo());
            return file;
        } else {
            JSFutil.addErrorMessage("No dispone de adjuntos para visualizar...");
            String noContent = "<html><h1>Sin adjunto...</></html>";
            return new DefaultStreamedContent(new ByteArrayInputStream(noContent.getBytes()), "text/html", "No existe Archivo");
        }
    }
}
