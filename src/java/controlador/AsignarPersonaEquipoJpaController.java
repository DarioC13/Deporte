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
import modelo.AsignarPersonaEquipo;
import modelo.Persona;

/**
 *
 * @author Francisco
 */
public class AsignarPersonaEquipoJpaController implements Serializable {

    public AsignarPersonaEquipoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AsignarPersonaEquipo asignarPersonaEquipo) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Persona idPersona = asignarPersonaEquipo.getIdPersona();
            if (idPersona != null) {
                idPersona = em.getReference(idPersona.getClass(), idPersona.getIdPersona());
                asignarPersonaEquipo.setIdPersona(idPersona);
            }
            em.persist(asignarPersonaEquipo);
            if (idPersona != null) {
                idPersona.getAsignarPersonaEquipoList().add(asignarPersonaEquipo);
                idPersona = em.merge(idPersona);
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

    public void edit(AsignarPersonaEquipo asignarPersonaEquipo) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            AsignarPersonaEquipo persistentAsignarPersonaEquipo = em.find(AsignarPersonaEquipo.class, asignarPersonaEquipo.getIdAsignarPersonaEquipo());
            Persona idPersonaOld = persistentAsignarPersonaEquipo.getIdPersona();
            Persona idPersonaNew = asignarPersonaEquipo.getIdPersona();
            if (idPersonaNew != null) {
                idPersonaNew = em.getReference(idPersonaNew.getClass(), idPersonaNew.getIdPersona());
                asignarPersonaEquipo.setIdPersona(idPersonaNew);
            }
            asignarPersonaEquipo = em.merge(asignarPersonaEquipo);
            if (idPersonaOld != null && !idPersonaOld.equals(idPersonaNew)) {
                idPersonaOld.getAsignarPersonaEquipoList().remove(asignarPersonaEquipo);
                idPersonaOld = em.merge(idPersonaOld);
            }
            if (idPersonaNew != null && !idPersonaNew.equals(idPersonaOld)) {
                idPersonaNew.getAsignarPersonaEquipoList().add(asignarPersonaEquipo);
                idPersonaNew = em.merge(idPersonaNew);
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
                Integer id = asignarPersonaEquipo.getIdAsignarPersonaEquipo();
                if (findAsignarPersonaEquipo(id) == null) {
                    throw new NonexistentEntityException("The asignarPersonaEquipo with id " + id + " no longer exists.");
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
            AsignarPersonaEquipo asignarPersonaEquipo;
            try {
                asignarPersonaEquipo = em.getReference(AsignarPersonaEquipo.class, id);
                asignarPersonaEquipo.getIdAsignarPersonaEquipo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The asignarPersonaEquipo with id " + id + " no longer exists.", enfe);
            }
            Persona idPersona = asignarPersonaEquipo.getIdPersona();
            if (idPersona != null) {
                idPersona.getAsignarPersonaEquipoList().remove(asignarPersonaEquipo);
                idPersona = em.merge(idPersona);
            }
            em.remove(asignarPersonaEquipo);
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

    public List<AsignarPersonaEquipo> findAsignarPersonaEquipoEntities() {
        return findAsignarPersonaEquipoEntities(true, -1, -1);
    }

    public List<AsignarPersonaEquipo> findAsignarPersonaEquipoEntities(int maxResults, int firstResult) {
        return findAsignarPersonaEquipoEntities(false, maxResults, firstResult);
    }

    private List<AsignarPersonaEquipo> findAsignarPersonaEquipoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AsignarPersonaEquipo.class));
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

    public AsignarPersonaEquipo findAsignarPersonaEquipo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AsignarPersonaEquipo.class, id);
        } finally {
            em.close();
        }
    }

    public int getAsignarPersonaEquipoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AsignarPersonaEquipo> rt = cq.from(AsignarPersonaEquipo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
