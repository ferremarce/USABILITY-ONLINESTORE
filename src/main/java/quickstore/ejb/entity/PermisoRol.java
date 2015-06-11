/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package quickstore.ejb.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jmferreira
 */
@Entity
@Table(name = "PERMISO_ROL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PermisoRol.findAll", query = "SELECT p FROM PermisoRol p")})
public class PermisoRol implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PERMISO_ROL")
    private Integer idPermisoRol;
    @Column(name = "FECHA_ASIGNACION")
    @Temporal(TemporalType.DATE)
    private Date fechaAsignacion;
    @Size(max = 255)
    @Column(name = "HABILITADO")
    private String habilitado;
    @JoinColumn(name = "ID_ROL", referencedColumnName = "ID_ROL")
    @ManyToOne
    private Rol idRol;
    @JoinColumn(name = "ID_PERMISO", referencedColumnName = "ID_PERMISO")
    @ManyToOne
    private Permiso idPermiso;

    public PermisoRol() {
    }

    public PermisoRol(Integer idPermisoRol) {
        this.idPermisoRol = idPermisoRol;
    }

    public Integer getIdPermisoRol() {
        return idPermisoRol;
    }

    public void setIdPermisoRol(Integer idPermisoRol) {
        this.idPermisoRol = idPermisoRol;
    }

    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public String getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(String habilitado) {
        this.habilitado = habilitado;
    }

    public Rol getIdRol() {
        return idRol;
    }

    public void setIdRol(Rol idRol) {
        this.idRol = idRol;
    }

    public Permiso getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(Permiso idPermiso) {
        this.idPermiso = idPermiso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPermisoRol != null ? idPermisoRol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PermisoRol)) {
            return false;
        }
        PermisoRol other = (PermisoRol) object;
        return !((this.idPermisoRol == null && other.idPermisoRol != null) || (this.idPermisoRol != null && !this.idPermisoRol.equals(other.idPermisoRol)));
    }

    @Override
    public String toString() {
        return "modelo.PermisoRol[ idPermisoRol=" + idPermisoRol + " ]";
    }
}
