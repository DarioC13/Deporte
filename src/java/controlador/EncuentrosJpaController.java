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
import modelo.AsginarCampeonatoCategoria;
import modelo.Esenarios;
import modelo.MarcadorJugador;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import modelo.ControlSanciones;
import modelo.ArbitrosEncuentros;
import modelo.Encuentros;
import modelo.MarcadorEquipo;

/**
 *
 * @author Francisco
 */
public class EncuentrosJpaController implements Serializable {

    public EncuentrosJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Encuentros encuentros) throws RollbackFailureException, Exception {
        if (encuentros.getMarcadorJugadorList() == null) {
            encuentros.setMarcadorJugadorList(new ArrayList<MarcadorJugador>());
        }
        if (encuentros.getControlSancionesList() == null) {
            encuentros.setControlSancionesList(new ArrayList<ControlSanciones>());
        }
        if (encuentros.getArbitrosEncuentrosList() == null) {
            encuentros.setArbitrosEncuentrosList(new ArrayList<ArbitrosEncuentros>());
        }
        if (encuentros.getMarcadorEquipoList() == null) {
            encuentros.setMarcadorEquipoList(new ArrayList<MarcadorEquipo>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            AsginarCampeonatoCategoria asignarCampeonatoCategoria = encuentros.getAsignarCampeonatoCategoria();
            if (asignarCampeonatoCategoria != null) {
                asignarCampeonatoCategoria = em.getReference(asignarCampeonatoCategoria.getClass(), asignarCampeonatoCategoria.getIdasginarCampeonatoCategoria());
                encuentros.setAsignarCampeonatoCategoria(asignarCampeonatoCategoria);
            }
            Esenarios idEsenario = encuentros.getIdEsenario();
            if (idEsenario != null) {
                idEsenario = em.getReference(idEsenario.getClass(), idEsenario.getIdEsenarios());
                encuentros.setIdEsenario(idEsenario);
            }
            List<MarcadorJugador> attachedMarcadorJugadorList = new ArrayList<MarcadorJugador>();
            for (MarcadorJugador marcadorJugadorListMarcadorJugadorToAttach : encuentros.getMarcadorJugadorList()) {
                marcadorJugadorListMarcadorJugadorToAttach = em.getReference(marcadorJugadorListMarcadorJugadorToAttach.getClass(), marcadorJugadorListMarcadorJugadorToAttach.getIdMarcador());
                attachedMarcadorJugadorList.add(marcadorJugadorListMarcadorJugadorToAttach);
            }
            encuentros.setMarcadorJugadorList(attachedMarcadorJugadorList);
            List<ControlSanciones> attachedControlSancionesList = new ArrayList<ControlSanciones>();
            for (ControlSanciones controlSancionesListControlSancionesToAttach : encuentros.getControlSancionesList()) {
                controlSancionesListControlSancionesToAttach = em.getReference(controlSancionesListControlSancionesToAttach.getClass(), controlSancionesListControlSancionesToAttach.getIdControlSanciones());
                attachedControlSancionesList.add(controlSancionesListControlSancionesToAttach);
            }
            encuentros.setControlSancionesList(attachedControlSancionesList);
            List<ArbitrosEncuentros> attachedArbitrosEncuentrosList = new ArrayList<ArbitrosEncuentros>();
            for (ArbitrosEncuentros arbitrosEncuentrosListArbitrosEncuentrosToAttach : encuentros.getArbitrosEncuentrosList()) {
                arbitrosEncuentrosListArbitrosEncuentrosToAttach = em.getReference(arbitrosEncuentrosListArbitrosEncuentrosToAttach.getClass(), arbitrosEncuentrosListArbitrosEncuentrosToAttach.getIdAlbitrosEncuentroscol());
                attachedArbitrosEncuentrosList.add(arbitrosEncuentrosListArbitrosEncuentrosToAttach);
            }
            encuentros.setArbitrosEncuentrosList(attachedArbitrosEncuentrosList);
            List<MarcadorEquipo> attachedMarcadorEquipoList = new ArrayList<MarcadorEquipo>();
            for (MarcadorEquipo marcadorEquipoListMarcadorEquipoToAttach : encuentros.getMarcadorEquipoList()) {
                marcadorEquipoListMarcadorEquipoToAttach = em.getReference(marcadorEquipoListMarcadorEquipoToAttach.getClass(), marcadorEquipoListMarcadorEquipoToAttach.getIdMarcadorEquipo());
                attachedMarcadorEquipoList.add(marcadorEquipoListMarcadorEquipoToAttach);
            }
            encuentros.setMarcadorEquipoList(attachedMarcadorEquipoList);
            em.persist(encuentros);
            if (asignarCampeonatoCategoria != null) {
                asignarCampeonatoCategoria.getEncuentrosList().add(encuentros);
                asignarCampeonatoCategoria = em.merge(asignarCampeonatoCategoria);
            }
            if (idEsenario != null) {
                idEsenario.getEncuentrosList().add(encuentros);
                idEsenario = em.merge(idEsenario);
            }
            for (MarcadorJugador marcadorJugadorListMarcadorJugador : encuentros.getMarcadorJugadorList()) {
                Encuentros oldIdEncuentrosOfMarcadorJugadorListMarcadorJugador = marcadorJugadorListMarcadorJugador.getIdEncuentros();
                marcadorJugadorListMarcadorJugador.setIdEncuentros(encuentros);
                marcadorJugadorListMarcadorJugador = em.merge(marcadorJugadorListMarcadorJugador);
                if (oldIdEncuentrosOfMarcadorJugadorListMarcadorJugador != null) {
                    oldIdEncuentrosOfMarcadorJugadorListMarcadorJugador.getMarcadorJugadorList().remove(marcadorJugadorListMarcadorJugador);
                    oldIdEncuentrosOfMarcadorJugadorListMarcadorJugador = em.merge(oldIdEncuentrosOfMarcadorJugadorListMarcadorJugador);
                }
            }
            for (ControlSanciones controlSancionesListControlSanciones : encuentros.getControlSancionesList()) {
                Encuentros oldIdEncuentroOfControlSancionesListControlSanciones = controlSancionesListControlSanciones.getIdEncuentro();
                controlSancionesListControlSanciones.setIdEncuentro(encuentros);
                controlSancionesListControlSanciones = em.merge(controlSancionesListControlSanciones);
                if (oldIdEncuentroOfControlSancionesListControlSanciones != null) {
                    oldIdEncuentroOfControlSancionesListControlSanciones.getControlSancionesList().remove(controlSancionesListControlSanciones);
                    oldIdEncuentroOfControlSancionesListControlSanciones = em.merge(oldIdEncuentroOfControlSancionesListControlSanciones);
                }
            }
            for (ArbitrosEncuentros arbitrosEncuentrosListArbitrosEncuentros : encuentros.getArbitrosEncuentrosList()) {
                Encuentros oldIdEncuentroOfArbitrosEncuentrosListArbitrosEncuentros = arbitrosEncuentrosListArbitrosEncuentros.getIdEncuentro();
                arbitrosEncuentrosListArbitrosEncuentros.setIdEncuentro(encuentros);
                arbitrosEncuentrosListArbitrosEncuentros = em.merge(arbitrosEncuentrosListArbitrosEncuentros);
                if (oldIdEncuentroOfArbitrosEncuentrosListArbitrosEncuentros != null) {
                    oldIdEncuentroOfArbitrosEncuentrosListArbitrosEncuentros.getArbitrosEncuentrosList().remove(arbitrosEncuentrosListArbitrosEncuentros);
                    oldIdEncuentroOfArbitrosEncuentrosListArbitrosEncuentros = em.merge(oldIdEncuentroOfArbitrosEncuentrosListArbitrosEncuentros);
                }
            }
            for (MarcadorEquipo marcadorEquipoListMarcadorEquipo : encuentros.getMarcadorEquipoList()) {
                Encuentros oldIdEncuentroOfMarcadorEquipoListMarcadorEquipo = marcadorEquipoListMarcadorEquipo.getIdEncuentro();
                marcadorEquipoListMarcadorEquipo.setIdEncuentro(encuentros);
                marcadorEquipoListMarcadorEquipo = em.merge(marcadorEquipoListMarcadorEquipo);
                if (oldIdEncuentroOfMarcadorEquipoListMarcadorEquipo != null) {
                    oldIdEncuentroOfMarcadorEquipoListMarcadorEquipo.getMarcadorEquipoList().remove(marcadorEquipoListMarcadorEquipo);
                    oldIdEncuentroOfMarcadorEquipoListMarcadorEquipo = em.merge(oldIdEncuentroOfMarcadorEquipoListMarcadorEquipo);
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

    public void edit(Encuentros encuentros) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Encuentros persistentEncuentros = em.find(Encuentros.class, encuentros.getIdEncuentros());
            AsginarCampeonatoCategoria asignarCampeonatoCategoriaOld = persistentEncuentros.getAsignarCampeonatoCategoria();
            AsginarCampeonatoCategoria asignarCampeonatoCategoriaNew = encuentros.getAsignarCampeonatoCategoria();
            Esenarios idEsenarioOld = persistentEncuentros.getIdEsenario();
            Esenarios idEsenarioNew = encuentros.getIdEsenario();
            List<MarcadorJugador> marcadorJugadorListOld = persistentEncuentros.getMarcadorJugadorList();
            List<MarcadorJugador> marcadorJugadorListNew = encuentros.getMarcadorJugadorList();
            List<ControlSanciones> controlSancionesListOld = persistentEncuentros.getControlSancionesList();
            List<ControlSanciones> controlSancionesListNew = encuentros.getControlSancionesList();
            List<ArbitrosEncuentros> arbitrosEncuentrosListOld = persistentEncuentros.getArbitrosEncuentrosList();
            List<ArbitrosEncuentros> arbitrosEncuentrosListNew = encuentros.getArbitrosEncuentrosList();
            List<MarcadorEquipo> marcadorEquipoListOld = persistentEncuentros.getMarcadorEquipoList();
            List<MarcadorEquipo> marcadorEquipoListNew = encuentros.getMarcadorEquipoList();
            if (asignarCampeonatoCategoriaNew != null) {
                asignarCampeonatoCategoriaNew = em.getReference(asignarCampeonatoCategoriaNew.getClass(), asignarCampeonatoCategoriaNew.getIdasginarCampeonatoCategoria());
                encuentros.setAsignarCampeonatoCategoria(asignarCampeonatoCategoriaNew);
            }
            if (idEsenarioNew != null) {
                idEsenarioNew = em.getReference(idEsenarioNew.getClass(), idEsenarioNew.getIdEsenarios());
                encuentros.setIdEsenario(idEsenarioNew);
            }
            List<MarcadorJugador> attachedMarcadorJugadorListNew = new ArrayList<MarcadorJugador>();
            for (MarcadorJugador marcadorJugadorListNewMarcadorJugadorToAttach : marcadorJugadorListNew) {
                marcadorJugadorListNewMarcadorJugadorToAttach = em.getReference(marcadorJugadorListNewMarcadorJugadorToAttach.getClass(), marcadorJugadorListNewMarcadorJugadorToAttach.getIdMarcador());
                attachedMarcadorJugadorListNew.add(marcadorJugadorListNewMarcadorJugadorToAttach);
            }
            marcadorJugadorListNew = attachedMarcadorJugadorListNew;
            encuentros.setMarcadorJugadorList(marcadorJugadorListNew);
            List<ControlSanciones> attachedControlSancionesListNew = new ArrayList<ControlSanciones>();
            for (ControlSanciones controlSancionesListNewControlSancionesToAttach : controlSancionesListNew) {
                controlSancionesListNewControlSancionesToAttach = em.getReference(controlSancionesListNewControlSancionesToAttach.getClass(), controlSancionesListNewControlSancionesToAttach.getIdControlSanciones());
                attachedControlSancionesListNew.add(controlSancionesListNewControlSancionesToAttach);
            }
            controlSancionesListNew = attachedControlSancionesListNew;
            encuentros.setControlSancionesList(controlSancionesListNew);
            List<ArbitrosEncuentros> attachedArbitrosEncuentrosListNew = new ArrayList<ArbitrosEncuentros>();
            for (ArbitrosEncuentros arbitrosEncuentrosListNewArbitrosEncuentrosToAttach : arbitrosEncuentrosListNew) {
                arbitrosEncuentrosListNewArbitrosEncuentrosToAttach = em.getReference(arbitrosEncuentrosListNewArbitrosEncuentrosToAttach.getClass(), arbitrosEncuentrosListNewArbitrosEncuentrosToAttach.getIdAlbitrosEncuentroscol());
                attachedArbitrosEncuentrosListNew.add(arbitrosEncuentrosListNewArbitrosEncuentrosToAttach);
            }
            arbitrosEncuentrosListNew = attachedArbitrosEncuentrosListNew;
            encuentros.setArbitrosEncuentrosList(arbitrosEncuentrosListNew);
            List<MarcadorEquipo> attachedMarcadorEquipoListNew = new ArrayList<MarcadorEquipo>();
            for (MarcadorEquipo marcadorEquipoListNewMarcadorEquipoToAttach : marcadorEquipoListNew) {
                marcadorEquipoListNewMarcadorEquipoToAttach = em.getReference(marcadorEquipoListNewMarcadorEquipoToAttach.getClass(), marcadorEquipoListNewMarcadorEquipoToAttach.getIdMarcadorEquipo());
                attachedMarcadorEquipoListNew.add(marcadorEquipoListNewMarcadorEquipoToAttach);
            }
            marcadorEquipoListNew = attachedMarcadorEquipoListNew;
            encuentros.setMarcadorEquipoList(marcadorEquipoListNew);
            encuentros = em.merge(encuentros);
            if (asignarCampeonatoCategoriaOld != null && !asignarCampeonatoCategoriaOld.equals(asignarCampeonatoCategoriaNew)) {
                asignarCampeonatoCategoriaOld.getEncuentrosList().remove(encuentros);
                asignarCampeonatoCategoriaOld = em.merge(asignarCampeonatoCategoriaOld);
            }
            if (asignarCampeonatoCategoriaNew != null && !asignarCampeonatoCategoriaNew.equals(asignarCampeonatoCategoriaOld)) {
                asignarCampeonatoCategoriaNew.getEncuentrosList().add(encuentros);
                asignarCampeonatoCategoriaNew = em.merge(asignarCampeonatoCategoriaNew);
            }
            if (idEsenarioOld != null && !idEsenarioOld.equals(idEsenarioNew)) {
                idEsenarioOld.getEncuentrosList().remove(encuentros);
                idEsenarioOld = em.merge(idEsenarioOld);
            }
            if (idEsenarioNew != null && !idEsenarioNew.equals(idEsenarioOld)) {
                idEsenarioNew.getEncuentrosList().add(encuentros);
                idEsenarioNew = em.merge(idEsenarioNew);
            }
            for (MarcadorJugador marcadorJugadorListOldMarcadorJugador : marcadorJugadorListOld) {
                if (!marcadorJugadorListNew.contains(marcadorJugadorListOldMarcadorJugador)) {
                    marcadorJugadorListOldMarcadorJugador.setIdEncuentros(null);
                    marcadorJugadorListOldMarcadorJugador = em.merge(marcadorJugadorListOldMarcadorJugador);
                }
            }
            for (MarcadorJugador marcadorJugadorListNewMarcadorJugador : marcadorJugadorListNew) {
                if (!marcadorJugadorListOld.contains(marcadorJugadorListNewMarcadorJugador)) {
                    Encuentros oldIdEncuentrosOfMarcadorJugadorListNewMarcadorJugador = marcadorJugadorListNewMarcadorJugador.getIdEncuentros();
                    marcadorJugadorListNewMarcadorJugador.setIdEncuentros(encuentros);
                    marcadorJugadorListNewMarcadorJugador = em.merge(marcadorJugadorListNewMarcadorJugador);
                    if (oldIdEncuentrosOfMarcadorJugadorListNewMarcadorJugador != null && !oldIdEncuentrosOfMarcadorJugadorListNewMarcadorJugador.equals(encuentros)) {
                        oldIdEncuentrosOfMarcadorJugadorListNewMarcadorJugador.getMarcadorJugadorList().remove(marcadorJugadorListNewMarcadorJugador);
                        oldIdEncuentrosOfMarcadorJugadorListNewMarcadorJugador = em.merge(oldIdEncuentrosOfMarcadorJugadorListNewMarcadorJugador);
                    }
                }
            }
            for (ControlSanciones controlSancionesListOldControlSanciones : controlSancionesListOld) {
                if (!controlSancionesListNew.contains(controlSancionesListOldControlSanciones)) {
                    controlSancionesListOldControlSanciones.setIdEncuentro(null);
                    controlSancionesListOldControlSanciones = em.merge(controlSancionesListOldControlSanciones);
                }
            }
            for (ControlSanciones controlSancionesListNewControlSanciones : controlSancionesListNew) {
                if (!controlSancionesListOld.contains(controlSancionesListNewControlSanciones)) {
                    Encuentros oldIdEncuentroOfControlSancionesListNewControlSanciones = controlSancionesListNewControlSanciones.getIdEncuentro();
                    controlSancionesListNewControlSanciones.setIdEncuentro(encuentros);
                    controlSancionesListNewControlSanciones = em.merge(controlSancionesListNewControlSanciones);
                    if (oldIdEncuentroOfControlSancionesListNewControlSanciones != null && !oldIdEncuentroOfControlSancionesListNewControlSanciones.equals(encuentros)) {
                        oldIdEncuentroOfControlSancionesListNewControlSanciones.getControlSancionesList().remove(controlSancionesListNewControlSanciones);
                        oldIdEncuentroOfControlSancionesListNewControlSanciones = em.merge(oldIdEncuentroOfControlSancionesListNewControlSanciones);
                    }
                }
            }
            for (ArbitrosEncuentros arbitrosEncuentrosListOldArbitrosEncuentros : arbitrosEncuentrosListOld) {
                if (!arbitrosEncuentrosListNew.contains(arbitrosEncuentrosListOldArbitrosEncuentros)) {
                    arbitrosEncuentrosListOldArbitrosEncuentros.setIdEncuentro(null);
                    arbitrosEncuentrosListOldArbitrosEncuentros = em.merge(arbitrosEncuentrosListOldArbitrosEncuentros);
                }
            }
            for (ArbitrosEncuentros arbitrosEncuentrosListNewArbitrosEncuentros : arbitrosEncuentrosListNew) {
                if (!arbitrosEncuentrosListOld.contains(arbitrosEncuentrosListNewArbitrosEncuentros)) {
                    Encuentros oldIdEncuentroOfArbitrosEncuentrosListNewArbitrosEncuentros = arbitrosEncuentrosListNewArbitrosEncuentros.getIdEncuentro();
                    arbitrosEncuentrosListNewArbitrosEncuentros.setIdEncuentro(encuentros);
                    arbitrosEncuentrosListNewArbitrosEncuentros = em.merge(arbitrosEncuentrosListNewArbitrosEncuentros);
                    if (oldIdEncuentroOfArbitrosEncuentrosListNewArbitrosEncuentros != null && !oldIdEncuentroOfArbitrosEncuentrosListNewArbitrosEncuentros.equals(encuentros)) {
                        oldIdEncuentroOfArbitrosEncuentrosListNewArbitrosEncuentros.getArbitrosEncuentrosList().remove(arbitrosEncuentrosListNewArbitrosEncuentros);
                        oldIdEncuentroOfArbitrosEncuentrosListNewArbitrosEncuentros = em.merge(oldIdEncuentroOfArbitrosEncuentrosListNewArbitrosEncuentros);
                    }
                }
            }
            for (MarcadorEquipo marcadorEquipoListOldMarcadorEquipo : marcadorEquipoListOld) {
                if (!marcadorEquipoListNew.contains(marcadorEquipoListOldMarcadorEquipo)) {
                    marcadorEquipoListOldMarcadorEquipo.setIdEncuentro(null);
                    marcadorEquipoListOldMarcadorEquipo = em.merge(marcadorEquipoListOldMarcadorEquipo);
                }
            }
            for (MarcadorEquipo marcadorEquipoListNewMarcadorEquipo : marcadorEquipoListNew) {
                if (!marcadorEquipoListOld.contains(marcadorEquipoListNewMarcadorEquipo)) {
                    Encuentros oldIdEncuentroOfMarcadorEquipoListNewMarcadorEquipo = marcadorEquipoListNewMarcadorEquipo.getIdEncuentro();
                    marcadorEquipoListNewMarcadorEquipo.setIdEncuentro(encuentros);
                    marcadorEquipoListNewMarcadorEquipo = em.merge(marcadorEquipoListNewMarcadorEquipo);
                    if (oldIdEncuentroOfMarcadorEquipoListNewMarcadorEquipo != null && !oldIdEncuentroOfMarcadorEquipoListNewMarcadorEquipo.equals(encuentros)) {
                        oldIdEncuentroOfMarcadorEquipoListNewMarcadorEquipo.getMarcadorEquipoList().remove(marcadorEquipoListNewMarcadorEquipo);
                        oldIdEncuentroOfMarcadorEquipoListNewMarcadorEquipo = em.merge(oldIdEncuentroOfMarcadorEquipoListNewMarcadorEquipo);
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
                Integer id = encuentros.getIdEncuentros();
                if (findEncuentros(id) == null) {
                    throw new NonexistentEntityException("The encuentros with id " + id + " no longer exists.");
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
            Encuentros encuentros;
            try {
                encuentros = em.getReference(Encuentros.class, id);
                encuentros.getIdEncuentros();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The encuentros with id " + id + " no longer exists.", enfe);
            }
            AsginarCampeonatoCategoria asignarCampeonatoCategoria = encuentros.getAsignarCampeonatoCategoria();
            if (asignarCampeonatoCategoria != null) {
                asignarCampeonatoCategoria.getEncuentrosList().remove(encuentros);
                asignarCampeonatoCategoria = em.merge(asignarCampeonatoCategoria);
            }
            Esenarios idEsenario = encuentros.getIdEsenario();
            if (idEsenario != null) {
                idEsenario.getEncuentrosList().remove(encuentros);
                idEsenario = em.merge(idEsenario);
            }
            List<MarcadorJugador> marcadorJugadorList = encuentros.getMarcadorJugadorList();
            for (MarcadorJugador marcadorJugadorListMarcadorJugador : marcadorJugadorList) {
                marcadorJugadorListMarcadorJugador.setIdEncuentros(null);
                marcadorJugadorListMarcadorJugador = em.merge(marcadorJugadorListMarcadorJugador);
            }
            List<ControlSanciones> controlSancionesList = encuentros.getControlSancionesList();
            for (ControlSanciones controlSancionesListControlSanciones : controlSancionesList) {
                controlSancionesListControlSanciones.setIdEncuentro(null);
                controlSancionesListControlSanciones = em.merge(controlSancionesListControlSanciones);
            }
            List<ArbitrosEncuentros> arbitrosEncuentrosList = encuentros.getArbitrosEncuentrosList();
            for (ArbitrosEncuentros arbitrosEncuentrosListArbitrosEncuentros : arbitrosEncuentrosList) {
                arbitrosEncuentrosListArbitrosEncuentros.setIdEncuentro(null);
                arbitrosEncuentrosListArbitrosEncuentros = em.merge(arbitrosEncuentrosListArbitrosEncuentros);
            }
            List<MarcadorEquipo> marcadorEquipoList = encuentros.getMarcadorEquipoList();
            for (MarcadorEquipo marcadorEquipoListMarcadorEquipo : marcadorEquipoList) {
                marcadorEquipoListMarcadorEquipo.setIdEncuentro(null);
                marcadorEquipoListMarcadorEquipo = em.merge(marcadorEquipoListMarcadorEquipo);
            }
            em.remove(encuentros);
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

    public List<Encuentros> findEncuentrosEntities() {
        return findEncuentrosEntities(true, -1, -1);
    }

    public List<Encuentros> findEncuentrosEntities(int maxResults, int firstResult) {
        return findEncuentrosEntities(false, maxResults, firstResult);
    }

    private List<Encuentros> findEncuentrosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Encuentros.class));
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

    public Encuentros findEncuentros(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Encuentros.class, id);
        } finally {
            em.close();
        }
    }

    public int getEncuentrosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Encuentros> rt = cq.from(Encuentros.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
