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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "QS_DIRECCION_CLIENTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DireccionCliente.findAll", query = "SELECT d FROM DireccionCliente d")})
public class DireccionCliente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_DIRECCION_CLIENTE")
    private Integer idDireccionCliente;
    @JoinColumn(name = "ID_DIRECCION", referencedColumnName = "ID_DIRECCION")
    @ManyToOne
    private Direccion idDireccion;
    @JoinColumn(name = "ID_CLIENTE", referencedColumnName = "ID_CLIENTE")
    @ManyToOne
    private Cliente idCliente;

    public DireccionCliente() {
    }

    public DireccionCliente(Integer idDireccionCliente) {
        this.idDireccionCliente = idDireccionCliente;
    }

    public Integer getIdDireccionCliente() {
        return idDireccionCliente;
    }

    public void setIdDireccionCliente(Integer idDireccionCliente) {
        this.idDireccionCliente = idDireccionCliente;
    }

    public Direccion getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(Direccion idDireccion) {
        this.idDireccion = idDireccion;
    }

    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDireccionCliente != null ? idDireccionCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DireccionCliente)) {
            return false;
        }
        DireccionCliente other = (DireccionCliente) object;
        if ((this.idDireccionCliente == null && other.idDireccionCliente != null) || (this.idDireccionCliente != null && !this.idDireccionCliente.equals(other.idDireccionCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "quickstore.ejb.entity.DireccionCliente[ idDireccionCliente=" + idDireccionCliente + " ]";
    }
    
}
