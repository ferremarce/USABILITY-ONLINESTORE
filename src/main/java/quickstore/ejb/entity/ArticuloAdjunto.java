/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quickstore.ejb.entity;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author root
 */
@Entity
@Table(name = "QS_ARTICULO_ADJUNTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ArticuloAdjunto.findAll", query = "SELECT a FROM ArticuloAdjunto a")})
public class ArticuloAdjunto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_ARCHIVO_ADJUNTO")
    private Integer idArchivoAdjunto;
    @Lob
    @Column(name = "ARCHIVO")
    private byte[] archivo;
    @Size(max = 255)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Size(max = 255)
    @Column(name = "NOMBRE_ARCHIVO")
    private String nombreArchivo;
    @Column(name = "TAMANHO_ARCHIVO")
    private long tamanhoArchivo;
    @Size(max = 255)
    @Column(name = "TIPO_ARCHIVO")
    private String tipoArchivo;
    @JoinColumn(name = "ID_TIPO_ADJUNTO", referencedColumnName = "ID_SUB_TIPO")
    @ManyToOne
    private SubTipo idTipoAdjunto;
    @JoinColumn(name = "ID_ARTICULO", referencedColumnName = "ID_ARTICULO")
    @ManyToOne
    private Articulo idArticulo;

    public ArticuloAdjunto() {
    }

    public ArticuloAdjunto(Integer idArchivoAdjunto) {
        this.idArchivoAdjunto = idArchivoAdjunto;
    }

    public Integer getIdArchivoAdjunto() {
        return idArchivoAdjunto;
    }

    public void setIdArchivoAdjunto(Integer idArchivoAdjunto) {
        this.idArchivoAdjunto = idArchivoAdjunto;
    }

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public long getTamanhoArchivo() {
        return tamanhoArchivo;
    }

    public void setTamanhoArchivo(long tamanhoArchivo) {
        this.tamanhoArchivo = tamanhoArchivo;
    }

    public String getTipoArchivo() {
        return tipoArchivo;
    }

    public void setTipoArchivo(String tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }

    public SubTipo getIdTipoAdjunto() {
        return idTipoAdjunto;
    }

    public void setIdTipoAdjunto(SubTipo idTipoAdjunto) {
        this.idTipoAdjunto = idTipoAdjunto;
    }

    public Articulo getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(Articulo idArticulo) {
        this.idArticulo = idArticulo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idArchivoAdjunto != null ? idArchivoAdjunto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ArticuloAdjunto)) {
            return false;
        }
        ArticuloAdjunto other = (ArticuloAdjunto) object;
        if ((this.idArchivoAdjunto == null && other.idArchivoAdjunto != null) || (this.idArchivoAdjunto != null && !this.idArchivoAdjunto.equals(other.idArchivoAdjunto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "quickstore.ejb.entity.ArticuloAdjunto_1[ idArchivoAdjunto=" + idArchivoAdjunto + " ]";
    }
    
}
