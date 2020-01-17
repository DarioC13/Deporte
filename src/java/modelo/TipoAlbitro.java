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
@Table(name = "tipo_albitro")
@NamedQueries({
    @NamedQuery(name = "TipoAlbitro.findAll", query = "SELECT t FROM TipoAlbitro t")
    , @NamedQuery(name = "TipoAlbitro.findByIdTipoAlbitro", query = "SELECT t FROM TipoAlbitro t WHERE t.idTipoAlbitro = :idTipoAlbitro")
    , @NamedQuery(name = "TipoAlbitro.findByTipoAlbitro", query = "SELECT t FROM TipoAlbitro t WHERE t.tipoAlbitro = :tipoAlbitro")
    , @NamedQuery(name = "TipoAlbitro.findByObservacion", query = "SELECT t FROM TipoAlbitro t WHERE t.observacion = :observacion")})
public class TipoAlbitro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_albitro")
    private Integer idTipoAlbitro;
    @Size(max = 45)
    @Column(name = "tipo_albitro")
    private String tipoAlbitro;
    @Size(max = 45)
    @Column(name = "observacion")
    private String observacion;
    @OneToMany(mappedBy = "idTipoAlbitro")
    private List<Albitros> albitrosList;

    public TipoAlbitro() {
    }

    public TipoAlbitro(Integer idTipoAlbitro) {
        this.idTipoAlbitro = idTipoAlbitro;
    }

    public Integer getIdTipoAlbitro() {
        return idTipoAlbitro;
    }

    public void setIdTipoAlbitro(Integer idTipoAlbitro) {
        this.idTipoAlbitro = idTipoAlbitro;
    }

    public String getTipoAlbitro() {
        return tipoAlbitro;
    }

    public void setTipoAlbitro(String tipoAlbitro) {
        this.tipoAlbitro = tipoAlbitro;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public List<Albitros> getAlbitrosList() {
        return albitrosList;
    }

    public void setAlbitrosList(List<Albitros> albitrosList) {
        this.albitrosList = albitrosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoAlbitro != null ? idTipoAlbitro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoAlbitro)) {
            return false;
        }
        TipoAlbitro other = (TipoAlbitro) object;
        if ((this.idTipoAlbitro == null && other.idTipoAlbitro != null) || (this.idTipoAlbitro != null && !this.idTipoAlbitro.equals(other.idTipoAlbitro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return tipoAlbitro;
    }
    
}
