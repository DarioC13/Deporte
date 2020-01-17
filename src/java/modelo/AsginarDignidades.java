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
@Table(name = "asginar_dignidades")
@NamedQueries({
    @NamedQuery(name = "AsginarDignidades.findAll", query = "SELECT a FROM AsginarDignidades a")
    , @NamedQuery(name = "AsginarDignidades.findByIdAsginarDignidades", query = "SELECT a FROM AsginarDignidades a WHERE a.idAsginarDignidades = :idAsginarDignidades")
    , @NamedQuery(name = "AsginarDignidades.findByObervacion", query = "SELECT a FROM AsginarDignidades a WHERE a.obervacion = :obervacion")})
public class AsginarDignidades implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_asginar_dignidades")
    private Integer idAsginarDignidades;
    @Size(max = 145)
    @Column(name = "obervacion")
    private String obervacion;
    @JoinColumn(name = "id_dignidades", referencedColumnName = "id_digninades")
    @ManyToOne
    private Dignidades idDignidades;
    @JoinColumn(name = "id_persona", referencedColumnName = "id_persona")
    @ManyToOne
    private Persona idPersona;

    public AsginarDignidades() {
    }

    public AsginarDignidades(Integer idAsginarDignidades) {
        this.idAsginarDignidades = idAsginarDignidades;
    }

    public Integer getIdAsginarDignidades() {
        return idAsginarDignidades;
    }

    public void setIdAsginarDignidades(Integer idAsginarDignidades) {
        this.idAsginarDignidades = idAsginarDignidades;
    }

    public String getObervacion() {
        return obervacion;
    }

    public void setObervacion(String obervacion) {
        this.obervacion = obervacion;
    }

    public Dignidades getIdDignidades() {
        return idDignidades;
    }

    public void setIdDignidades(Dignidades idDignidades) {
        this.idDignidades = idDignidades;
    }

    public Persona getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Persona idPersona) {
        this.idPersona = idPersona;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAsginarDignidades != null ? idAsginarDignidades.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AsginarDignidades)) {
            return false;
        }
        AsginarDignidades other = (AsginarDignidades) object;
        if ((this.idAsginarDignidades == null && other.idAsginarDignidades != null) || (this.idAsginarDignidades != null && !this.idAsginarDignidades.equals(other.idAsginarDignidades))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.AsginarDignidades[ idAsginarDignidades=" + idAsginarDignidades + " ]";
    }
    
}
