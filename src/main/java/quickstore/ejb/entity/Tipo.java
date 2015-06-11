/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package quickstore.ejb.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jmferreira
 */
@Entity
@Table(name = "TIPO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipo.findAll", query = "SELECT t FROM Tipo t")})
public class Tipo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_TIPO")
    private Integer idTipo;
    @Size(max = 255)
    @Column(name = "DESCRIPCION_TIPO")
    private String descripcionTipo;
    @OneToMany(mappedBy = "idTipo")
    private Collection<SubTipo> subTipoCollection;

    public Tipo() {
    }

    public Tipo(Integer idTipo) {
        this.idTipo = idTipo;
    }

    public Integer getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Integer idTipo) {
        this.idTipo = idTipo;
    }

    public String getDescripcionTipo() {
        return descripcionTipo;
    }

    public void setDescripcionTipo(String descripcionTipo) {
        this.descripcionTipo = descripcionTipo;
    }

    @XmlTransient
    public Collection<SubTipo> getSubTipoCollection() {
        return subTipoCollection;
    }

    public void setSubTipoCollection(Collection<SubTipo> subTipoCollection) {
        this.subTipoCollection = subTipoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipo != null ? idTipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipo)) {
            return false;
        }
        Tipo other = (Tipo) object;
        return !((this.idTipo == null && other.idTipo != null) || (this.idTipo != null && !this.idTipo.equals(other.idTipo)));
    }

    @Override
    public String toString() {
        return this.getDescripcionTipo();
    }
    
}
