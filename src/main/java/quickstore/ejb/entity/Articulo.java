/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quickstore.ejb.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author root
 */
@Entity
@Table(name = "ARTICULO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Articulo.findAll", query = "SELECT a FROM Articulo a")})
public class Articulo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ARTICULO")
    private Integer idArticulo;
    @Size(max = 255)
    @Column(name = "NOMBRE_ARTICULO")
    private String nombreArticulo;
    @JoinColumn(name = "ID_CATEGORIA", referencedColumnName = "ID_SUB_TIPO")
    @ManyToOne
    private SubTipo idCategoria;
    @Lob
    @Column(name = "DESCRIPCION_ARTICULO")
    private String descripcionArticulo;
    @OneToMany(mappedBy = "idArticulo")
    private List<ArticuloAdjunto> articuloAdjuntoList;

    public Articulo() {
    }

    public Articulo(Integer idArticulo) {
        this.idArticulo = idArticulo;
    }

    public Integer getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(Integer idArticulo) {
        this.idArticulo = idArticulo;
    }

    public String getNombreArticulo() {
        return nombreArticulo;
    }

    public void setNombreArticulo(String nombreArticulo) {
        this.nombreArticulo = nombreArticulo;
    }

    public SubTipo getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(SubTipo idCategoria) {
        this.idCategoria = idCategoria;
    }

    
    public String getDescripcionArticulo() {
        return descripcionArticulo;
    }

    public void setDescripcionArticulo(String descripcionArticulo) {
        this.descripcionArticulo = descripcionArticulo;
    }

    public List<ArticuloAdjunto> getArticuloAdjuntoList() {
        return articuloAdjuntoList;
    }

    public void setArticuloAdjuntoList(List<ArticuloAdjunto> articuloAdjuntoList) {
        this.articuloAdjuntoList = articuloAdjuntoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idArticulo != null ? idArticulo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Articulo)) {
            return false;
        }
        Articulo other = (Articulo) object;
        return !((this.idArticulo == null && other.idArticulo != null) || (this.idArticulo != null && !this.idArticulo.equals(other.idArticulo)));
    }

    @Override
    public String toString() {
        return "quickstore.ejb.entity.Articulo[ idArticulo=" + idArticulo + " ]";
    }

}
