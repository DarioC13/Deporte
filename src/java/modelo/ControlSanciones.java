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
@Table(name = "control_sanciones")
@NamedQueries({
    @NamedQuery(name = "ControlSanciones.findAll", query = "SELECT c FROM ControlSanciones c")
    , @NamedQuery(name = "ControlSanciones.findByIdControlSanciones", query = "SELECT c FROM ControlSanciones c WHERE c.idControlSanciones = :idControlSanciones")
    , @NamedQuery(name = "ControlSanciones.findByObservaciones", query = "SELECT c FROM ControlSanciones c WHERE c.observaciones = :observaciones")})
public class ControlSanciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_control_sanciones")
    private Integer idControlSanciones;
    @Size(max = 45)
    @Column(name = "observaciones")
    private String observaciones;
    @JoinColumn(name = "id_encuentro", referencedColumnName = "id_encuentros")
    @ManyToOne
    private Encuentros idEncuentro;
    @JoinColumn(name = "id_control_pago", referencedColumnName = "id_control_de_pago")
    @ManyToOne
    private ControlDePago idControlPago;
    @JoinColumn(name = "id_persona", referencedColumnName = "id_persona")
    @ManyToOne
    private Persona idPersona;
    @JoinColumn(name = "id_sancion", referencedColumnName = "id_sanciones")
    @ManyToOne
    private Sancion idSancion;

    public ControlSanciones() {
    }

    public ControlSanciones(Integer idControlSanciones) {
        this.idControlSanciones = idControlSanciones;
    }

    public Integer getIdControlSanciones() {
        return idControlSanciones;
    }

    public void setIdControlSanciones(Integer idControlSanciones) {
        this.idControlSanciones = idControlSanciones;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Encuentros getIdEncuentro() {
        return idEncuentro;
    }

    public void setIdEncuentro(Encuentros idEncuentro) {
        this.idEncuentro = idEncuentro;
    }

    public ControlDePago getIdControlPago() {
        return idControlPago;
    }

    public void setIdControlPago(ControlDePago idControlPago) {
        this.idControlPago = idControlPago;
    }

    public Persona getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Persona idPersona) {
        this.idPersona = idPersona;
    }

    public Sancion getIdSancion() {
        return idSancion;
    }

    public void setIdSancion(Sancion idSancion) {
        this.idSancion = idSancion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idControlSanciones != null ? idControlSanciones.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ControlSanciones)) {
            return false;
        }
        ControlSanciones other = (ControlSanciones) object;
        if ((this.idControlSanciones == null && other.idControlSanciones != null) || (this.idControlSanciones != null && !this.idControlSanciones.equals(other.idControlSanciones))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.ControlSanciones[ idControlSanciones=" + idControlSanciones + " ]";
    }
    
}
