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
@Table(name = "dignidades")
@NamedQueries({
    @NamedQuery(name = "Dignidades.findAll", query = "SELECT d FROM Dignidades d")
    , @NamedQuery(name = "Dignidades.findByIdDigninades", query = "SELECT d FROM Dignidades d WHERE d.idDigninades = :idDigninades")
    , @NamedQuery(name = "Dignidades.findByCargo", query = "SELECT d FROM Dignidades d WHERE d.cargo = :cargo")
    , @NamedQuery(name = "Dignidades.findByDescipcion", query = "SELECT d FROM Dignidades d WHERE d.descipcion = :descipcion")
    , @NamedQuery(name = "Dignidades.findByFechaRegistro", query = "SELECT d FROM Dignidades d WHERE d.fechaRegistro = :fechaRegistro")})
public class Dignidades implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_digninades")
    private Integer idDigninades;
    @Size(max = 45)
    @Column(name = "cargo")
    private String cargo;
    @Size(max = 205)
    @Column(name = "descipcion")
    private String descipcion;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @OneToMany(mappedBy = "idDignidades")
    private List<AsginarDignidades> asginarDignidadesList;

    public Dignidades() {
    }

    public Dignidades(Integer idDigninades) {
        this.idDigninades = idDigninades;
    }

    public Integer getIdDigninades() {
        return idDigninades;
    }

    public void setIdDigninades(Integer idDigninades) {
        this.idDigninades = idDigninades;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getDescipcion() {
        return descipcion;
    }

    public void setDescipcion(String descipcion) {
        this.descipcion = descipcion;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public List<AsginarDignidades> getAsginarDignidadesList() {
        return asginarDignidadesList;
    }

    public void setAsginarDignidadesList(List<AsginarDignidades> asginarDignidadesList) {
        this.asginarDignidadesList = asginarDignidadesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDigninades != null ? idDigninades.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dignidades)) {
            return false;
        }
        Dignidades other = (Dignidades) object;
        if ((this.idDigninades == null && other.idDigninades != null) || (this.idDigninades != null && !this.idDigninades.equals(other.idDigninades))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getCargo();
    }
    
}
