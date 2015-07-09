/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quickstore.ejb.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author root
 */
@Entity
@Table(name = "QS_ARTICULO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Articulo.findAll", query = "SELECT a FROM Articulo a")})
public class Articulo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_ARTICULO")
    private Integer idArticulo;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "DESCRIPCION_ARTICULO")
    private String descripcionArticulo;
    @Column(name = "ES_PROMOCION")
    private Boolean esPromocion;
    @Size(max = 255)
    @Column(name = "NOMBRE_ARTICULO")
    private String nombreArticulo;
    @Size(max = 255)
    @Column(name = "BREVE_DESCRIPCION")
    private String breveDescripcion;
    @Column(name = "PORCENTAJE_DESCUENTO")
    private Integer porcentajeDescuento;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PRECIO")
    private Float precio;
    @JoinColumn(name = "ID_CATEGORIA", referencedColumnName = "ID_SUB_TIPO")
    @ManyToOne
    private SubTipo idCategoria;
    @OneToMany(mappedBy = "idArticulo")
    private List<ArticuloAdjunto> articuloAdjuntoList;
    @OneToMany(mappedBy = "idArticulo")
    private List<OrdenCarrito> ordenCarritoList;
    @OneToMany(mappedBy = "idArticulo")
    private List<DeseoArticulo> deseoArticuloList;

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

    public String getDescripcionArticulo() {
        return descripcionArticulo;
    }

    public void setDescripcionArticulo(String descripcionArticulo) {
        this.descripcionArticulo = descripcionArticulo;
    }

    public Boolean getEsPromocion() {
        return esPromocion;
    }

    public void setEsPromocion(Boolean esPromocion) {
        this.esPromocion = esPromocion;
    }

    public String getNombreArticulo() {
        return nombreArticulo;
    }

    public void setNombreArticulo(String nombreArticulo) {
        this.nombreArticulo = nombreArticulo;
    }

    public Integer getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(Integer porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public SubTipo getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(SubTipo idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getBreveDescripcion() {
        return breveDescripcion;
    }

    public void setBreveDescripcion(String breveDescripcion) {
        this.breveDescripcion = breveDescripcion;
    }

    @XmlTransient
    public List<ArticuloAdjunto> getArticuloAdjuntoList() {
        return articuloAdjuntoList;
    }

    public void setArticuloAdjuntoList(List<ArticuloAdjunto> articuloAdjuntoList) {
        this.articuloAdjuntoList = articuloAdjuntoList;
    }

    @XmlTransient
    public List<OrdenCarrito> getOrdenCarritoList() {
        return ordenCarritoList;
    }

    public void setOrdenCarritoList(List<OrdenCarrito> ordenCarritoList) {
        this.ordenCarritoList = ordenCarritoList;
    }

    @XmlTransient
    public List<DeseoArticulo> getDeseoArticuloList() {
        return deseoArticuloList;
    }

    public void setDeseoArticuloList(List<DeseoArticulo> deseoArticuloList) {
        this.deseoArticuloList = deseoArticuloList;
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
        if ((this.idArticulo == null && other.idArticulo != null) || (this.idArticulo != null && !this.idArticulo.equals(other.idArticulo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "quickstore.ejb.entity.Articulo_1[ idArticulo=" + idArticulo + " ]";
    }

}
