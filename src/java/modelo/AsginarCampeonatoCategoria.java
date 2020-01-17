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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "asginar_campeonato_categoria")
@NamedQueries({
    @NamedQuery(name = "AsginarCampeonatoCategoria.findAll", query = "SELECT a FROM AsginarCampeonatoCategoria a")
    , @NamedQuery(name = "AsginarCampeonatoCategoria.findByIdasginarCampeonatoCategoria", query = "SELECT a FROM AsginarCampeonatoCategoria a WHERE a.idasginarCampeonatoCategoria = :idasginarCampeonatoCategoria")
    , @NamedQuery(name = "AsginarCampeonatoCategoria.findByObservacion", query = "SELECT a FROM AsginarCampeonatoCategoria a WHERE a.observacion = :observacion")})
public class AsginarCampeonatoCategoria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idasginar_campeonato_categoria")
    private Integer idasginarCampeonatoCategoria;
    @Size(max = 45)
    @Column(name = "observacion")
    private String observacion;
    @OneToMany(mappedBy = "asignarCampeonatoCategoria")
    private List<Encuentros> encuentrosList;
    @JoinColumn(name = "id_campeonato", referencedColumnName = "id_campeonato")
    @ManyToOne
    private Campeonato idCampeonato;
    @JoinColumn(name = "id_categoria", referencedColumnName = "id_categoria")
    @ManyToOne
    private Categoria idCategoria;
    @JoinColumn(name = "id_equipo", referencedColumnName = "id_equipo")
    @ManyToOne
    private Equipo idEquipo;

    public AsginarCampeonatoCategoria() {
    }

    public AsginarCampeonatoCategoria(Integer idasginarCampeonatoCategoria) {
        this.idasginarCampeonatoCategoria = idasginarCampeonatoCategoria;
    }

    public Integer getIdasginarCampeonatoCategoria() {
        return idasginarCampeonatoCategoria;
    }

    public void setIdasginarCampeonatoCategoria(Integer idasginarCampeonatoCategoria) {
        this.idasginarCampeonatoCategoria = idasginarCampeonatoCategoria;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public List<Encuentros> getEncuentrosList() {
        return encuentrosList;
    }

    public void setEncuentrosList(List<Encuentros> encuentrosList) {
        this.encuentrosList = encuentrosList;
    }

    public Campeonato getIdCampeonato() {
        return idCampeonato;
    }

    public void setIdCampeonato(Campeonato idCampeonato) {
        this.idCampeonato = idCampeonato;
    }

    public Categoria getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Categoria idCategoria) {
        this.idCategoria = idCategoria;
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
        hash += (idasginarCampeonatoCategoria != null ? idasginarCampeonatoCategoria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AsginarCampeonatoCategoria)) {
            return false;
        }
        AsginarCampeonatoCategoria other = (AsginarCampeonatoCategoria) object;
        if ((this.idasginarCampeonatoCategoria == null && other.idasginarCampeonatoCategoria != null) || (this.idasginarCampeonatoCategoria != null && !this.idasginarCampeonatoCategoria.equals(other.idasginarCampeonatoCategoria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return idCampeonato.getCampeonato();
    }
    
}
