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
import modelo.ControlSanciones;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import modelo.ControlDePago;

/**
 *
 * @author Francisco
 */
public class ControlDePagoJpaController implements Serializable {

    public ControlDePagoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ControlDePago controlDePago) throws RollbackFailureException, Exception {
        if (controlDePago.getControlSancionesList() == null) {
            controlDePago.setControlSancionesList(new ArrayList<ControlSanciones>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<ControlSanciones> attachedControlSancionesList = new ArrayList<ControlSanciones>();
            for (ControlSanciones controlSancionesListControlSancionesToAttach : controlDePago.getControlSancionesList()) {
                controlSancionesListControlSancionesToAttach = em.getReference(controlSancionesListControlSancionesToAttach.getClass(), controlSancionesListControlSancionesToAttach.getIdControlSanciones());
                attachedControlSancionesList.add(controlSancionesListControlSancionesToAttach);
            }
            controlDePago.setControlSancionesList(attachedControlSancionesList);
            em.persist(controlDePago);
            for (ControlSanciones controlSancionesListControlSanciones : controlDePago.getControlSancionesList()) {
                ControlDePago oldIdControlPagoOfControlSancionesListControlSanciones = controlSancionesListControlSanciones.getIdControlPago();
                controlSancionesListControlSanciones.setIdControlPago(controlDePago);
                controlSancionesListControlSanciones = em.merge(controlSancionesListControlSanciones);
                if (oldIdControlPagoOfControlSancionesListControlSanciones != null) {
                    oldIdControlPagoOfControlSancionesListControlSanciones.getControlSancionesList().remove(controlSancionesListControlSanciones);
                    oldIdControlPagoOfControlSancionesListControlSanciones = em.merge(oldIdControlPagoOfControlSancionesListControlSanciones);
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

    public void edit(ControlDePago controlDePago) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ControlDePago persistentControlDePago = em.find(ControlDePago.class, controlDePago.getIdControlDePago());
            List<ControlSanciones> controlSancionesListOld = persistentControlDePago.getControlSancionesList();
            List<ControlSanciones> controlSancionesListNew = controlDePago.getControlSancionesList();
            List<ControlSanciones> attachedControlSancionesListNew = new ArrayList<ControlSanciones>();
            for (ControlSanciones controlSancionesListNewControlSancionesToAttach : controlSancionesListNew) {
                controlSancionesListNewControlSancionesToAttach = em.getReference(controlSancionesListNewControlSancionesToAttach.getClass(), controlSancionesListNewControlSancionesToAttach.getIdControlSanciones());
                attachedControlSancionesListNew.add(controlSancionesListNewControlSancionesToAttach);
            }
            controlSancionesListNew = attachedControlSancionesListNew;
            controlDePago.setControlSancionesList(controlSancionesListNew);
            controlDePago = em.merge(controlDePago);
            for (ControlSanciones controlSancionesListOldControlSanciones : controlSancionesListOld) {
                if (!controlSancionesListNew.contains(controlSancionesListOldControlSanciones)) {
                    controlSancionesListOldControlSanciones.setIdControlPago(null);
                    controlSancionesListOldControlSanciones = em.merge(controlSancionesListOldControlSanciones);
                }
            }
            for (ControlSanciones controlSancionesListNewControlSanciones : controlSancionesListNew) {
                if (!controlSancionesListOld.contains(controlSancionesListNewControlSanciones)) {
                    ControlDePago oldIdControlPagoOfControlSancionesListNewControlSanciones = controlSancionesListNewControlSanciones.getIdControlPago();
                    controlSancionesListNewControlSanciones.setIdControlPago(controlDePago);
                    controlSancionesListNewControlSanciones = em.merge(controlSancionesListNewControlSanciones);
                    if (oldIdControlPagoOfControlSancionesListNewControlSanciones != null && !oldIdControlPagoOfControlSancionesListNewControlSanciones.equals(controlDePago)) {
                        oldIdControlPagoOfControlSancionesListNewControlSanciones.getControlSancionesList().remove(controlSancionesListNewControlSanciones);
                        oldIdControlPagoOfControlSancionesListNewControlSanciones = em.merge(oldIdControlPagoOfControlSancionesListNewControlSanciones);
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
                Integer id = controlDePago.getIdControlDePago();
                if (findControlDePago(id) == null) {
                    throw new NonexistentEntityException("The controlDePago with id " + id + " no longer exists.");
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
            ControlDePago controlDePago;
            try {
                controlDePago = em.getReference(ControlDePago.class, id);
                controlDePago.getIdControlDePago();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The controlDePago with id " + id + " no longer exists.", enfe);
            }
            List<ControlSanciones> controlSancionesList = controlDePago.getControlSancionesList();
            for (ControlSanciones controlSancionesListControlSanciones : controlSancionesList) {
                controlSancionesListControlSanciones.setIdControlPago(null);
                controlSancionesListControlSanciones = em.merge(controlSancionesListControlSanciones);
            }
            em.remove(controlDePago);
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

    public List<ControlDePago> findControlDePagoEntities() {
        return findControlDePagoEntities(true, -1, -1);
    }

    public List<ControlDePago> findControlDePagoEntities(int maxResults, int firstResult) {
        return findControlDePagoEntities(false, maxResults, firstResult);
    }

    private List<ControlDePago> findControlDePagoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ControlDePago.class));
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

    public ControlDePago findControlDePago(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ControlDePago.class, id);
        } finally {
            em.close();
        }
    }

    public int getControlDePagoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ControlDePago> rt = cq.from(ControlDePago.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
