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
@Table(name = "QS_METODO_PAGO_CLIENTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MetodoPagoCliente.findAll", query = "SELECT m FROM MetodoPagoCliente m")})
public class MetodoPagoCliente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_METODO_PAGO_CLIENTE")
    private Integer idMetodoPagoCliente;
    @JoinColumn(name = "ID_METODO_PAGO", referencedColumnName = "ID_SUB_TIPO")
    @ManyToOne
    private SubTipo idMetodoPago;
    @JoinColumn(name = "ID_CLIENTE", referencedColumnName = "ID_CLIENTE")
    @ManyToOne
    private Cliente idCliente;
    @OneToMany(mappedBy = "idMetodoPagoCliente")
    private List<Pago> pagoList;

    public MetodoPagoCliente() {
    }

    public MetodoPagoCliente(Integer idMetodoPagoCliente) {
        this.idMetodoPagoCliente = idMetodoPagoCliente;
    }

    public Integer getIdMetodoPagoCliente() {
        return idMetodoPagoCliente;
    }

    public void setIdMetodoPagoCliente(Integer idMetodoPagoCliente) {
        this.idMetodoPagoCliente = idMetodoPagoCliente;
    }

    public SubTipo getIdMetodoPago() {
        return idMetodoPago;
    }

    public void setIdMetodoPago(SubTipo idMetodoPago) {
        this.idMetodoPago = idMetodoPago;
    }

    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
    }

    @XmlTransient
    public List<Pago> getPagoList() {
        return pagoList;
    }

    public void setPagoList(List<Pago> pagoList) {
        this.pagoList = pagoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMetodoPagoCliente != null ? idMetodoPagoCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MetodoPagoCliente)) {
            return false;
        }
        MetodoPagoCliente other = (MetodoPagoCliente) object;
        if ((this.idMetodoPagoCliente == null && other.idMetodoPagoCliente != null) || (this.idMetodoPagoCliente != null && !this.idMetodoPagoCliente.equals(other.idMetodoPagoCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "quickstore.ejb.entity.MetodoPagoCliente[ idMetodoPagoCliente=" + idMetodoPagoCliente + " ]";
    }
    
}
