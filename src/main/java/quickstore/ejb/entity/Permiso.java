/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package quickstore.ejb.entity;

import java.io.Serializable;
import java.util.Collection;
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
 * @author jmferreira
 */
@Entity
@Table(name = "PERMISO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Permiso.findAll", query = "SELECT p FROM Permiso p")})
public class Permiso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PERMISO")
    private Integer idPermiso;
    @Size(max = 255)
    @Column(name = "DESCRIPCION_PERMISO")
    private String descripcionPermiso;
    @Size(max = 255)
    @Column(name = "NIVEL")
    private String nivel;
    @Column(name = "ORDEN")
    private Integer orden;
    @Size(max = 255)
    @Column(name = "TAG_MENU")
    private String tagMenu;
    @Size(max = 255)
    @Column(name = "URL_IMAGEN")
    private String urlImagen;
    @OneToMany(mappedBy = "idPermiso")
    private Collection<PermisoRol> permisoRolCollection;

    public Permiso() {
    }

    public Permiso(Integer idPermiso) {
        this.idPermiso = idPermiso;
    }

    public Integer getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(Integer idPermiso) {
        this.idPermiso = idPermiso;
    }

    public String getDescripcionPermiso() {
        return descripcionPermiso;
    }

    public void setDescripcionPermiso(String descripcionPermiso) {
        this.descripcionPermiso = descripcionPermiso;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getTagMenu() {
        return tagMenu;
    }

    public void setTagMenu(String tagMenu) {
        this.tagMenu = tagMenu;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    @XmlTransient
    public Collection<PermisoRol> getPermisoRolCollection() {
        return permisoRolCollection;
    }

    public void setPermisoRolCollection(Collection<PermisoRol> permisoRolCollection) {
        this.permisoRolCollection = permisoRolCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPermiso != null ? idPermiso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Permiso)) {
            return false;
        }
        Permiso other = (Permiso) object;
        return !((this.idPermiso == null && other.idPermiso != null) || (this.idPermiso != null && !this.idPermiso.equals(other.idPermiso)));
    }

    @Override
    public String toString() {
        return this.nivel+" "+this.getDescripcionPermiso();
    }
    
}
