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
@Table(name = "albitros")
@NamedQueries({
    @NamedQuery(name = "Albitros.findAll", query = "SELECT a FROM Albitros a")
    , @NamedQuery(name = "Albitros.findByIdAlbitros", query = "SELECT a FROM Albitros a WHERE a.idAlbitros = :idAlbitros")
    , @NamedQuery(name = "Albitros.findByObservacion", query = "SELECT a FROM Albitros a WHERE a.observacion = :observacion")})
public class Albitros implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_albitros")
    private Integer idAlbitros;
    @Size(max = 45)
    @Column(name = "observacion")
    private String observacion;
    @OneToMany(mappedBy = "idAlbitro")
    private List<ArbitrosEncuentros> arbitrosEncuentrosList;
    @JoinColumn(name = "id_persona", referencedColumnName = "id_persona")
    @ManyToOne
    private Persona idPersona;
    @JoinColumn(name = "id_tipo_albitro", referencedColumnName = "id_tipo_albitro")
    @ManyToOne
    private TipoAlbitro idTipoAlbitro;

    public Albitros() {
    }

    public Albitros(Integer idAlbitros) {
        this.idAlbitros = idAlbitros;
    }

    public Integer getIdAlbitros() {
        return idAlbitros;
    }

    public void setIdAlbitros(Integer idAlbitros) {
        this.idAlbitros = idAlbitros;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public List<ArbitrosEncuentros> getArbitrosEncuentrosList() {
        return arbitrosEncuentrosList;
    }

    public void setArbitrosEncuentrosList(List<ArbitrosEncuentros> arbitrosEncuentrosList) {
        this.arbitrosEncuentrosList = arbitrosEncuentrosList;
    }

    public Persona getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Persona idPersona) {
        this.idPersona = idPersona;
    }

    public TipoAlbitro getIdTipoAlbitro() {
        return idTipoAlbitro;
    }

    public void setIdTipoAlbitro(TipoAlbitro idTipoAlbitro) {
        this.idTipoAlbitro = idTipoAlbitro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAlbitros != null ? idAlbitros.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Albitros)) {
            return false;
        }
        Albitros other = (Albitros) object;
        if ((this.idAlbitros == null && other.idAlbitros != null) || (this.idAlbitros != null && !this.idAlbitros.equals(other.idAlbitros))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return idPersona.getName()+" "+idPersona.getApellido();
    }
    
}
