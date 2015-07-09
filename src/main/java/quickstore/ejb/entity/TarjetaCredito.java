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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "QS_TARJETA_CREDITO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TarjetaCredito.findAll", query = "SELECT q FROM TarjetaCredito q")})
public class TarjetaCredito implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_TARJETA_CREDITO")
    private Integer idTarjetaCredito;
    @Size(max = 45)
    @Column(name = "NUMERO_TARJETA")
    private String numeroTarjeta;
    @Size(max = 45)
    @Column(name = "TITULAR")
    private String titular;
    @Size(max = 45)
    @Column(name = "MES_CADUCIDAD")
    private String mesCaducidad;
    @Size(max = 45)
    @Column(name = "ANHO_CADUCIDAD")
    private String anhoCaducidad;
    @Size(max = 45)
    @Column(name = "CODIGO_CVV")
    private String codigoCvv;
    @OneToMany(mappedBy = "idTarjetaCredito")
    private List<MetodoPagoCliente> metodoPagoClienteList;

    public TarjetaCredito() {
    }

    public TarjetaCredito(Integer idTarjetaCredito) {
        this.idTarjetaCredito = idTarjetaCredito;
    }

    public Integer getIdTarjetaCredito() {
        return idTarjetaCredito;
    }

    public void setIdTarjetaCredito(Integer idTarjetaCredito) {
        this.idTarjetaCredito = idTarjetaCredito;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getMesCaducidad() {
        return mesCaducidad;
    }

    public void setMesCaducidad(String mesCaducidad) {
        this.mesCaducidad = mesCaducidad;
    }

    public String getAnhoCaducidad() {
        return anhoCaducidad;
    }

    public void setAnhoCaducidad(String anhoCaducidad) {
        this.anhoCaducidad = anhoCaducidad;
    }

    public String getCodigoCvv() {
        return codigoCvv;
    }

    public void setCodigoCvv(String codigoCvv) {
        this.codigoCvv = codigoCvv;
    }

    @XmlTransient
    public List<MetodoPagoCliente> getMetodoPagoClienteList() {
        return metodoPagoClienteList;
    }

    public void setMetodoPagoClienteList(List<MetodoPagoCliente> metodoPagoClienteList) {
        this.metodoPagoClienteList = metodoPagoClienteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTarjetaCredito != null ? idTarjetaCredito.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TarjetaCredito)) {
            return false;
        }
        TarjetaCredito other = (TarjetaCredito) object;
        if ((this.idTarjetaCredito == null && other.idTarjetaCredito != null) || (this.idTarjetaCredito != null && !this.idTarjetaCredito.equals(other.idTarjetaCredito))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "quickstore.ejb.entity.QsTarjetaCredito[ idTarjetaCredito=" + idTarjetaCredito + " ]";
    }
    
}
