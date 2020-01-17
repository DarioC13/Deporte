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
@Table(name = "marcador_jugador")
@NamedQueries({
    @NamedQuery(name = "MarcadorJugador.findAll", query = "SELECT m FROM MarcadorJugador m")
    , @NamedQuery(name = "MarcadorJugador.findByIdMarcador", query = "SELECT m FROM MarcadorJugador m WHERE m.idMarcador = :idMarcador")
    , @NamedQuery(name = "MarcadorJugador.findByGol", query = "SELECT m FROM MarcadorJugador m WHERE m.gol = :gol")
    , @NamedQuery(name = "MarcadorJugador.findByInformeJugador", query = "SELECT m FROM MarcadorJugador m WHERE m.informeJugador = :informeJugador")})
public class MarcadorJugador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_marcador")
    private Integer idMarcador;
    @Column(name = "gol")
    private Integer gol;
    @Size(max = 245)
    @Column(name = "informe_jugador")
    private String informeJugador;
    @JoinColumn(name = "id_encuentros", referencedColumnName = "id_encuentros")
    @ManyToOne
    private Encuentros idEncuentros;
    @JoinColumn(name = "id_persona", referencedColumnName = "id_persona")
    @ManyToOne
    private Persona idPersona;
    @JoinColumn(name = "id_sanciones", referencedColumnName = "id_sanciones")
    @ManyToOne
    private Sancion idSanciones;

    public MarcadorJugador() {
    }

    public MarcadorJugador(Integer idMarcador) {
        this.idMarcador = idMarcador;
    }

    public Integer getIdMarcador() {
        return idMarcador;
    }

    public void setIdMarcador(Integer idMarcador) {
        this.idMarcador = idMarcador;
    }

    public Integer getGol() {
        return gol;
    }

    public void setGol(Integer gol) {
        this.gol = gol;
    }

    public String getInformeJugador() {
        return informeJugador;
    }

    public void setInformeJugador(String informeJugador) {
        this.informeJugador = informeJugador;
    }

    public Encuentros getIdEncuentros() {
        return idEncuentros;
    }

    public void setIdEncuentros(Encuentros idEncuentros) {
        this.idEncuentros = idEncuentros;
    }

    public Persona getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Persona idPersona) {
        this.idPersona = idPersona;
    }

    public Sancion getIdSanciones() {
        return idSanciones;
    }

    public void setIdSanciones(Sancion idSanciones) {
        this.idSanciones = idSanciones;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMarcador != null ? idMarcador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarcadorJugador)) {
            return false;
        }
        MarcadorJugador other = (MarcadorJugador) object;
        if ((this.idMarcador == null && other.idMarcador != null) || (this.idMarcador != null && !this.idMarcador.equals(other.idMarcador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.MarcadorJugador[ idMarcador=" + idMarcador + " ]";
    }
    
}
