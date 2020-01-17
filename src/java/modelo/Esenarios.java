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
@Table(name = "esenarios")
@NamedQueries({
    @NamedQuery(name = "Esenarios.findAll", query = "SELECT e FROM Esenarios e")
    , @NamedQuery(name = "Esenarios.findByIdEsenarios", query = "SELECT e FROM Esenarios e WHERE e.idEsenarios = :idEsenarios")
    , @NamedQuery(name = "Esenarios.findByEsesario", query = "SELECT e FROM Esenarios e WHERE e.esesario = :esesario")
    , @NamedQuery(name = "Esenarios.findByObservacion", query = "SELECT e FROM Esenarios e WHERE e.observacion = :observacion")})
public class Esenarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_esenarios")
    private Integer idEsenarios;
    @Size(max = 45)
    @Column(name = "esesario")
    private String esesario;
    @Size(max = 45)
    @Column(name = "observacion")
    private String observacion;
    @OneToMany(mappedBy = "idEsenario")
    private List<Encuentros> encuentrosList;

    public Esenarios() {
    }

    public Esenarios(Integer idEsenarios) {
        this.idEsenarios = idEsenarios;
    }

    public Integer getIdEsenarios() {
        return idEsenarios;
    }

    public void setIdEsenarios(Integer idEsenarios) {
        this.idEsenarios = idEsenarios;
    }

    public String getEsesario() {
        return esesario;
    }

    public void setEsesario(String esesario) {
        this.esesario = esesario;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEsenarios != null ? idEsenarios.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Esenarios)) {
            return false;
        }
        Esenarios other = (Esenarios) object;
        if ((this.idEsenarios == null && other.idEsenarios != null) || (this.idEsenarios != null && !this.idEsenarios.equals(other.idEsenarios))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getEsesario();
    }
    
}
