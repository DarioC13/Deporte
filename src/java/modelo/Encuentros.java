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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "encuentros")
@NamedQueries({
    @NamedQuery(name = "Encuentros.findAll", query = "SELECT e FROM Encuentros e")
    , @NamedQuery(name = "Encuentros.findByIdEncuentros", query = "SELECT e FROM Encuentros e WHERE e.idEncuentros = :idEncuentros")
    , @NamedQuery(name = "Encuentros.findByFechaEncuentro", query = "SELECT e FROM Encuentros e WHERE e.fechaEncuentro = :fechaEncuentro")
    , @NamedQuery(name = "Encuentros.findByHora", query = "SELECT e FROM Encuentros e WHERE e.hora = :hora")})
public class Encuentros implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_encuentros")
    private Integer idEncuentros;
    @Column(name = "fecha_encuentro")
    @Temporal(TemporalType.DATE)
    private Date fechaEncuentro;
    @Size(max = 45)
    @Column(name = "hora")
    private String hora;
    @OneToMany(mappedBy = "idEncuentros")
    private List<MarcadorJugador> marcadorJugadorList;
    @OneToMany(mappedBy = "idEncuentro")
    private List<ControlSanciones> controlSancionesList;
    @JoinColumn(name = "asignar_campeonato_categoria", referencedColumnName = "idasginar_campeonato_categoria")
    @ManyToOne
    private AsginarCampeonatoCategoria asignarCampeonatoCategoria;
    @JoinColumn(name = "id_equipo_a", referencedColumnName = "id_equipo")
    @ManyToOne
    private Equipo idEquipoA;
    @JoinColumn(name = "id_equipo_b", referencedColumnName = "id_equipo")
    @ManyToOne
    private Equipo idEquipoB;
    @JoinColumn(name = "id_esenario", referencedColumnName = "id_esenarios")
    @ManyToOne
    private Esenarios idEsenario;
    @OneToMany(mappedBy = "idEncuentro")
    private List<ArbitrosEncuentros> arbitrosEncuentrosList;
    @OneToMany(mappedBy = "idEncuentro")
    private List<MarcadorEquipo> marcadorEquipoList;

    public Encuentros() {
    }

    public Encuentros(Integer idEncuentros) {
        this.idEncuentros = idEncuentros;
    }

    public Integer getIdEncuentros() {
        return idEncuentros;
    }

    public void setIdEncuentros(Integer idEncuentros) {
        this.idEncuentros = idEncuentros;
    }

    public Date getFechaEncuentro() {
        return fechaEncuentro;
    }

    public void setFechaEncuentro(Date fechaEncuentro) {
        this.fechaEncuentro = fechaEncuentro;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public List<MarcadorJugador> getMarcadorJugadorList() {
        return marcadorJugadorList;
    }

    public void setMarcadorJugadorList(List<MarcadorJugador> marcadorJugadorList) {
        this.marcadorJugadorList = marcadorJugadorList;
    }

    public List<ControlSanciones> getControlSancionesList() {
        return controlSancionesList;
    }

    public void setControlSancionesList(List<ControlSanciones> controlSancionesList) {
        this.controlSancionesList = controlSancionesList;
    }

    public AsginarCampeonatoCategoria getAsignarCampeonatoCategoria() {
        return asignarCampeonatoCategoria;
    }

    public void setAsignarCampeonatoCategoria(AsginarCampeonatoCategoria asignarCampeonatoCategoria) {
        this.asignarCampeonatoCategoria = asignarCampeonatoCategoria;
    }

    public Equipo getIdEquipoA() {
        return idEquipoA;
    }

    public void setIdEquipoA(Equipo idEquipoA) {
        this.idEquipoA = idEquipoA;
    }

    public Equipo getIdEquipoB() {
        return idEquipoB;
    }

    public void setIdEquipoB(Equipo idEquipoB) {
        this.idEquipoB = idEquipoB;
    }

    public Esenarios getIdEsenario() {
        return idEsenario;
    }

    public void setIdEsenario(Esenarios idEsenario) {
        this.idEsenario = idEsenario;
    }

    public List<ArbitrosEncuentros> getArbitrosEncuentrosList() {
        return arbitrosEncuentrosList;
    }

    public void setArbitrosEncuentrosList(List<ArbitrosEncuentros> arbitrosEncuentrosList) {
        this.arbitrosEncuentrosList = arbitrosEncuentrosList;
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
        hash += (idEncuentros != null ? idEncuentros.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Encuentros)) {
            return false;
        }
        Encuentros other = (Encuentros) object;
        if ((this.idEncuentros == null && other.idEncuentros != null) || (this.idEncuentros != null && !this.idEncuentros.equals(other.idEncuentros))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getIdEquipoA().getNomEquipo()+" VS "+ getIdEquipoB().getNomEquipo()+" "+ getFechaEncuentro();
    }
    
}
