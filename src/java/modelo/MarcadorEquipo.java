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
@Table(name = "marcador_equipo")
@NamedQueries({
    @NamedQuery(name = "MarcadorEquipo.findAll", query = "SELECT m FROM MarcadorEquipo m")
    , @NamedQuery(name = "MarcadorEquipo.findByIdMarcadorEquipo", query = "SELECT m FROM MarcadorEquipo m WHERE m.idMarcadorEquipo = :idMarcadorEquipo")
    , @NamedQuery(name = "MarcadorEquipo.findByNumGolFavor", query = "SELECT m FROM MarcadorEquipo m WHERE m.numGolFavor = :numGolFavor")
    , @NamedQuery(name = "MarcadorEquipo.findByNumGolContra", query = "SELECT m FROM MarcadorEquipo m WHERE m.numGolContra = :numGolContra")
    , @NamedQuery(name = "MarcadorEquipo.findByDiferenciaGoles", query = "SELECT m FROM MarcadorEquipo m WHERE m.diferenciaGoles = :diferenciaGoles")
    , @NamedQuery(name = "MarcadorEquipo.findByInformeEncuentroEquipo", query = "SELECT m FROM MarcadorEquipo m WHERE m.informeEncuentroEquipo = :informeEncuentroEquipo")})
public class MarcadorEquipo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_marcador_equipo")
    private Integer idMarcadorEquipo;
    @Column(name = "num_gol_favor")
    private Integer numGolFavor;
    @Column(name = "num_gol_contra")
    private Integer numGolContra;
    @Column(name = "diferencia_goles")
    private Integer diferenciaGoles;
    @Size(max = 205)
    @Column(name = "informe_encuentro_equipo")
    private String informeEncuentroEquipo;
    @JoinColumn(name = "id_encuentro", referencedColumnName = "id_encuentros")
    @ManyToOne
    private Encuentros idEncuentro;
    @JoinColumn(name = "id_equipo", referencedColumnName = "id_equipo")
    @ManyToOne
    private Equipo idEquipo;
    @JoinColumn(name = "id_estado_encuentro", referencedColumnName = "id_estado_encuentro")
    @ManyToOne
    private EstadoEncuentro idEstadoEncuentro;

    public MarcadorEquipo() {
    }

    public MarcadorEquipo(Integer idMarcadorEquipo) {
        this.idMarcadorEquipo = idMarcadorEquipo;
    }

    public Integer getIdMarcadorEquipo() {
        return idMarcadorEquipo;
    }

    public void setIdMarcadorEquipo(Integer idMarcadorEquipo) {
        this.idMarcadorEquipo = idMarcadorEquipo;
    }

    public Integer getNumGolFavor() {
        return numGolFavor;
    }

    public void setNumGolFavor(Integer numGolFavor) {
        this.numGolFavor = numGolFavor;
    }

    public Integer getNumGolContra() {
        return numGolContra;
    }

    public void setNumGolContra(Integer numGolContra) {
        this.numGolContra = numGolContra;
    }

    public Integer getDiferenciaGoles() {
        return diferenciaGoles;
    }

    public void setDiferenciaGoles(Integer diferenciaGoles) {
        this.diferenciaGoles = diferenciaGoles;
    }

    public String getInformeEncuentroEquipo() {
        return informeEncuentroEquipo;
    }

    public void setInformeEncuentroEquipo(String informeEncuentroEquipo) {
        this.informeEncuentroEquipo = informeEncuentroEquipo;
    }

    public Encuentros getIdEncuentro() {
        return idEncuentro;
    }

    public void setIdEncuentro(Encuentros idEncuentro) {
        this.idEncuentro = idEncuentro;
    }

    public Equipo getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(Equipo idEquipo) {
        this.idEquipo = idEquipo;
    }

    public EstadoEncuentro getIdEstadoEncuentro() {
        return idEstadoEncuentro;
    }

    public void setIdEstadoEncuentro(EstadoEncuentro idEstadoEncuentro) {
        this.idEstadoEncuentro = idEstadoEncuentro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMarcadorEquipo != null ? idMarcadorEquipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarcadorEquipo)) {
            return false;
        }
        MarcadorEquipo other = (MarcadorEquipo) object;
        if ((this.idMarcadorEquipo == null && other.idMarcadorEquipo != null) || (this.idMarcadorEquipo != null && !this.idMarcadorEquipo.equals(other.idMarcadorEquipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.MarcadorEquipo[ idMarcadorEquipo=" + idMarcadorEquipo + " ]";
    }
    
}
