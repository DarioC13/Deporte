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
import modelo.MarcadorJugador;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import modelo.ControlSanciones;
import modelo.Sancion;

/**
 *
 * @author Francisco
 */
public class SancionJpaController implements Serializable {

    public SancionJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sancion sancion) throws RollbackFailureException, Exception {
        if (sancion.getMarcadorJugadorList() == null) {
            sancion.setMarcadorJugadorList(new ArrayList<MarcadorJugador>());
        }
        if (sancion.getControlSancionesList() == null) {
            sancion.setControlSancionesList(new ArrayList<ControlSanciones>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<MarcadorJugador> attachedMarcadorJugadorList = new ArrayList<MarcadorJugador>();
            for (MarcadorJugador marcadorJugadorListMarcadorJugadorToAttach : sancion.getMarcadorJugadorList()) {
                marcadorJugadorListMarcadorJugadorToAttach = em.getReference(marcadorJugadorListMarcadorJugadorToAttach.getClass(), marcadorJugadorListMarcadorJugadorToAttach.getIdMarcador());
                attachedMarcadorJugadorList.add(marcadorJugadorListMarcadorJugadorToAttach);
            }
            sancion.setMarcadorJugadorList(attachedMarcadorJugadorList);
            List<ControlSanciones> attachedControlSancionesList = new ArrayList<ControlSanciones>();
            for (ControlSanciones controlSancionesListControlSancionesToAttach : sancion.getControlSancionesList()) {
                controlSancionesListControlSancionesToAttach = em.getReference(controlSancionesListControlSancionesToAttach.getClass(), controlSancionesListControlSancionesToAttach.getIdControlSanciones());
                attachedControlSancionesList.add(controlSancionesListControlSancionesToAttach);
            }
            sancion.setControlSancionesList(attachedControlSancionesList);
            em.persist(sancion);
            for (MarcadorJugador marcadorJugadorListMarcadorJugador : sancion.getMarcadorJugadorList()) {
                Sancion oldIdSancionesOfMarcadorJugadorListMarcadorJugador = marcadorJugadorListMarcadorJugador.getIdSanciones();
                marcadorJugadorListMarcadorJugador.setIdSanciones(sancion);
                marcadorJugadorListMarcadorJugador = em.merge(marcadorJugadorListMarcadorJugador);
                if (oldIdSancionesOfMarcadorJugadorListMarcadorJugador != null) {
                    oldIdSancionesOfMarcadorJugadorListMarcadorJugador.getMarcadorJugadorList().remove(marcadorJugadorListMarcadorJugador);
                    oldIdSancionesOfMarcadorJugadorListMarcadorJugador = em.merge(oldIdSancionesOfMarcadorJugadorListMarcadorJugador);
                }
            }
            for (ControlSanciones controlSancionesListControlSanciones : sancion.getControlSancionesList()) {
                Sancion oldIdSancionOfControlSancionesListControlSanciones = controlSancionesListControlSanciones.getIdSancion();
                controlSancionesListControlSanciones.setIdSancion(sancion);
                controlSancionesListControlSanciones = em.merge(controlSancionesListControlSanciones);
                if (oldIdSancionOfControlSancionesListControlSanciones != null) {
                    oldIdSancionOfControlSancionesListControlSanciones.getControlSancionesList().remove(controlSancionesListControlSanciones);
                    oldIdSancionOfControlSancionesListControlSanciones = em.merge(oldIdSancionOfControlSancionesListControlSanciones);
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

    public void edit(Sancion sancion) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Sancion persistentSancion = em.find(Sancion.class, sancion.getIdSanciones());
            List<MarcadorJugador> marcadorJugadorListOld = persistentSancion.getMarcadorJugadorList();
            List<MarcadorJugador> marcadorJugadorListNew = sancion.getMarcadorJugadorList();
            List<ControlSanciones> controlSancionesListOld = persistentSancion.getControlSancionesList();
            List<ControlSanciones> controlSancionesListNew = sancion.getControlSancionesList();
            List<MarcadorJugador> attachedMarcadorJugadorListNew = new ArrayList<MarcadorJugador>();
            for (MarcadorJugador marcadorJugadorListNewMarcadorJugadorToAttach : marcadorJugadorListNew) {
                marcadorJugadorListNewMarcadorJugadorToAttach = em.getReference(marcadorJugadorListNewMarcadorJugadorToAttach.getClass(), marcadorJugadorListNewMarcadorJugadorToAttach.getIdMarcador());
                attachedMarcadorJugadorListNew.add(marcadorJugadorListNewMarcadorJugadorToAttach);
            }
            marcadorJugadorListNew = attachedMarcadorJugadorListNew;
            sancion.setMarcadorJugadorList(marcadorJugadorListNew);
            List<ControlSanciones> attachedControlSancionesListNew = new ArrayList<ControlSanciones>();
            for (ControlSanciones controlSancionesListNewControlSancionesToAttach : controlSancionesListNew) {
                controlSancionesListNewControlSancionesToAttach = em.getReference(controlSancionesListNewControlSancionesToAttach.getClass(), controlSancionesListNewControlSancionesToAttach.getIdControlSanciones());
                attachedControlSancionesListNew.add(controlSancionesListNewControlSancionesToAttach);
            }
            controlSancionesListNew = attachedControlSancionesListNew;
            sancion.setControlSancionesList(controlSancionesListNew);
            sancion = em.merge(sancion);
            for (MarcadorJugador marcadorJugadorListOldMarcadorJugador : marcadorJugadorListOld) {
                if (!marcadorJugadorListNew.contains(marcadorJugadorListOldMarcadorJugador)) {
                    marcadorJugadorListOldMarcadorJugador.setIdSanciones(null);
                    marcadorJugadorListOldMarcadorJugador = em.merge(marcadorJugadorListOldMarcadorJugador);
                }
            }
            for (MarcadorJugador marcadorJugadorListNewMarcadorJugador : marcadorJugadorListNew) {
                if (!marcadorJugadorListOld.contains(marcadorJugadorListNewMarcadorJugador)) {
                    Sancion oldIdSancionesOfMarcadorJugadorListNewMarcadorJugador = marcadorJugadorListNewMarcadorJugador.getIdSanciones();
                    marcadorJugadorListNewMarcadorJugador.setIdSanciones(sancion);
                    marcadorJugadorListNewMarcadorJugador = em.merge(marcadorJugadorListNewMarcadorJugador);
                    if (oldIdSancionesOfMarcadorJugadorListNewMarcadorJugador != null && !oldIdSancionesOfMarcadorJugadorListNewMarcadorJugador.equals(sancion)) {
                        oldIdSancionesOfMarcadorJugadorListNewMarcadorJugador.getMarcadorJugadorList().remove(marcadorJugadorListNewMarcadorJugador);
                        oldIdSancionesOfMarcadorJugadorListNewMarcadorJugador = em.merge(oldIdSancionesOfMarcadorJugadorListNewMarcadorJugador);
                    }
                }
            }
            for (ControlSanciones controlSancionesListOldControlSanciones : controlSancionesListOld) {
                if (!controlSancionesListNew.contains(controlSancionesListOldControlSanciones)) {
                    controlSancionesListOldControlSanciones.setIdSancion(null);
                    controlSancionesListOldControlSanciones = em.merge(controlSancionesListOldControlSanciones);
                }
            }
            for (ControlSanciones controlSancionesListNewControlSanciones : controlSancionesListNew) {
                if (!controlSancionesListOld.contains(controlSancionesListNewControlSanciones)) {
                    Sancion oldIdSancionOfControlSancionesListNewControlSanciones = controlSancionesListNewControlSanciones.getIdSancion();
                    controlSancionesListNewControlSanciones.setIdSancion(sancion);
                    controlSancionesListNewControlSanciones = em.merge(controlSancionesListNewControlSanciones);
                    if (oldIdSancionOfControlSancionesListNewControlSanciones != null && !oldIdSancionOfControlSancionesListNewControlSanciones.equals(sancion)) {
                        oldIdSancionOfControlSancionesListNewControlSanciones.getControlSancionesList().remove(controlSancionesListNewControlSanciones);
                        oldIdSancionOfControlSancionesListNewControlSanciones = em.merge(oldIdSancionOfControlSancionesListNewControlSanciones);
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
                Integer id = sancion.getIdSanciones();
                if (findSancion(id) == null) {
                    throw new NonexistentEntityException("The sancion with id " + id + " no longer exists.");
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
            Sancion sancion;
            try {
                sancion = em.getReference(Sancion.class, id);
                sancion.getIdSanciones();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sancion with id " + id + " no longer exists.", enfe);
            }
            List<MarcadorJugador> marcadorJugadorList = sancion.getMarcadorJugadorList();
            for (MarcadorJugador marcadorJugadorListMarcadorJugador : marcadorJugadorList) {
                marcadorJugadorListMarcadorJugador.setIdSanciones(null);
                marcadorJugadorListMarcadorJugador = em.merge(marcadorJugadorListMarcadorJugador);
            }
            List<ControlSanciones> controlSancionesList = sancion.getControlSancionesList();
            for (ControlSanciones controlSancionesListControlSanciones : controlSancionesList) {
                controlSancionesListControlSanciones.setIdSancion(null);
                controlSancionesListControlSanciones = em.merge(controlSancionesListControlSanciones);
            }
            em.remove(sancion);
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

    public List<Sancion> findSancionEntities() {
        return findSancionEntities(true, -1, -1);
    }

    public List<Sancion> findSancionEntities(int maxResults, int firstResult) {
        return findSancionEntities(false, maxResults, firstResult);
    }

    private List<Sancion> findSancionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sancion.class));
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

    public Sancion findSancion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sancion.class, id);
        } finally {
            em.close();
        }
    }

    public int getSancionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sancion> rt = cq.from(Sancion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
