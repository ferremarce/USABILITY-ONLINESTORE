/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quickstore.ejb.entity;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author root
 */
@Entity
@Table(name = "QS_DESEO_ARTICULO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DeseoArticulo.findAll", query = "SELECT d FROM DeseoArticulo d")})
public class DeseoArticulo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_DESEO_ARTICULO")
    private Integer idDeseoArticulo;
    @JoinColumn(name = "ID_ARTICULO", referencedColumnName = "ID_ARTICULO")
    @ManyToOne
    private Articulo idArticulo;
    @JoinColumn(name = "ID_LISTA_DESEO", referencedColumnName = "ID_LISTA_DESEO")
    @ManyToOne
    private ListaDeseo idListaDeseo;

    public DeseoArticulo() {
    }

    public DeseoArticulo(Integer idDeseoArticulo) {
        this.idDeseoArticulo = idDeseoArticulo;
    }

    public Integer getIdDeseoArticulo() {
        return idDeseoArticulo;
    }

    public void setIdDeseoArticulo(Integer idDeseoArticulo) {
        this.idDeseoArticulo = idDeseoArticulo;
    }

    public Articulo getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(Articulo idArticulo) {
        this.idArticulo = idArticulo;
    }

    public ListaDeseo getIdListaDeseo() {
        return idListaDeseo;
    }

    public void setIdListaDeseo(ListaDeseo idListaDeseo) {
        this.idListaDeseo = idListaDeseo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDeseoArticulo != null ? idDeseoArticulo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DeseoArticulo)) {
            return false;
        }
        DeseoArticulo other = (DeseoArticulo) object;
        if ((this.idDeseoArticulo == null && other.idDeseoArticulo != null) || (this.idDeseoArticulo != null && !this.idDeseoArticulo.equals(other.idDeseoArticulo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "quickstore.ejb.entity.DeseoArticulo[ idDeseoArticulo=" + idDeseoArticulo + " ]";
    }
    
}
