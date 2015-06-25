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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author root
 */
@Entity
@Table(name = "QS_LISTA_DESEO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ListaDeseo.findAll", query = "SELECT l FROM ListaDeseo l")})
public class ListaDeseo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_LISTA_DESEO")
    private Integer idListaDeseo;
    @JoinColumn(name = "ID_CLIENTE", referencedColumnName = "ID_CLIENTE")
    @ManyToOne
    private Cliente idCliente;
    @OneToMany(mappedBy = "idListaDeseo")
    private List<DeseoArticulo> deseoArticuloList;

    public ListaDeseo() {
    }

    public ListaDeseo(Integer idListaDeseo) {
        this.idListaDeseo = idListaDeseo;
    }

    public Integer getIdListaDeseo() {
        return idListaDeseo;
    }

    public void setIdListaDeseo(Integer idListaDeseo) {
        this.idListaDeseo = idListaDeseo;
    }

    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
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
        hash += (idListaDeseo != null ? idListaDeseo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ListaDeseo)) {
            return false;
        }
        ListaDeseo other = (ListaDeseo) object;
        if ((this.idListaDeseo == null && other.idListaDeseo != null) || (this.idListaDeseo != null && !this.idListaDeseo.equals(other.idListaDeseo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "quickstore.ejb.entity.ListaDeseo[ idListaDeseo=" + idListaDeseo + " ]";
    }
    
}
