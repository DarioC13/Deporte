/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Lenovo
 */
@Entity
@Table(name = "rol_deportivo")
@NamedQueries({
    @NamedQuery(name = "RolDeportivo.findAll", query = "SELECT r FROM RolDeportivo r")
    , @NamedQuery(name = "RolDeportivo.findByIdRolDeportivo", query = "SELECT r FROM RolDeportivo r WHERE r.idRolDeportivo = :idRolDeportivo")
    , @NamedQuery(name = "RolDeportivo.findByRolDeportivo", query = "SELECT r FROM RolDeportivo r WHERE r.rolDeportivo = :rolDeportivo")
    , @NamedQuery(name = "RolDeportivo.findByObservacion", query = "SELECT r FROM RolDeportivo r WHERE r.observacion = :observacion")
    , @NamedQuery(name = "RolDeportivo.findByFechaHoraRegistro", query = "SELECT r FROM RolDeportivo r WHERE r.fechaHoraRegistro = :fechaHoraRegistro")})
public class RolDeportivo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_rol_deportivo")
    private Integer idRolDeportivo;
    @Size(max = 45)
    @Column(name = "rol_deportivo")
    private String rolDeportivo;
    @Size(max = 45)
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "fecha_hora_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraRegistro;
    @OneToMany(mappedBy = "idRolDeportivo")
    private List<Persona> personaList;

    public RolDeportivo() {
    }

    public RolDeportivo(Integer idRolDeportivo) {
        this.idRolDeportivo = idRolDeportivo;
    }

    public Integer getIdRolDeportivo() {
        return idRolDeportivo;
    }

    public void setIdRolDeportivo(Integer idRolDeportivo) {
        this.idRolDeportivo = idRolDeportivo;
    }

    public String getRolDeportivo() {
        return rolDeportivo;
    }

    public void setRolDeportivo(String rolDeportivo) {
        this.rolDeportivo = rolDeportivo;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Date getFechaHoraRegistro() {
        return fechaHoraRegistro;
    }

    public void setFechaHoraRegistro(Date fechaHoraRegistro) {
        this.fechaHoraRegistro = fechaHoraRegistro;
    }

    public List<Persona> getPersonaList() {
        return personaList;
    }

    public void setPersonaList(List<Persona> personaList) {
        this.personaList = personaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRolDeportivo != null ? idRolDeportivo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RolDeportivo)) {
            return false;
        }
        RolDeportivo other = (RolDeportivo) object;
        if ((this.idRolDeportivo == null && other.idRolDeportivo != null) || (this.idRolDeportivo != null && !this.idRolDeportivo.equals(other.idRolDeportivo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getRolDeportivo();
    }
    
}
