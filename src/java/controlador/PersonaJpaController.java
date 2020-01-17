/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import controlador.exceptions.NonexistentEntityException;
import controlador.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.RolDeportivo;
import modelo.MarcadorJugador;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import modelo.ControlSanciones;
import modelo.Albitros;
import modelo.AsginarDignidades;
import modelo.Usuario;
import modelo.AsignarPersonaEquipo;
import modelo.Persona;

/**
 *
 * @author Francisco
 */
public class PersonaJpaController implements Serializable {

    public PersonaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Persona persona) throws RollbackFailureException, Exception {
        if (persona.getMarcadorJugadorList() == null) {
            persona.setMarcadorJugadorList(new ArrayList<MarcadorJugador>());
        }
        if (persona.getControlSancionesList() == null) {
            persona.setControlSancionesList(new ArrayList<ControlSanciones>());
        }
        if (persona.getAlbitrosList() == null) {
            persona.setAlbitrosList(new ArrayList<Albitros>());
        }
        if (persona.getAsginarDignidadesList() == null) {
            persona.setAsginarDignidadesList(new ArrayList<AsginarDignidades>());
        }
        if (persona.getUsuarioList() == null) {
            persona.setUsuarioList(new ArrayList<Usuario>());
        }
        if (persona.getAsignarPersonaEquipoList() == null) {
            persona.setAsignarPersonaEquipoList(new ArrayList<AsignarPersonaEquipo>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            RolDeportivo idRolDeportivo = persona.getIdRolDeportivo();
            if (idRolDeportivo != null) {
                idRolDeportivo = em.getReference(idRolDeportivo.getClass(), idRolDeportivo.getIdRolDeportivo());
                persona.setIdRolDeportivo(idRolDeportivo);
            }
            List<MarcadorJugador> attachedMarcadorJugadorList = new ArrayList<MarcadorJugador>();
            for (MarcadorJugador marcadorJugadorListMarcadorJugadorToAttach : persona.getMarcadorJugadorList()) {
                marcadorJugadorListMarcadorJugadorToAttach = em.getReference(marcadorJugadorListMarcadorJugadorToAttach.getClass(), marcadorJugadorListMarcadorJugadorToAttach.getIdMarcador());
                attachedMarcadorJugadorList.add(marcadorJugadorListMarcadorJugadorToAttach);
            }
            persona.setMarcadorJugadorList(attachedMarcadorJugadorList);
            List<ControlSanciones> attachedControlSancionesList = new ArrayList<ControlSanciones>();
            for (ControlSanciones controlSancionesListControlSancionesToAttach : persona.getControlSancionesList()) {
                controlSancionesListControlSancionesToAttach = em.getReference(controlSancionesListControlSancionesToAttach.getClass(), controlSancionesListControlSancionesToAttach.getIdControlSanciones());
                attachedControlSancionesList.add(controlSancionesListControlSancionesToAttach);
            }
            persona.setControlSancionesList(attachedControlSancionesList);
            List<Albitros> attachedAlbitrosList = new ArrayList<Albitros>();
            for (Albitros albitrosListAlbitrosToAttach : persona.getAlbitrosList()) {
                albitrosListAlbitrosToAttach = em.getReference(albitrosListAlbitrosToAttach.getClass(), albitrosListAlbitrosToAttach.getIdAlbitros());
                attachedAlbitrosList.add(albitrosListAlbitrosToAttach);
            }
            persona.setAlbitrosList(attachedAlbitrosList);
            List<AsginarDignidades> attachedAsginarDignidadesList = new ArrayList<AsginarDignidades>();
            for (AsginarDignidades asginarDignidadesListAsginarDignidadesToAttach : persona.getAsginarDignidadesList()) {
                asginarDignidadesListAsginarDignidadesToAttach = em.getReference(asginarDignidadesListAsginarDignidadesToAttach.getClass(), asginarDignidadesListAsginarDignidadesToAttach.getIdAsginarDignidades());
                attachedAsginarDignidadesList.add(asginarDignidadesListAsginarDignidadesToAttach);
            }
            persona.setAsginarDignidadesList(attachedAsginarDignidadesList);
            List<Usuario> attachedUsuarioList = new ArrayList<Usuario>();
            for (Usuario usuarioListUsuarioToAttach : persona.getUsuarioList()) {
                usuarioListUsuarioToAttach = em.getReference(usuarioListUsuarioToAttach.getClass(), usuarioListUsuarioToAttach.getIdUsuario());
                attachedUsuarioList.add(usuarioListUsuarioToAttach);
            }
            persona.setUsuarioList(attachedUsuarioList);
            List<AsignarPersonaEquipo> attachedAsignarPersonaEquipoList = new ArrayList<AsignarPersonaEquipo>();
            for (AsignarPersonaEquipo asignarPersonaEquipoListAsignarPersonaEquipoToAttach : persona.getAsignarPersonaEquipoList()) {
                asignarPersonaEquipoListAsignarPersonaEquipoToAttach = em.getReference(asignarPersonaEquipoListAsignarPersonaEquipoToAttach.getClass(), asignarPersonaEquipoListAsignarPersonaEquipoToAttach.getIdAsignarPersonaEquipo());
                attachedAsignarPersonaEquipoList.add(asignarPersonaEquipoListAsignarPersonaEquipoToAttach);
            }
            persona.setAsignarPersonaEquipoList(attachedAsignarPersonaEquipoList);
            em.persist(persona);
            if (idRolDeportivo != null) {
                idRolDeportivo.getPersonaList().add(persona);
                idRolDeportivo = em.merge(idRolDeportivo);
            }
            for (MarcadorJugador marcadorJugadorListMarcadorJugador : persona.getMarcadorJugadorList()) {
                Persona oldIdPersonaOfMarcadorJugadorListMarcadorJugador = marcadorJugadorListMarcadorJugador.getIdPersona();
                marcadorJugadorListMarcadorJugador.setIdPersona(persona);
                marcadorJugadorListMarcadorJugador = em.merge(marcadorJugadorListMarcadorJugador);
                if (oldIdPersonaOfMarcadorJugadorListMarcadorJugador != null) {
                    oldIdPersonaOfMarcadorJugadorListMarcadorJugador.getMarcadorJugadorList().remove(marcadorJugadorListMarcadorJugador);
                    oldIdPersonaOfMarcadorJugadorListMarcadorJugador = em.merge(oldIdPersonaOfMarcadorJugadorListMarcadorJugador);
                }
            }
            for (ControlSanciones controlSancionesListControlSanciones : persona.getControlSancionesList()) {
                Persona oldIdPersonaOfControlSancionesListControlSanciones = controlSancionesListControlSanciones.getIdPersona();
                controlSancionesListControlSanciones.setIdPersona(persona);
                controlSancionesListControlSanciones = em.merge(controlSancionesListControlSanciones);
                if (oldIdPersonaOfControlSancionesListControlSanciones != null) {
                    oldIdPersonaOfControlSancionesListControlSanciones.getControlSancionesList().remove(controlSancionesListControlSanciones);
                    oldIdPersonaOfControlSancionesListControlSanciones = em.merge(oldIdPersonaOfControlSancionesListControlSanciones);
                }
            }
            for (Albitros albitrosListAlbitros : persona.getAlbitrosList()) {
                Persona oldIdPersonaOfAlbitrosListAlbitros = albitrosListAlbitros.getIdPersona();
                albitrosListAlbitros.setIdPersona(persona);
                albitrosListAlbitros = em.merge(albitrosListAlbitros);
                if (oldIdPersonaOfAlbitrosListAlbitros != null) {
                    oldIdPersonaOfAlbitrosListAlbitros.getAlbitrosList().remove(albitrosListAlbitros);
                    oldIdPersonaOfAlbitrosListAlbitros = em.merge(oldIdPersonaOfAlbitrosListAlbitros);
                }
            }
            for (AsginarDignidades asginarDignidadesListAsginarDignidades : persona.getAsginarDignidadesList()) {
                Persona oldIdPersonaOfAsginarDignidadesListAsginarDignidades = asginarDignidadesListAsginarDignidades.getIdPersona();
                asginarDignidadesListAsginarDignidades.setIdPersona(persona);
                asginarDignidadesListAsginarDignidades = em.merge(asginarDignidadesListAsginarDignidades);
                if (oldIdPersonaOfAsginarDignidadesListAsginarDignidades != null) {
                    oldIdPersonaOfAsginarDignidadesListAsginarDignidades.getAsginarDignidadesList().remove(asginarDignidadesListAsginarDignidades);
                    oldIdPersonaOfAsginarDignidadesListAsginarDignidades = em.merge(oldIdPersonaOfAsginarDignidadesListAsginarDignidades);
                }
            }
            for (Usuario usuarioListUsuario : persona.getUsuarioList()) {
                Persona oldIdPersonaOfUsuarioListUsuario = usuarioListUsuario.getIdPersona();
                usuarioListUsuario.setIdPersona(persona);
                usuarioListUsuario = em.merge(usuarioListUsuario);
                if (oldIdPersonaOfUsuarioListUsuario != null) {
                    oldIdPersonaOfUsuarioListUsuario.getUsuarioList().remove(usuarioListUsuario);
                    oldIdPersonaOfUsuarioListUsuario = em.merge(oldIdPersonaOfUsuarioListUsuario);
                }
            }
            for (AsignarPersonaEquipo asignarPersonaEquipoListAsignarPersonaEquipo : persona.getAsignarPersonaEquipoList()) {
                Persona oldIdPersonaOfAsignarPersonaEquipoListAsignarPersonaEquipo = asignarPersonaEquipoListAsignarPersonaEquipo.getIdPersona();
                asignarPersonaEquipoListAsignarPersonaEquipo.setIdPersona(persona);
                asignarPersonaEquipoListAsignarPersonaEquipo = em.merge(asignarPersonaEquipoListAsignarPersonaEquipo);
                if (oldIdPersonaOfAsignarPersonaEquipoListAsignarPersonaEquipo != null) {
                    oldIdPersonaOfAsignarPersonaEquipoListAsignarPersonaEquipo.getAsignarPersonaEquipoList().remove(asignarPersonaEquipoListAsignarPersonaEquipo);
                    oldIdPersonaOfAsignarPersonaEquipoListAsignarPersonaEquipo = em.merge(oldIdPersonaOfAsignarPersonaEquipoListAsignarPersonaEquipo);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Persona persona) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Persona persistentPersona = em.find(Persona.class, persona.getIdPersona());
            RolDeportivo idRolDeportivoOld = persistentPersona.getIdRolDeportivo();
            RolDeportivo idRolDeportivoNew = persona.getIdRolDeportivo();
            List<MarcadorJugador> marcadorJugadorListOld = persistentPersona.getMarcadorJugadorList();
            List<MarcadorJugador> marcadorJugadorListNew = persona.getMarcadorJugadorList();
            List<ControlSanciones> controlSancionesListOld = persistentPersona.getControlSancionesList();
            List<ControlSanciones> controlSancionesListNew = persona.getControlSancionesList();
            List<Albitros> albitrosListOld = persistentPersona.getAlbitrosList();
            List<Albitros> albitrosListNew = persona.getAlbitrosList();
            List<AsginarDignidades> asginarDignidadesListOld = persistentPersona.getAsginarDignidadesList();
            List<AsginarDignidades> asginarDignidadesListNew = persona.getAsginarDignidadesList();
            List<Usuario> usuarioListOld = persistentPersona.getUsuarioList();
            List<Usuario> usuarioListNew = persona.getUsuarioList();
            List<AsignarPersonaEquipo> asignarPersonaEquipoListOld = persistentPersona.getAsignarPersonaEquipoList();
            List<AsignarPersonaEquipo> asignarPersonaEquipoListNew = persona.getAsignarPersonaEquipoList();
            if (idRolDeportivoNew != null) {
                idRolDeportivoNew = em.getReference(idRolDeportivoNew.getClass(), idRolDeportivoNew.getIdRolDeportivo());
                persona.setIdRolDeportivo(idRolDeportivoNew);
            }
            List<MarcadorJugador> attachedMarcadorJugadorListNew = new ArrayList<MarcadorJugador>();
            for (MarcadorJugador marcadorJugadorListNewMarcadorJugadorToAttach : marcadorJugadorListNew) {
                marcadorJugadorListNewMarcadorJugadorToAttach = em.getReference(marcadorJugadorListNewMarcadorJugadorToAttach.getClass(), marcadorJugadorListNewMarcadorJugadorToAttach.getIdMarcador());
                attachedMarcadorJugadorListNew.add(marcadorJugadorListNewMarcadorJugadorToAttach);
            }
            marcadorJugadorListNew = attachedMarcadorJugadorListNew;
            persona.setMarcadorJugadorList(marcadorJugadorListNew);
            List<ControlSanciones> attachedControlSancionesListNew = new ArrayList<ControlSanciones>();
            for (ControlSanciones controlSancionesListNewControlSancionesToAttach : controlSancionesListNew) {
                controlSancionesListNewControlSancionesToAttach = em.getReference(controlSancionesListNewControlSancionesToAttach.getClass(), controlSancionesListNewControlSancionesToAttach.getIdControlSanciones());
                attachedControlSancionesListNew.add(controlSancionesListNewControlSancionesToAttach);
            }
            controlSancionesListNew = attachedControlSancionesListNew;
            persona.setControlSancionesList(controlSancionesListNew);
            List<Albitros> attachedAlbitrosListNew = new ArrayList<Albitros>();
            for (Albitros albitrosListNewAlbitrosToAttach : albitrosListNew) {
                albitrosListNewAlbitrosToAttach = em.getReference(albitrosListNewAlbitrosToAttach.getClass(), albitrosListNewAlbitrosToAttach.getIdAlbitros());
                attachedAlbitrosListNew.add(albitrosListNewAlbitrosToAttach);
            }
            albitrosListNew = attachedAlbitrosListNew;
            persona.setAlbitrosList(albitrosListNew);
            List<AsginarDignidades> attachedAsginarDignidadesListNew = new ArrayList<AsginarDignidades>();
            for (AsginarDignidades asginarDignidadesListNewAsginarDignidadesToAttach : asginarDignidadesListNew) {
                asginarDignidadesListNewAsginarDignidadesToAttach = em.getReference(asginarDignidadesListNewAsginarDignidadesToAttach.getClass(), asginarDignidadesListNewAsginarDignidadesToAttach.getIdAsginarDignidades());
                attachedAsginarDignidadesListNew.add(asginarDignidadesListNewAsginarDignidadesToAttach);
            }
            asginarDignidadesListNew = attachedAsginarDignidadesListNew;
            persona.setAsginarDignidadesList(asginarDignidadesListNew);
            List<Usuario> attachedUsuarioListNew = new ArrayList<Usuario>();
            for (Usuario usuarioListNewUsuarioToAttach : usuarioListNew) {
                usuarioListNewUsuarioToAttach = em.getReference(usuarioListNewUsuarioToAttach.getClass(), usuarioListNewUsuarioToAttach.getIdUsuario());
                attachedUsuarioListNew.add(usuarioListNewUsuarioToAttach);
            }
            usuarioListNew = attachedUsuarioListNew;
            persona.setUsuarioList(usuarioListNew);
            List<AsignarPersonaEquipo> attachedAsignarPersonaEquipoListNew = new ArrayList<AsignarPersonaEquipo>();
            for (AsignarPersonaEquipo asignarPersonaEquipoListNewAsignarPersonaEquipoToAttach : asignarPersonaEquipoListNew) {
                asignarPersonaEquipoListNewAsignarPersonaEquipoToAttach = em.getReference(asignarPersonaEquipoListNewAsignarPersonaEquipoToAttach.getClass(), asignarPersonaEquipoListNewAsignarPersonaEquipoToAttach.getIdAsignarPersonaEquipo());
                attachedAsignarPersonaEquipoListNew.add(asignarPersonaEquipoListNewAsignarPersonaEquipoToAttach);
            }
            asignarPersonaEquipoListNew = attachedAsignarPersonaEquipoListNew;
            persona.setAsignarPersonaEquipoList(asignarPersonaEquipoListNew);
            persona = em.merge(persona);
            if (idRolDeportivoOld != null && !idRolDeportivoOld.equals(idRolDeportivoNew)) {
                idRolDeportivoOld.getPersonaList().remove(persona);
                idRolDeportivoOld = em.merge(idRolDeportivoOld);
            }
            if (idRolDeportivoNew != null && !idRolDeportivoNew.equals(idRolDeportivoOld)) {
                idRolDeportivoNew.getPersonaList().add(persona);
                idRolDeportivoNew = em.merge(idRolDeportivoNew);
            }
            for (MarcadorJugador marcadorJugadorListOldMarcadorJugador : marcadorJugadorListOld) {
                if (!marcadorJugadorListNew.contains(marcadorJugadorListOldMarcadorJugador)) {
                    marcadorJugadorListOldMarcadorJugador.setIdPersona(null);
                    marcadorJugadorListOldMarcadorJugador = em.merge(marcadorJugadorListOldMarcadorJugador);
                }
            }
            for (MarcadorJugador marcadorJugadorListNewMarcadorJugador : marcadorJugadorListNew) {
                if (!marcadorJugadorListOld.contains(marcadorJugadorListNewMarcadorJugador)) {
                    Persona oldIdPersonaOfMarcadorJugadorListNewMarcadorJugador = marcadorJugadorListNewMarcadorJugador.getIdPersona();
                    marcadorJugadorListNewMarcadorJugador.setIdPersona(persona);
                    marcadorJugadorListNewMarcadorJugador = em.merge(marcadorJugadorListNewMarcadorJugador);
                    if (oldIdPersonaOfMarcadorJugadorListNewMarcadorJugador != null && !oldIdPersonaOfMarcadorJugadorListNewMarcadorJugador.equals(persona)) {
                        oldIdPersonaOfMarcadorJugadorListNewMarcadorJugador.getMarcadorJugadorList().remove(marcadorJugadorListNewMarcadorJugador);
                        oldIdPersonaOfMarcadorJugadorListNewMarcadorJugador = em.merge(oldIdPersonaOfMarcadorJugadorListNewMarcadorJugador);
                    }
                }
            }
            for (ControlSanciones controlSancionesListOldControlSanciones : controlSancionesListOld) {
                if (!controlSancionesListNew.contains(controlSancionesListOldControlSanciones)) {
                    controlSancionesListOldControlSanciones.setIdPersona(null);
                    controlSancionesListOldControlSanciones = em.merge(controlSancionesListOldControlSanciones);
                }
            }
            for (ControlSanciones controlSancionesListNewControlSanciones : controlSancionesListNew) {
                if (!controlSancionesListOld.contains(controlSancionesListNewControlSanciones)) {
                    Persona oldIdPersonaOfControlSancionesListNewControlSanciones = controlSancionesListNewControlSanciones.getIdPersona();
                    controlSancionesListNewControlSanciones.setIdPersona(persona);
                    controlSancionesListNewControlSanciones = em.merge(controlSancionesListNewControlSanciones);
                    if (oldIdPersonaOfControlSancionesListNewControlSanciones != null && !oldIdPersonaOfControlSancionesListNewControlSanciones.equals(persona)) {
                        oldIdPersonaOfControlSancionesListNewControlSanciones.getControlSancionesList().remove(controlSancionesListNewControlSanciones);
                        oldIdPersonaOfControlSancionesListNewControlSanciones = em.merge(oldIdPersonaOfControlSancionesListNewControlSanciones);
                    }
                }
            }
            for (Albitros albitrosListOldAlbitros : albitrosListOld) {
                if (!albitrosListNew.contains(albitrosListOldAlbitros)) {
                    albitrosListOldAlbitros.setIdPersona(null);
                    albitrosListOldAlbitros = em.merge(albitrosListOldAlbitros);
                }
            }
            for (Albitros albitrosListNewAlbitros : albitrosListNew) {
                if (!albitrosListOld.contains(albitrosListNewAlbitros)) {
                    Persona oldIdPersonaOfAlbitrosListNewAlbitros = albitrosListNewAlbitros.getIdPersona();
                    albitrosListNewAlbitros.setIdPersona(persona);
                    albitrosListNewAlbitros = em.merge(albitrosListNewAlbitros);
                    if (oldIdPersonaOfAlbitrosListNewAlbitros != null && !oldIdPersonaOfAlbitrosListNewAlbitros.equals(persona)) {
                        oldIdPersonaOfAlbitrosListNewAlbitros.getAlbitrosList().remove(albitrosListNewAlbitros);
                        oldIdPersonaOfAlbitrosListNewAlbitros = em.merge(oldIdPersonaOfAlbitrosListNewAlbitros);
                    }
                }
            }
            for (AsginarDignidades asginarDignidadesListOldAsginarDignidades : asginarDignidadesListOld) {
                if (!asginarDignidadesListNew.contains(asginarDignidadesListOldAsginarDignidades)) {
                    asginarDignidadesListOldAsginarDignidades.setIdPersona(null);
                    asginarDignidadesListOldAsginarDignidades = em.merge(asginarDignidadesListOldAsginarDignidades);
                }
            }
            for (AsginarDignidades asginarDignidadesListNewAsginarDignidades : asginarDignidadesListNew) {
                if (!asginarDignidadesListOld.contains(asginarDignidadesListNewAsginarDignidades)) {
                    Persona oldIdPersonaOfAsginarDignidadesListNewAsginarDignidades = asginarDignidadesListNewAsginarDignidades.getIdPersona();
                    asginarDignidadesListNewAsginarDignidades.setIdPersona(persona);
                    asginarDignidadesListNewAsginarDignidades = em.merge(asginarDignidadesListNewAsginarDignidades);
                    if (oldIdPersonaOfAsginarDignidadesListNewAsginarDignidades != null && !oldIdPersonaOfAsginarDignidadesListNewAsginarDignidades.equals(persona)) {
                        oldIdPersonaOfAsginarDignidadesListNewAsginarDignidades.getAsginarDignidadesList().remove(asginarDignidadesListNewAsginarDignidades);
                        oldIdPersonaOfAsginarDignidadesListNewAsginarDignidades = em.merge(oldIdPersonaOfAsginarDignidadesListNewAsginarDignidades);
                    }
                }
            }
            for (Usuario usuarioListOldUsuario : usuarioListOld) {
                if (!usuarioListNew.contains(usuarioListOldUsuario)) {
                    usuarioListOldUsuario.setIdPersona(null);
                    usuarioListOldUsuario = em.merge(usuarioListOldUsuario);
                }
            }
            for (Usuario usuarioListNewUsuario : usuarioListNew) {
                if (!usuarioListOld.contains(usuarioListNewUsuario)) {
                    Persona oldIdPersonaOfUsuarioListNewUsuario = usuarioListNewUsuario.getIdPersona();
                    usuarioListNewUsuario.setIdPersona(persona);
                    usuarioListNewUsuario = em.merge(usuarioListNewUsuario);
                    if (oldIdPersonaOfUsuarioListNewUsuario != null && !oldIdPersonaOfUsuarioListNewUsuario.equals(persona)) {
                        oldIdPersonaOfUsuarioListNewUsuario.getUsuarioList().remove(usuarioListNewUsuario);
                        oldIdPersonaOfUsuarioListNewUsuario = em.merge(oldIdPersonaOfUsuarioListNewUsuario);
                    }
                }
            }
            for (AsignarPersonaEquipo asignarPersonaEquipoListOldAsignarPersonaEquipo : asignarPersonaEquipoListOld) {
                if (!asignarPersonaEquipoListNew.contains(asignarPersonaEquipoListOldAsignarPersonaEquipo)) {
                    asignarPersonaEquipoListOldAsignarPersonaEquipo.setIdPersona(null);
                    asignarPersonaEquipoListOldAsignarPersonaEquipo = em.merge(asignarPersonaEquipoListOldAsignarPersonaEquipo);
                }
            }
            for (AsignarPersonaEquipo asignarPersonaEquipoListNewAsignarPersonaEquipo : asignarPersonaEquipoListNew) {
                if (!asignarPersonaEquipoListOld.contains(asignarPersonaEquipoListNewAsignarPersonaEquipo)) {
                    Persona oldIdPersonaOfAsignarPersonaEquipoListNewAsignarPersonaEquipo = asignarPersonaEquipoListNewAsignarPersonaEquipo.getIdPersona();
                    asignarPersonaEquipoListNewAsignarPersonaEquipo.setIdPersona(persona);
                    asignarPersonaEquipoListNewAsignarPersonaEquipo = em.merge(asignarPersonaEquipoListNewAsignarPersonaEquipo);
                    if (oldIdPersonaOfAsignarPersonaEquipoListNewAsignarPersonaEquipo != null && !oldIdPersonaOfAsignarPersonaEquipoListNewAsignarPersonaEquipo.equals(persona)) {
                        oldIdPersonaOfAsignarPersonaEquipoListNewAsignarPersonaEquipo.getAsignarPersonaEquipoList().remove(asignarPersonaEquipoListNewAsignarPersonaEquipo);
                        oldIdPersonaOfAsignarPersonaEquipoListNewAsignarPersonaEquipo = em.merge(oldIdPersonaOfAsignarPersonaEquipoListNewAsignarPersonaEquipo);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = persona.getIdPersona();
                if (findPersona(id) == null) {
                    throw new NonexistentEntityException("The persona with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Persona persona;
            try {
                persona = em.getReference(Persona.class, id);
                persona.getIdPersona();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The persona with id " + id + " no longer exists.", enfe);
            }
            RolDeportivo idRolDeportivo = persona.getIdRolDeportivo();
            if (idRolDeportivo != null) {
                idRolDeportivo.getPersonaList().remove(persona);
                idRolDeportivo = em.merge(idRolDeportivo);
            }
            List<MarcadorJugador> marcadorJugadorList = persona.getMarcadorJugadorList();
            for (MarcadorJugador marcadorJugadorListMarcadorJugador : marcadorJugadorList) {
                marcadorJugadorListMarcadorJugador.setIdPersona(null);
                marcadorJugadorListMarcadorJugador = em.merge(marcadorJugadorListMarcadorJugador);
            }
            List<ControlSanciones> controlSancionesList = persona.getControlSancionesList();
            for (ControlSanciones controlSancionesListControlSanciones : controlSancionesList) {
                controlSancionesListControlSanciones.setIdPersona(null);
                controlSancionesListControlSanciones = em.merge(controlSancionesListControlSanciones);
            }
            List<Albitros> albitrosList = persona.getAlbitrosList();
            for (Albitros albitrosListAlbitros : albitrosList) {
                albitrosListAlbitros.setIdPersona(null);
                albitrosListAlbitros = em.merge(albitrosListAlbitros);
            }
            List<AsginarDignidades> asginarDignidadesList = persona.getAsginarDignidadesList();
            for (AsginarDignidades asginarDignidadesListAsginarDignidades : asginarDignidadesList) {
                asginarDignidadesListAsginarDignidades.setIdPersona(null);
                asginarDignidadesListAsginarDignidades = em.merge(asginarDignidadesListAsginarDignidades);
            }
            List<Usuario> usuarioList = persona.getUsuarioList();
            for (Usuario usuarioListUsuario : usuarioList) {
                usuarioListUsuario.setIdPersona(null);
                usuarioListUsuario = em.merge(usuarioListUsuario);
            }
            List<AsignarPersonaEquipo> asignarPersonaEquipoList = persona.getAsignarPersonaEquipoList();
            for (AsignarPersonaEquipo asignarPersonaEquipoListAsignarPersonaEquipo : asignarPersonaEquipoList) {
                asignarPersonaEquipoListAsignarPersonaEquipo.setIdPersona(null);
                asignarPersonaEquipoListAsignarPersonaEquipo = em.merge(asignarPersonaEquipoListAsignarPersonaEquipo);
            }
            em.remove(persona);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Persona> findPersonaEntities() {
        return findPersonaEntities(true, -1, -1);
    }

    public List<Persona> findPersonaEntities(int maxResults, int firstResult) {
        return findPersonaEntities(false, maxResults, firstResult);
    }

    private List<Persona> findPersonaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Persona.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Persona findPersona(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Persona.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Persona> rt = cq.from(Persona.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
