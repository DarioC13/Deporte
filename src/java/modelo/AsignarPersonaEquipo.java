/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
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
import javax.validation.constraints.Size;

/**
 *
 * @author Lenovo
 */
@Entity
@Table(name = "asignar_persona_equipo")
@NamedQueries({
    @NamedQuery(name = "AsignarPersonaEquipo.findAll", query = "SELECT a FROM AsignarPersonaEquipo a")
    , @NamedQuery(name = "AsignarPersonaEquipo.findByIdAsignarPersonaEquipo", query = "SELECT a FROM AsignarPersonaEquipo a WHERE a.idAsignarPersonaEquipo = :idAsignarPersonaEquipo")
    , @NamedQuery(name = "AsignarPersonaEquipo.findByObservacion", query = "SELECT a FROM AsignarPersonaEquipo a WHERE a.observacion = :observacion")})
public class AsignarPersonaEquipo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_asignar_persona_equipo")
    private Integer idAsignarPersonaEquipo;
    @Size(max = 105)
    @Column(name = "observacion")
    private String observacion;
    @JoinColumn(name = "id_equipo", referencedColumnName = "id_equipo")
    @ManyToOne
    private Equipo idEquipo;
    @JoinColumn(name = "id_persona", referencedColumnName = "id_persona")
    @ManyToOne
    private Persona idPersona;

    public AsignarPersonaEquipo() {
    }

    public AsignarPersonaEquipo(Integer idAsignarPersonaEquipo) {
        this.idAsignarPersonaEquipo = idAsignarPersonaEquipo;
    }

    public Integer getIdAsignarPersonaEquipo() {
        return idAsignarPersonaEquipo;
    }

    public void setIdAsignarPersonaEquipo(Integer idAsignarPersonaEquipo) {
        this.idAsignarPersonaEquipo = idAsignarPersonaEquipo;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Equipo getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(Equipo idEquipo) {
        this.idEquipo = idEquipo;
    }

    public Persona getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Persona idPersona) {
        this.idPersona = idPersona;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAsignarPersonaEquipo != null ? idAsignarPersonaEquipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AsignarPersonaEquipo)) {
            return false;
        }
        AsignarPersonaEquipo other = (AsignarPersonaEquipo) object;
        if ((this.idAsignarPersonaEquipo == null && other.idAsignarPersonaEquipo != null) || (this.idAsignarPersonaEquipo != null && !this.idAsignarPersonaEquipo.equals(other.idAsignarPersonaEquipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.AsignarPersonaEquipo[ idAsignarPersonaEquipo=" + idAsignarPersonaEquipo + " ]";
    }
    
}
