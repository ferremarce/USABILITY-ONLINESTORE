/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quickstore.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import javax.servlet.ServletContext;
import org.primefaces.context.RequestContext;
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
    private static final Logger LOG = Logger.getLogger(ArticuloController.class.getName());
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
    private Boolean hayCambios;
    @Inject
    private ClickCounter clickCounter;

    /**
     * Creates a new instance of ArticuloController
     */
    public ArticuloController() {
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

    public Boolean getHayCambios() {
        return hayCambios;
    }

    public void setHayCambios(Boolean hayCambios) {
        this.hayCambios = hayCambios;
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
        this.hayCambios = false;
        this.listaAdjuntoArticulo = new ArrayList<>();
        this.articulo = new Articulo();
        return "/backend/articulo/CrearArticulo";
    }

    /**
     * Preparar el Formulario de Listado
     *
     * @return
     */
    public String doListarForm() {
        this.clickCounter.setCount(0);
        this.criterioBusqueda = "";
        this.doListar();
        return "/backend/articulo/ListarArticulo";
    }

    /**
     * Preparar el Formulario de Visualización
     *
     * @param u
     * @return
     */
    public String doVerForm(Articulo u) {
        this.listaAdjuntoArticulo = this.articuloAdjuntoDAO.findAllbyArticulo(u.getIdArticulo());
        this.articulo = u;
        return "/backend/articulo/VerArticulo";
    }

    /**
     * Preparar el Formulario de Edición
     *
     * @param u
     * @return
     */
    public String doEditarForm(Articulo u) {
        this.hayCambios = false;
        this.listaAdjuntoArticulo = this.articuloAdjuntoDAO.findAllbyArticulo(u.getIdArticulo());
        this.articulo = u;
        return "/backend/articulo/CrearArticulo";
    }

    /**
     * Listar
     */
    public void doListar() {
//        try { 
//            Thread.sleep(5000);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(ArticuloController.class.getName()).log(Level.SEVERE, null, ex);
//        }
        if (this.criterioBusqueda.length() > 0) {
            this.listaArticulo = articuloDAO.findAllbyCriterio(criterioBusqueda);
        } else {
            this.listaArticulo = articuloDAO.findAllSorted("nombreArticulo", "ASC");
        }
        if (this.listaArticulo.size() > 0) {
            JSFutil.addSuccessMessage(this.listaArticulo.size() + " registro/s recuperado/s");
        } else {
            JSFutil.addSuccessMessage("Sin registros");
        }
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
        this.doListar();
        return "/backend/index";
    }

    public String doAbortOperation() {
        if (this.hayCambios) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('confirmacionAbort').show();");
            return "";
        } else {
            return this.doListarForm();
        }

    }

    private void persist(PersistAction persistAction) {
        try {
            if (persistAction.compareTo(PersistAction.CREATE) == 0) {
                if (this.articulo.getPorcentajeDescuento() == null) {
                    this.articulo.setPorcentajeDescuento(0);
                }
                articuloDAO.create(articulo);
                if (this.listaAdjuntoArticulo.size() > 0) {
                    for (ArticuloAdjunto aadj : this.listaAdjuntoArticulo) {
                        aadj.setIdArticulo(articulo);
                        articuloAdjuntoDAO.create(aadj);
                    }
                }
            } else if (persistAction.compareTo(PersistAction.UPDATE) == 0) {
                Integer id = this.articulo.getIdArticulo();
                if (this.articulo.getPorcentajeDescuento() == null) {
                    this.articulo.setPorcentajeDescuento(0);
                }
                articuloDAO.update(articulo);
                //Borramos todos sus adjuntos
                for (ArticuloAdjunto x : this.articuloAdjuntoDAO.findAllbyArticulo(id)) {
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
            adj.setArchivo(JSFutil.getBytesFromInputStream(uf.getInputstream()));
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
        String indice = context.getExternalContext().getRequestParameterMap().get("index");
        //Si no se envia un parametro, asume el primer elemento de la lista
        if (indice == null) {
            indice = "0";
        }
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        } else {
            ArticuloAdjunto adj = this.listaAdjuntoArticulo.get(Integer.parseInt(indice));
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

    public void generateAlias() {
        if (this.articulo.getAliasArticulo() == null) {
            this.articulo.setAliasArticulo(JSFutil.getFriendlyURI(this.articulo.getNombreArticulo()));
        }
    }

    public void siHayCambios() {
        this.hayCambios = true;
    }

    public StreamedContent imagenToDisplayFromId() throws FileNotFoundException {
        FacesContext context = FacesContext.getCurrentInstance();
        String id = context.getExternalContext().getRequestParameterMap().get("id");
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        } else {
            Articulo a = articuloDAO.find(Integer.parseInt(id));
            List<ArticuloAdjunto> listaAdj = articuloAdjuntoDAO.findAllbyArticulo(a.getIdArticulo());
            ArticuloAdjunto adj = null;
            if (!listaAdj.isEmpty()) {
                adj = listaAdj.get(0);
            }
            StreamedContent file;
            if (adj != null) {
                InputStream stream = new ByteArrayInputStream(adj.getArchivo());
                file = new DefaultStreamedContent(stream, adj.getTipoArchivo(), adj.getNombreArchivo());
            } else {
                ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
                String pathToWeb = servletContext.getRealPath(File.separator);
                File f = new File(pathToWeb + "img/noimage.png");
                file = new DefaultStreamedContent(new FileInputStream(f));
            }
            return file;
        }
    }
}
