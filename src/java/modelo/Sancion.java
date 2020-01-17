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
@Table(name = "sancion")
@NamedQueries({
    @NamedQuery(name = "Sancion.findAll", query = "SELECT s FROM Sancion s")
    , @NamedQuery(name = "Sancion.findByIdSanciones", query = "SELECT s FROM Sancion s WHERE s.idSanciones = :idSanciones")
    , @NamedQuery(name = "Sancion.findBySancion", query = "SELECT s FROM Sancion s WHERE s.sancion = :sancion")
    , @NamedQuery(name = "Sancion.findByCosto", query = "SELECT s FROM Sancion s WHERE s.costo = :costo")})
public class Sancion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_sanciones")
    private Integer idSanciones;
    @Size(max = 255)
    @Column(name = "sancion")
    private String sancion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "costo")
    private Double costo;
    @OneToMany(mappedBy = "idSanciones")
    private List<MarcadorJugador> marcadorJugadorList;
    @OneToMany(mappedBy = "idSancion")
    private List<ControlSanciones> controlSancionesList;

    public Sancion() {
    }

    public Sancion(Integer idSanciones) {
        this.idSanciones = idSanciones;
    }

    public Integer getIdSanciones() {
        return idSanciones;
    }

    public void setIdSanciones(Integer idSanciones) {
        this.idSanciones = idSanciones;
    }

    public String getSancion() {
        return sancion;
    }

    public void setSancion(String sancion) {
        this.sancion = sancion;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSanciones != null ? idSanciones.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sancion)) {
            return false;
        }
        Sancion other = (Sancion) object;
        if ((this.idSanciones == null && other.idSanciones != null) || (this.idSanciones != null && !this.idSanciones.equals(other.idSanciones))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getSancion()+"  $"+ getCosto();
    }
    
}
