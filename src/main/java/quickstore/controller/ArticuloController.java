/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quickstore.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Inject;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import quickstore.ejb.entity.Articulo;
import quickstore.ejb.entity.ArticuloAdjunto;
import quickstore.ejb.facade.ArticuloAdjuntoDAO;
import quickstore.ejb.facade.ArticuloDAO;
import quickstore.util.JSFutil;
import quickstore.util.JSFutil.PersistAction;
import sun.misc.IOUtils;

/**
 *
 * @author root
 */
@Named(value = "ArticuloController")
@SessionScoped
public class ArticuloController implements Serializable {

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

    /**
     * Creates a new instance of ArticuloController
     */
    public ArticuloController() {
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

    //********************************************
    // METODOS DE ACCIÓN
    //********************************************
    /**
     * Crear un Registro
     *
     * @return
     */
    public String doCrearForm() {
        this.listaAdjuntoArticulo = new ArrayList<>();
        this.articulo = new Articulo();
        return "/articulo/CrearArticulo";
    }

    /**
     * Preparar el Formulario de Listado
     *
     * @return
     */
    public String doListarForm() {
        this.doListar();
        return "/articulo/ListarArticulo";
    }

    /**
     * Preparar el Formulario de Visualización
     *
     * @param u
     * @return
     */
    public String doVerForm(Articulo u) {
        this.articulo = u;
        return "/articulo/VerArticulo";
    }

    /**
     * Preparar el Formulario de Edición
     *
     * @param u
     * @return
     */
    public String doEditarForm(Articulo u) {
        this.listaAdjuntoArticulo = this.articuloAdjuntoDAO.findAllbyArticulo(u.getIdArticulo());
        this.articulo = u;
        return "/articulo/CrearArticulo";
    }

    /**
     * Listar la Nomina por mes/anho
     */
    public void doListar() {
        this.listaArticulo = articuloDAO.findAll();
    }

    /**
     * Guardar un registro
     *
     * @return
     */
    public String doGuardar() {
        if (this.articulo.getIdArticulo() != null) {
            persist(PersistAction.UPDATE);
        } else {
            persist(PersistAction.CREATE);
        }
        return doListarForm();
    }

    private void persist(PersistAction persistAction) {
        try {
            if (persistAction.compareTo(PersistAction.CREATE) == 0) {
                articuloDAO.create(articulo);
                if (this.listaAdjuntoArticulo.size() > 0) {
                    for (ArticuloAdjunto aadj : this.listaAdjuntoArticulo) {
                        aadj.setIdArticulo(articulo);
                        articuloAdjuntoDAO.create(aadj);
                    }
                }
            } else if (persistAction.compareTo(PersistAction.UPDATE) == 0) {
                Integer id=this.articulo.getIdArticulo();
                articuloDAO.update(articulo);
                //Borramos todos sus adjuntos
                for (ArticuloAdjunto x: this.articuloAdjuntoDAO.findAllbyArticulo(id)){
                    this.articuloAdjuntoDAO.remove(x);
                }
                if (this.listaAdjuntoArticulo.size() > 0) {
                    for (ArticuloAdjunto aadj : this.listaAdjuntoArticulo) {
                        aadj.setIdArticulo(new Articulo(id));
                        articuloAdjuntoDAO.create(aadj);
                    }
                }
            } else if (persistAction.compareTo(PersistAction.DELETE) == 0) {
                articuloDAO.remove(articulo);
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
/**
     * Borrar un registro
     *
     * @param u
     */
    public void doBorrar(Articulo u) {
        this.articulo = u;
        persist(PersistAction.DELETE);
        doListarForm();
    }
    public void doSacarArticuloAdjuntoLista(int index) {
        this.listaAdjuntoArticulo.remove(index);
    }
    //********************************************
// METODOS DEL LISTENER
//********************************************

    /**
     * Manejador del FileUpload
     *
     * @param event
     */
    public void handleFileUploadArticulo(FileUploadEvent event) {
        try {
            if (this.listaAdjuntoArticulo == null) {
                this.listaAdjuntoArticulo = new ArrayList<>();
            }
            UploadedFile uf = event.getFile();
            ArticuloAdjunto adj = new ArticuloAdjunto();
            adj.setArchivo(IOUtils.readFully(uf.getInputstream(), -1, true));
            adj.setNombreArchivo(uf.getFileName());
            adj.setTipoArchivo(uf.getContentType());
            adj.setTamanhoArchivo(uf.getSize());
            this.listaAdjuntoArticulo.add(adj);

        } catch (IOException ex) {
            Logger.getLogger(ArticuloController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * getter Imagen a mostrar desde Uploaded
     *
     * @return
     */
    public StreamedContent imagenToDisplay() {
        FacesContext context = FacesContext.getCurrentInstance();
        String get = context.getExternalContext().getRequestParameterMap().get("index");

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        } else {
            ArticuloAdjunto adj = this.listaAdjuntoArticulo.get(Integer.parseInt(get));
            InputStream stream = new ByteArrayInputStream(adj.getArchivo());
            StreamedContent file = new DefaultStreamedContent(stream, adj.getTipoArchivo(), adj.getNombreArchivo());
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
