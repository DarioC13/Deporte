/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

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
import javax.validation.constraints.Size;

/**
 *
 * @author Lenovo
 */
@Entity
@Table(name = "estado_encuentro")
@NamedQueries({
    @NamedQuery(name = "EstadoEncuentro.findAll", query = "SELECT e FROM EstadoEncuentro e")
    , @NamedQuery(name = "EstadoEncuentro.findByIdEstadoEncuentro", query = "SELECT e FROM EstadoEncuentro e WHERE e.idEstadoEncuentro = :idEstadoEncuentro")
    , @NamedQuery(name = "EstadoEncuentro.findByEstadoEncuentro", query = "SELECT e FROM EstadoEncuentro e WHERE e.estadoEncuentro = :estadoEncuentro")
    , @NamedQuery(name = "EstadoEncuentro.findByPuntos", query = "SELECT e FROM EstadoEncuentro e WHERE e.puntos = :puntos")})
public class EstadoEncuentro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_estado_encuentro")
    private Integer idEstadoEncuentro;
    @Size(max = 45)
    @Column(name = "estado_encuentro")
    private String estadoEncuentro;
    @Size(max = 45)
    @Column(name = "puntos")
    private String puntos;
    @OneToMany(mappedBy = "idEstadoEncuentro")
    private List<MarcadorEquipo> marcadorEquipoList;

    public EstadoEncuentro() {
    }

    public EstadoEncuentro(Integer idEstadoEncuentro) {
        this.idEstadoEncuentro = idEstadoEncuentro;
    }

    public Integer getIdEstadoEncuentro() {
        return idEstadoEncuentro;
    }

    public void setIdEstadoEncuentro(Integer idEstadoEncuentro) {
        this.idEstadoEncuentro = idEstadoEncuentro;
    }

    public String getEstadoEncuentro() {
        return estadoEncuentro;
    }

    public void setEstadoEncuentro(String estadoEncuentro) {
        this.estadoEncuentro = estadoEncuentro;
    }

    public String getPuntos() {
        return puntos;
    }

    public void setPuntos(String puntos) {
        this.puntos = puntos;
    }

    public List<MarcadorEquipo> getMarcadorEquipoList() {
        return marcadorEquipoList;
    }

    public void setMarcadorEquipoList(List<MarcadorEquipo> marcadorEquipoList) {
        this.marcadorEquipoList = marcadorEquipoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstadoEncuentro != null ? idEstadoEncuentro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoEncuentro)) {
            return false;
        }
        EstadoEncuentro other = (EstadoEncuentro) object;
        if ((this.idEstadoEncuentro == null && other.idEstadoEncuentro != null) || (this.idEstadoEncuentro != null && !this.idEstadoEncuentro.equals(other.idEstadoEncuentro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getEstadoEncuentro();
    }
    
}
