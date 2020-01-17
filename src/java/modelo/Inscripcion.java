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
 * @author Francisco
 */
@Entity
@Table(name = "inscripcion", catalog = "db_deportes", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Inscripcion.findAll", query = "SELECT i FROM Inscripcion i")
    , @NamedQuery(name = "Inscripcion.findByIdInscripcion", query = "SELECT i FROM Inscripcion i WHERE i.idInscripcion = :idInscripcion")
    , @NamedQuery(name = "Inscripcion.findByInscripcion", query = "SELECT i FROM Inscripcion i WHERE i.inscripcion = :inscripcion")
    , @NamedQuery(name = "Inscripcion.findByGarantia", query = "SELECT i FROM Inscripcion i WHERE i.garantia = :garantia")
    , @NamedQuery(name = "Inscripcion.findByObservacion", query = "SELECT i FROM Inscripcion i WHERE i.observacion = :observacion")
    , @NamedQuery(name = "Inscripcion.findByFechaResgistro", query = "SELECT i FROM Inscripcion i WHERE i.fechaResgistro = :fechaResgistro")})
public class Inscripcion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_inscripcion")
    private Integer idInscripcion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "inscripcion")
    private Double inscripcion;
    @Column(name = "garantia")
    private Double garantia;
    @Size(max = 145)
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "fecha_resgistro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaResgistro;
    @JoinColumn(name = "id_campeonato", referencedColumnName = "id_campeonato")
    @ManyToOne
    private Campeonato idCampeonato;
    @JoinColumn(name = "id_equipo", referencedColumnName = "id_equipo")
    @ManyToOne
    private Equipo idEquipo;

    public Inscripcion() {
    }

    public Inscripcion(Integer idInscripcion) {
        this.idInscripcion = idInscripcion;
    }

    public Integer getIdInscripcion() {
        return idInscripcion;
    }

    public void setIdInscripcion(Integer idInscripcion) {
        this.idInscripcion = idInscripcion;
    }

    public Double getInscripcion() {
        return inscripcion;
    }

    public void setInscripcion(Double inscripcion) {
        this.inscripcion = inscripcion;
    }

    public Double getGarantia() {
        return garantia;
    }

    public void setGarantia(Double garantia) {
        this.garantia = garantia;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Date getFechaResgistro() {
        return fechaResgistro;
    }

    public void setFechaResgistro(Date fechaResgistro) {
        this.fechaResgistro = fechaResgistro;
    }

    public Campeonato getIdCampeonato() {
        return idCampeonato;
    }

    public void setIdCampeonato(Campeonato idCampeonato) {
        this.idCampeonato = idCampeonato;
    }

    public Equipo getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(Equipo idEquipo) {
        this.idEquipo = idEquipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idInscripcion != null ? idInscripcion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Inscripcion)) {
            return false;
        }
        Inscripcion other = (Inscripcion) object;
        if ((this.idInscripcion == null && other.idInscripcion != null) || (this.idInscripcion != null && !this.idInscripcion.equals(other.idInscripcion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Inscripcion[ idInscripcion=" + idInscripcion + " ]";
    }
    
}
