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
@Table(name = "control_de_pago")
@NamedQueries({
    @NamedQuery(name = "ControlDePago.findAll", query = "SELECT c FROM ControlDePago c")
    , @NamedQuery(name = "ControlDePago.findByIdControlDePago", query = "SELECT c FROM ControlDePago c WHERE c.idControlDePago = :idControlDePago")
    , @NamedQuery(name = "ControlDePago.findByControlPago", query = "SELECT c FROM ControlDePago c WHERE c.controlPago = :controlPago")
    , @NamedQuery(name = "ControlDePago.findByObservacion", query = "SELECT c FROM ControlDePago c WHERE c.observacion = :observacion")})
public class ControlDePago implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_control_de_pago")
    private Integer idControlDePago;
    @Size(max = 45)
    @Column(name = "control_pago")
    private String controlPago;
    @Size(max = 105)
    @Column(name = "observacion")
    private String observacion;
    @OneToMany(mappedBy = "idControlPago")
    private List<ControlSanciones> controlSancionesList;

    public ControlDePago() {
    }

    public ControlDePago(Integer idControlDePago) {
        this.idControlDePago = idControlDePago;
    }

    public Integer getIdControlDePago() {
        return idControlDePago;
    }

    public void setIdControlDePago(Integer idControlDePago) {
        this.idControlDePago = idControlDePago;
    }

    public String getControlPago() {
        return controlPago;
    }

    public void setControlPago(String controlPago) {
        this.controlPago = controlPago;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
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
        hash += (idControlDePago != null ? idControlDePago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ControlDePago)) {
            return false;
        }
        ControlDePago other = (ControlDePago) object;
        if ((this.idControlDePago == null && other.idControlDePago != null) || (this.idControlDePago != null && !this.idControlDePago.equals(other.idControlDePago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getControlPago();
    }
    
}
