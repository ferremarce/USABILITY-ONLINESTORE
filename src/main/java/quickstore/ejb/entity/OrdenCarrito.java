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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author root
 */
@Entity
@Table(name = "QS_ORDEN_CARRITO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrdenCarrito.findAll", query = "SELECT o FROM OrdenCarrito o")})
public class OrdenCarrito implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ORDEN_CARRITO")
    private Integer idOrdenCarrito;
    @Column(name = "CANTIDAD")
    private BigInteger cantidad;
    @Size(max = 255)
    @Column(name = "COMENTARIO")
    private String comentario;
    @JoinColumn(name = "ID_CARRITO", referencedColumnName = "ID_CARRITO")
    @ManyToOne
    private Carrito idCarrito;
    @JoinColumn(name = "ID_ARTICULO", referencedColumnName = "ID_ARTICULO")
    @ManyToOne
    private Articulo idArticulo;

    public OrdenCarrito() {
    }

    public OrdenCarrito(Integer idOrdenCarrito) {
        this.idOrdenCarrito = idOrdenCarrito;
    }

    public Integer getIdOrdenCarrito() {
        return idOrdenCarrito;
    }

    public void setIdOrdenCarrito(Integer idOrdenCarrito) {
        this.idOrdenCarrito = idOrdenCarrito;
    }

    public BigInteger getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigInteger cantidad) {
        this.cantidad = cantidad;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Carrito getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(Carrito idCarrito) {
        this.idCarrito = idCarrito;
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
        hash += (idOrdenCarrito != null ? idOrdenCarrito.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrdenCarrito)) {
            return false;
        }
        OrdenCarrito other = (OrdenCarrito) object;
        if ((this.idOrdenCarrito == null && other.idOrdenCarrito != null) || (this.idOrdenCarrito != null && !this.idOrdenCarrito.equals(other.idOrdenCarrito))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "quickstore.ejb.entity.OrdenCarrito[ idOrdenCarrito=" + idOrdenCarrito + " ]";
    }
    
}
