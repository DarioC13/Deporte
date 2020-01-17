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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "persona")
@NamedQueries({
    @NamedQuery(name = "Persona.findAll", query = "SELECT p FROM Persona p")
    , @NamedQuery(name = "Persona.findByIdPersona", query = "SELECT p FROM Persona p WHERE p.idPersona = :idPersona")
    , @NamedQuery(name = "Persona.findByName", query = "SELECT p FROM Persona p WHERE p.name = :name")
    , @NamedQuery(name = "Persona.findByApellido", query = "SELECT p FROM Persona p WHERE p.apellido = :apellido")
    , @NamedQuery(name = "Persona.findByCedula", query = "SELECT p FROM Persona p WHERE p.cedula = :cedula")
    , @NamedQuery(name = "Persona.findByFechaNacimiento", query = "SELECT p FROM Persona p WHERE p.fechaNacimiento = :fechaNacimiento")
    , @NamedQuery(name = "Persona.findByCelular", query = "SELECT p FROM Persona p WHERE p.celular = :celular")
    , @NamedQuery(name = "Persona.findByCorreo", query = "SELECT p FROM Persona p WHERE p.correo = :correo")
    , @NamedQuery(name = "Persona.findByGenero", query = "SELECT p FROM Persona p WHERE p.genero = :genero")
    , @NamedQuery(name = "Persona.findByDireccion", query = "SELECT p FROM Persona p WHERE p.direccion = :direccion")
    , @NamedQuery(name = "Persona.findByFoto", query = "SELECT p FROM Persona p WHERE p.foto = :foto")
    , @NamedQuery(name = "Persona.findByNumCamistea", query = "SELECT p FROM Persona p WHERE p.numCamistea = :numCamistea")
    , @NamedQuery(name = "Persona.findByEstado", query = "SELECT p FROM Persona p WHERE p.estado = :estado")})
public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_persona")
    private Integer idPersona;
    @Size(max = 45)
    @Column(name = "name")
    private String name;
    @Size(max = 45)
    @Column(name = "apellido")
    private String apellido;
    @Size(max = 45)
    @Column(name = "cedula")
    private String cedula;
    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @Size(max = 45)
    @Column(name = "celular")
    private String celular;
    @Size(max = 45)
    @Column(name = "correo")
    private String correo;
    @Column(name = "genero")
    private Integer genero;
    @Size(max = 45)
    @Column(name = "direccion")
    private String direccion;
    @Size(max = 145)
    @Column(name = "foto")
    private String foto;
    @Column(name = "num_camistea")
    private Integer numCamistea;
    @Column(name = "estado")
    private Integer estado;
    @JoinColumn(name = "id_rol_deportivo", referencedColumnName = "id_rol_deportivo")
    @ManyToOne
    private RolDeportivo idRolDeportivo;
    @OneToMany(mappedBy = "idPersona")
    private List<MarcadorJugador> marcadorJugadorList;
    @OneToMany(mappedBy = "idPersona")
    private List<ControlSanciones> controlSancionesList;
    @OneToMany(mappedBy = "idPersona")
    private List<Albitros> albitrosList;
    @OneToMany(mappedBy = "idPersona")
    private List<AsginarDignidades> asginarDignidadesList;
    @OneToMany(mappedBy = "idPersona")
    private List<Usuario> usuarioList;
    @OneToMany(mappedBy = "idPersona")
    private List<AsignarPersonaEquipo> asignarPersonaEquipoList;

    public Persona() {
    }

    public Persona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Integer getGenero() {
        return genero;
    }

    public void setGenero(Integer genero) {
        this.genero = genero;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Integer getNumCamistea() {
        return numCamistea;
    }

    public void setNumCamistea(Integer numCamistea) {
        this.numCamistea = numCamistea;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public RolDeportivo getIdRolDeportivo() {
        return idRolDeportivo;
    }

    public void setIdRolDeportivo(RolDeportivo idRolDeportivo) {
        this.idRolDeportivo = idRolDeportivo;
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

    public List<Albitros> getAlbitrosList() {
        return albitrosList;
    }

    public void setAlbitrosList(List<Albitros> albitrosList) {
        this.albitrosList = albitrosList;
    }

    public List<AsginarDignidades> getAsginarDignidadesList() {
        return asginarDignidadesList;
    }

    public void setAsginarDignidadesList(List<AsginarDignidades> asginarDignidadesList) {
        this.asginarDignidadesList = asginarDignidadesList;
    }

    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    public List<AsignarPersonaEquipo> getAsignarPersonaEquipoList() {
        return asignarPersonaEquipoList;
    }

    public void setAsignarPersonaEquipoList(List<AsignarPersonaEquipo> asignarPersonaEquipoList) {
        this.asignarPersonaEquipoList = asignarPersonaEquipoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPersona != null ? idPersona.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Persona)) {
            return false;
        }
        Persona other = (Persona) object;
        if ((this.idPersona == null && other.idPersona != null) || (this.idPersona != null && !this.idPersona.equals(other.idPersona))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return   name + " "+ apellido;
    }
    
}
