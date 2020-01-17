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

/**
 *
 * @author Lenovo
 */
@Entity
@Table(name = "arbitros_encuentros")
@NamedQueries({
    @NamedQuery(name = "ArbitrosEncuentros.findAll", query = "SELECT a FROM ArbitrosEncuentros a")
    , @NamedQuery(name = "ArbitrosEncuentros.findByIdAlbitrosEncuentroscol", query = "SELECT a FROM ArbitrosEncuentros a WHERE a.idAlbitrosEncuentroscol = :idAlbitrosEncuentroscol")})
public class ArbitrosEncuentros implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_albitros_encuentroscol")
    private Integer idAlbitrosEncuentroscol;
    @JoinColumn(name = "id_albitro", referencedColumnName = "id_albitros")
    @ManyToOne
    private Albitros idAlbitro;
    @JoinColumn(name = "id_encuentro", referencedColumnName = "id_encuentros")
    @ManyToOne
    private Encuentros idEncuentro;

    public ArbitrosEncuentros() {
    }

    public ArbitrosEncuentros(Integer idAlbitrosEncuentroscol) {
        this.idAlbitrosEncuentroscol = idAlbitrosEncuentroscol;
    }

    public Integer getIdAlbitrosEncuentroscol() {
        return idAlbitrosEncuentroscol;
    }

    public void setIdAlbitrosEncuentroscol(Integer idAlbitrosEncuentroscol) {
        this.idAlbitrosEncuentroscol = idAlbitrosEncuentroscol;
    }

    public Albitros getIdAlbitro() {
        return idAlbitro;
    }

    public void setIdAlbitro(Albitros idAlbitro) {
        this.idAlbitro = idAlbitro;
    }

    public Encuentros getIdEncuentro() {
        return idEncuentro;
    }

    public void setIdEncuentro(Encuentros idEncuentro) {
        this.idEncuentro = idEncuentro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAlbitrosEncuentroscol != null ? idAlbitrosEncuentroscol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ArbitrosEncuentros)) {
            return false;
        }
        ArbitrosEncuentros other = (ArbitrosEncuentros) object;
        if ((this.idAlbitrosEncuentroscol == null && other.idAlbitrosEncuentroscol != null) || (this.idAlbitrosEncuentroscol != null && !this.idAlbitrosEncuentroscol.equals(other.idAlbitrosEncuentroscol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.ArbitrosEncuentros[ idAlbitrosEncuentroscol=" + idAlbitrosEncuentroscol + " ]";
    }
    
}
