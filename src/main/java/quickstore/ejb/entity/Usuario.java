/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package quickstore.ejb.entity;

import java.io.Serializable;
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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jmferreira
 */
@Entity
@Table(name = "USUARIO",
        uniqueConstraints
        = @UniqueConstraint(columnNames = {"CUENTA"}))
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private Integer idUsuario;
    @Column(name = "ACTIVO")
    private Boolean esActivo;
    @Size(max = 255)
    @Column(name = "APELLIDOS")
    private String apellidos;
    @Size(max = 255)
    @Column(name = "CONTRASENHA")
    private String contrasenha;
    @Size(max = 255)
    @Column(unique = true, name = "CUENTA")
    private String cuenta;
    @Size(max = 255)
    @Column(name = "NOMBRES")
    private String nombres;
    @JoinColumn(name = "ID_ROL", referencedColumnName = "ID_ROL")
    @ManyToOne
    private Rol idRol;
    @JoinColumn(name = "ID_PREFERENCE", referencedColumnName = "ID_PREFERENCE")
    @ManyToOne
    private Preference idPreference;
    @JoinColumn(name = "ID_SUB_ROL", referencedColumnName = "ID_SUB_TIPO")
    @ManyToOne
    private SubTipo idSubRol;
    @Column(name = "LOGIN_EXTERNO")
    private Boolean loginExterno;
    @JoinColumn(name = "ID_DATOS_USUARIO", referencedColumnName = "ID_DATOS_USUARIO")
    @ManyToOne
    private DatosUsuario idDatosUsuario;

    public Usuario() {
    }

    public Usuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Boolean getEsActivo() {
        return esActivo;
    }

    public void setEsActivo(Boolean esActivo) {
        this.esActivo = esActivo;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getContrasenha() {
        return contrasenha;
    }

    public void setContrasenha(String contrasenha) {
        this.contrasenha = contrasenha;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public Rol getIdRol() {
        return idRol;
    }

    public void setIdRol(Rol idRol) {
        this.idRol = idRol;
    }

    public Preference getIdPreference() {
        return idPreference;
    }

    public void setIdPreference(Preference idPreference) {
        this.idPreference = idPreference;
    }

    public SubTipo getIdSubRol() {
        return idSubRol;
    }

    public void setIdSubRol(SubTipo idSubRol) {
        this.idSubRol = idSubRol;
    }

    public Boolean getLoginExterno() {
        return loginExterno;
    }

    public void setLoginExterno(Boolean loginExterno) {
        this.loginExterno = loginExterno;
    }

    public DatosUsuario getIdDatosUsuario() {
        return idDatosUsuario;
    }

    public void setIdDatosUsuario(DatosUsuario idDatosUsuario) {
        this.idDatosUsuario = idDatosUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        return !((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario)));
    }

    @Override
    public String toString() {
        return this.getApellidos() + ", " + this.getNombres();
    }
}
