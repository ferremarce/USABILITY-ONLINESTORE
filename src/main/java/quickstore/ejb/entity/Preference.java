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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author root
 */
@Entity
@Table(name = "PREFERENCE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Preference.findAll", query = "SELECT p FROM Preference p")})
public class Preference implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_PREFERENCE")
    private Integer idPreference;
    @Size(max = 45)
    @Column(name = "FUENTE")
    private String fuente;
    @Size(max = 45)
    @Column(name = "TAMANHO")
    private String tamanho;
    @Size(max = 45)
    @Column(name = "IDIOMA")
    private String idioma;
    @Size(max = 45)
    @Column(name = "TEMA")
    private String tema;
    @Size(max = 255)
    @Column(name = "NOMBRE_PREFERENCIA")
    private String nombrePreferencia;
    @OneToMany(mappedBy = "idPreference")
    private List<Usuario> usuarioList;

    public Preference() {
    }

    public Preference(Integer idPreference) {
        this.idPreference = idPreference;
    }

    public Integer getIdPreference() {
        return idPreference;
    }

    public void setIdPreference(Integer idPreference) {
        this.idPreference = idPreference;
    }

    public String getFuente() {
        return fuente;
    }

    public void setFuente(String fuente) {
        this.fuente = fuente;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getNombrePreferencia() {
        return nombrePreferencia;
    }

    public void setNombrePreferencia(String nombrePreferencia) {
        this.nombrePreferencia = nombrePreferencia;
    }

    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPreference != null ? idPreference.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Preference)) {
            return false;
        }
        Preference other = (Preference) object;
        if ((this.idPreference == null && other.idPreference != null) || (this.idPreference != null && !this.idPreference.equals(other.idPreference))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "quickstore.ejb.entity.Preference[ idPreference=" + idPreference + " ]";
    }

}
