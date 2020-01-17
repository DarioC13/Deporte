/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Francisco
 */
@Entity
@Table(name = "campeonato", catalog = "db_deportes", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Campeonato.findAll", query = "SELECT c FROM Campeonato c")
    , @NamedQuery(name = "Campeonato.findByIdCampeonato", query = "SELECT c FROM Campeonato c WHERE c.idCampeonato = :idCampeonato")
    , @NamedQuery(name = "Campeonato.findByCampeonato", query = "SELECT c FROM Campeonato c WHERE c.campeonato = :campeonato")
    , @NamedQuery(name = "Campeonato.findByFechaDeInicio", query = "SELECT c FROM Campeonato c WHERE c.fechaDeInicio = :fechaDeInicio")
    , @NamedQuery(name = "Campeonato.findByFechaFin", query = "SELECT c FROM Campeonato c WHERE c.fechaFin = :fechaFin")})
public class Campeonato implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_campeonato")
    private Integer idCampeonato;
    @Size(max = 85)
    @Column(name = "campeonato")
    private String campeonato;
    @Column(name = "fecha_de_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaDeInicio;
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;

    public Campeonato() {
    }

    public Campeonato(Integer idCampeonato) {
        this.idCampeonato = idCampeonato;
    }

    public Integer getIdCampeonato() {
        return idCampeonato;
    }

    public void setIdCampeonato(Integer idCampeonato) {
        this.idCampeonato = idCampeonato;
    }

    public String getCampeonato() {
        return campeonato;
    }

    public void setCampeonato(String campeonato) {
        this.campeonato = campeonato;
    }

    public Date getFechaDeInicio() {
        return fechaDeInicio;
    }

    public void setFechaDeInicio(Date fechaDeInicio) {
        this.fechaDeInicio = fechaDeInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCampeonato != null ? idCampeonato.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Campeonato)) {
            return false;
        }
        Campeonato other = (Campeonato) object;
        if ((this.idCampeonato == null && other.idCampeonato != null) || (this.idCampeonato != null && !this.idCampeonato.equals(other.idCampeonato))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getCampeonato();
    }
    
}
