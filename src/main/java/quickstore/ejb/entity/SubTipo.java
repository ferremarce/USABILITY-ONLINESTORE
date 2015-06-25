/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quickstore.ejb.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jmferreira1978@gmail.com
 */
@Entity
@Table(name = "SUB_TIPO")
@NamedQueries({
    @NamedQuery(name = "SubTipo.findAll", query = "SELECT s FROM SubTipo s")})
public class SubTipo implements Serializable {
    @OneToMany(mappedBy = "idCategoria")
    private List<Articulo> articuloList;
    @OneToMany(mappedBy = "idTipoAdjunto")
    private List<ArticuloAdjunto> articuloAdjuntoList;
    @OneToMany(mappedBy = "idMetodoPago")
    private List<MetodoPagoCliente> metodoPagoClienteList;
    @OneToMany(mappedBy = "idSubRol")
    private List<Usuario> usuarioList;
    @OneToMany(mappedBy = "idPais")
    private List<DatosUsuario> datosUsuarioList;
    @OneToMany(mappedBy = "idActividad")
    private List<DatosUsuario> datosUsuarioList1;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SUB_TIPO")
    private Integer idSubTipo;
    @Size(max = 255)
    @Column(name = "DESCRIPCION_SUB_TIPO")
    private String descripcionSubTipo;
    @Column(name = "ID_SUB_TIPO_ANTERIOR")
    private Integer idSubTipoAnterior;
    @JoinColumn(name = "ID_TIPO", referencedColumnName = "ID_TIPO")
    @ManyToOne
    private Tipo idTipo;

    public SubTipo() {
    }

    public SubTipo(Integer idSubTipo) {
        this.idSubTipo = idSubTipo;
    }

    public Integer getIdSubTipo() {
        return idSubTipo;
    }

    public void setIdSubTipo(Integer idSubTipo) {
        this.idSubTipo = idSubTipo;
    }

    public String getDescripcionSubTipo() {
        return descripcionSubTipo;
    }

    public void setDescripcionSubTipo(String descripcionSubTipo) {
        this.descripcionSubTipo = descripcionSubTipo;
    }

    public Integer getIdSubTipoAnterior() {
        return idSubTipoAnterior;
    }

    public void setIdSubTipoAnterior(Integer idSubTipoAnterior) {
        this.idSubTipoAnterior = idSubTipoAnterior;
    }

    public Tipo getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Tipo idTipo) {
        this.idTipo = idTipo;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSubTipo != null ? idSubTipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SubTipo)) {
            return false;
        }
        SubTipo other = (SubTipo) object;
        return !((this.idSubTipo == null && other.idSubTipo != null) || (this.idSubTipo != null && !this.idSubTipo.equals(other.idSubTipo)));
    }

    @Override
    public String toString() {
        return this.getDescripcionSubTipo();
    }

    @XmlTransient
    public List<Articulo> getArticuloList() {
        return articuloList;
    }

    public void setArticuloList(List<Articulo> articuloList) {
        this.articuloList = articuloList;
    }

    @XmlTransient
    public List<ArticuloAdjunto> getArticuloAdjuntoList() {
        return articuloAdjuntoList;
    }

    public void setArticuloAdjuntoList(List<ArticuloAdjunto> articuloAdjuntoList) {
        this.articuloAdjuntoList = articuloAdjuntoList;
    }

    @XmlTransient
    public List<MetodoPagoCliente> getMetodoPagoClienteList() {
        return metodoPagoClienteList;
    }

    public void setMetodoPagoClienteList(List<MetodoPagoCliente> metodoPagoClienteList) {
        this.metodoPagoClienteList = metodoPagoClienteList;
    }

    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    @XmlTransient
    public List<DatosUsuario> getDatosUsuarioList() {
        return datosUsuarioList;
    }

    public void setDatosUsuarioList(List<DatosUsuario> datosUsuarioList) {
        this.datosUsuarioList = datosUsuarioList;
    }

    @XmlTransient
    public List<DatosUsuario> getDatosUsuarioList1() {
        return datosUsuarioList1;
    }

    public void setDatosUsuarioList1(List<DatosUsuario> datosUsuarioList1) {
        this.datosUsuarioList1 = datosUsuarioList1;
    }


}
