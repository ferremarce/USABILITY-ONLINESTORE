/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quickstore.ejb.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author root
 */
@Entity
@Table(name = "DATOS_USUARIO",uniqueConstraints={@UniqueConstraint(columnNames = {"email"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DatosUsuario.findAll", query = "SELECT p FROM DatosUsuario p")})
public class DatosUsuario implements Serializable {
    @Size(max = 255)
    @Column(name = "IDIOMA")
    private String idioma;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DATOS_USUARIO")
    private Integer idDatosUsuario;
    @Size(max = 20)
    @Column(name = "RECIBIR_MAIL")
    private String recibirMail;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 255)
    @Column(name = "EMAIL", unique = true)
    private String email;
    @Size(max = 255)
    @Column(name = "APELLIDOS")
    private String apellidos;
    @Column(name = "FECHA_NACIMIENTO")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @Size(max = 255)
    @Column(name = "SEXO")
    private String sexo;
    @Size(max = 255)
    @Column(name = "NOMBRES")
    private String nombres;
    @OneToMany(mappedBy = "idDatosUsuario")
    private List<Usuario> usuarioList;
    @JoinColumn(name = "ID_PAIS", referencedColumnName = "ID_SUB_TIPO")
    @ManyToOne
    private SubTipo idPais;
    @JoinColumn(name = "ID_ACTIVIDAD", referencedColumnName = "ID_SUB_TIPO")
    @ManyToOne
    private SubTipo idActividad;
    @Column(name = "FECHA_REGISTRO")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "FECHA_PROCESO")
    @Temporal(TemporalType.DATE)
    private Date fechaProceso;
    @Size(max = 255)
    @Column(name = "PROFESION")
    private String profesion;

    public DatosUsuario() {
    }

    public Integer getIdDatosUsuario() {
        return idDatosUsuario;
    }

    public void setIdDatosUsuario(Integer idDatosUsuario) {
        this.idDatosUsuario = idDatosUsuario;
    }

    public String getRecibirMail() {
        return recibirMail;
    }

    public void setRecibirMail(String recibirMail) {
        this.recibirMail = recibirMail;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    public SubTipo getIdPais() {
        return idPais;
    }

    public void setIdPais(SubTipo idPais) {
        this.idPais = idPais;
    }

    public SubTipo getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(SubTipo idActividad) {
        this.idActividad = idActividad;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaProceso() {
        return fechaProceso;
    }

    public void setFechaProceso(Date fechaProceso) {
        this.fechaProceso = fechaProceso;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDatosUsuario != null ? idDatosUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DatosUsuario)) {
            return false;
        }
        DatosUsuario other = (DatosUsuario) object;
        if ((this.idDatosUsuario == null && other.idDatosUsuario != null) || (this.idDatosUsuario != null && !this.idDatosUsuario.equals(other.idDatosUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dominio.plapy.DatosUsuario[ idDatosUsuario=" + idDatosUsuario + " ]";
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }
}
