/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import controlador.exceptions.NonexistentEntityException;
import controlador.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import modelo.Encuentros;
import modelo.ControlDePago;
import modelo.ControlSanciones;
import modelo.Persona;
import modelo.Sancion;

/**
 *
 * @author Francisco
 */
public class ControlSancionesJpaController implements Serializable {

    public ControlSancionesJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ControlSanciones controlSanciones) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Encuentros idEncuentro = controlSanciones.getIdEncuentro();
            if (idEncuentro != null) {
                idEncuentro = em.getReference(idEncuentro.getClass(), idEncuentro.getIdEncuentros());
                controlSanciones.setIdEncuentro(idEncuentro);
            }
            ControlDePago idControlPago = controlSanciones.getIdControlPago();
            if (idControlPago != null) {
                idControlPago = em.getReference(idControlPago.getClass(), idControlPago.getIdControlDePago());
                controlSanciones.setIdControlPago(idControlPago);
            }
            Persona idPersona = controlSanciones.getIdPersona();
            if (idPersona != null) {
                idPersona = em.getReference(idPersona.getClass(), idPersona.getIdPersona());
                controlSanciones.setIdPersona(idPersona);
            }
            Sancion idSancion = controlSanciones.getIdSancion();
            if (idSancion != null) {
                idSancion = em.getReference(idSancion.getClass(), idSancion.getIdSanciones());
                controlSanciones.setIdSancion(idSancion);
            }
            em.persist(controlSanciones);
            if (idEncuentro != null) {
                idEncuentro.getControlSancionesList().add(controlSanciones);
                idEncuentro = em.merge(idEncuentro);
            }
            if (idControlPago != null) {
                idControlPago.getControlSancionesList().add(controlSanciones);
                idControlPago = em.merge(idControlPago);
            }
            if (idPersona != null) {
                idPersona.getControlSancionesList().add(controlSanciones);
                idPersona = em.merge(idPersona);
            }
            if (idSancion != null) {
                idSancion.getControlSancionesList().add(controlSanciones);
                idSancion = em.merge(idSancion);
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

    public void edit(ControlSanciones controlSanciones) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ControlSanciones persistentControlSanciones = em.find(ControlSanciones.class, controlSanciones.getIdControlSanciones());
            Encuentros idEncuentroOld = persistentControlSanciones.getIdEncuentro();
            Encuentros idEncuentroNew = controlSanciones.getIdEncuentro();
            ControlDePago idControlPagoOld = persistentControlSanciones.getIdControlPago();
            ControlDePago idControlPagoNew = controlSanciones.getIdControlPago();
            Persona idPersonaOld = persistentControlSanciones.getIdPersona();
            Persona idPersonaNew = controlSanciones.getIdPersona();
            Sancion idSancionOld = persistentControlSanciones.getIdSancion();
            Sancion idSancionNew = controlSanciones.getIdSancion();
            if (idEncuentroNew != null) {
                idEncuentroNew = em.getReference(idEncuentroNew.getClass(), idEncuentroNew.getIdEncuentros());
                controlSanciones.setIdEncuentro(idEncuentroNew);
            }
            if (idControlPagoNew != null) {
                idControlPagoNew = em.getReference(idControlPagoNew.getClass(), idControlPagoNew.getIdControlDePago());
                controlSanciones.setIdControlPago(idControlPagoNew);
            }
            if (idPersonaNew != null) {
                idPersonaNew = em.getReference(idPersonaNew.getClass(), idPersonaNew.getIdPersona());
                controlSanciones.setIdPersona(idPersonaNew);
            }
            if (idSancionNew != null) {
                idSancionNew = em.getReference(idSancionNew.getClass(), idSancionNew.getIdSanciones());
                controlSanciones.setIdSancion(idSancionNew);
            }
            controlSanciones = em.merge(controlSanciones);
            if (idEncuentroOld != null && !idEncuentroOld.equals(idEncuentroNew)) {
                idEncuentroOld.getControlSancionesList().remove(controlSanciones);
                idEncuentroOld = em.merge(idEncuentroOld);
            }
            if (idEncuentroNew != null && !idEncuentroNew.equals(idEncuentroOld)) {
                idEncuentroNew.getControlSancionesList().add(controlSanciones);
                idEncuentroNew = em.merge(idEncuentroNew);
            }
            if (idControlPagoOld != null && !idControlPagoOld.equals(idControlPagoNew)) {
                idControlPagoOld.getControlSancionesList().remove(controlSanciones);
                idControlPagoOld = em.merge(idControlPagoOld);
            }
            if (idControlPagoNew != null && !idControlPagoNew.equals(idControlPagoOld)) {
                idControlPagoNew.getControlSancionesList().add(controlSanciones);
                idControlPagoNew = em.merge(idControlPagoNew);
            }
            if (idPersonaOld != null && !idPersonaOld.equals(idPersonaNew)) {
                idPersonaOld.getControlSancionesList().remove(controlSanciones);
                idPersonaOld = em.merge(idPersonaOld);
            }
            if (idPersonaNew != null && !idPersonaNew.equals(idPersonaOld)) {
                idPersonaNew.getControlSancionesList().add(controlSanciones);
                idPersonaNew = em.merge(idPersonaNew);
            }
            if (idSancionOld != null && !idSancionOld.equals(idSancionNew)) {
                idSancionOld.getControlSancionesList().remove(controlSanciones);
                idSancionOld = em.merge(idSancionOld);
            }
            if (idSancionNew != null && !idSancionNew.equals(idSancionOld)) {
                idSancionNew.getControlSancionesList().add(controlSanciones);
                idSancionNew = em.merge(idSancionNew);
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
                Integer id = controlSanciones.getIdControlSanciones();
                if (findControlSanciones(id) == null) {
                    throw new NonexistentEntityException("The controlSanciones with id " + id + " no longer exists.");
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
            ControlSanciones controlSanciones;
            try {
                controlSanciones = em.getReference(ControlSanciones.class, id);
                controlSanciones.getIdControlSanciones();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The controlSanciones with id " + id + " no longer exists.", enfe);
            }
            Encuentros idEncuentro = controlSanciones.getIdEncuentro();
            if (idEncuentro != null) {
                idEncuentro.getControlSancionesList().remove(controlSanciones);
                idEncuentro = em.merge(idEncuentro);
            }
            ControlDePago idControlPago = controlSanciones.getIdControlPago();
            if (idControlPago != null) {
                idControlPago.getControlSancionesList().remove(controlSanciones);
                idControlPago = em.merge(idControlPago);
            }
            Persona idPersona = controlSanciones.getIdPersona();
            if (idPersona != null) {
                idPersona.getControlSancionesList().remove(controlSanciones);
                idPersona = em.merge(idPersona);
            }
            Sancion idSancion = controlSanciones.getIdSancion();
            if (idSancion != null) {
                idSancion.getControlSancionesList().remove(controlSanciones);
                idSancion = em.merge(idSancion);
            }
            em.remove(controlSanciones);
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

    public List<ControlSanciones> findControlSancionesEntities() {
        return findControlSancionesEntities(true, -1, -1);
    }

    public List<ControlSanciones> findControlSancionesEntities(int maxResults, int firstResult) {
        return findControlSancionesEntities(false, maxResults, firstResult);
    }

    private List<ControlSanciones> findControlSancionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ControlSanciones.class));
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

    public ControlSanciones findControlSanciones(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ControlSanciones.class, id);
        } finally {
            em.close();
        }
    }

    public int getControlSancionesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ControlSanciones> rt = cq.from(ControlSanciones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
