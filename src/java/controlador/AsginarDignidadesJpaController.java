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
import modelo.AsginarDignidades;
import modelo.Dignidades;
import modelo.Persona;

/**
 *
 * @author Francisco
 */
public class AsginarDignidadesJpaController implements Serializable {

    public AsginarDignidadesJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AsginarDignidades asginarDignidades) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Dignidades idDignidades = asginarDignidades.getIdDignidades();
            if (idDignidades != null) {
                idDignidades = em.getReference(idDignidades.getClass(), idDignidades.getIdDigninades());
                asginarDignidades.setIdDignidades(idDignidades);
            }
            Persona idPersona = asginarDignidades.getIdPersona();
            if (idPersona != null) {
                idPersona = em.getReference(idPersona.getClass(), idPersona.getIdPersona());
                asginarDignidades.setIdPersona(idPersona);
            }
            em.persist(asginarDignidades);
            if (idDignidades != null) {
                idDignidades.getAsginarDignidadesList().add(asginarDignidades);
                idDignidades = em.merge(idDignidades);
            }
            if (idPersona != null) {
                idPersona.getAsginarDignidadesList().add(asginarDignidades);
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

    public void edit(AsginarDignidades asginarDignidades) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            AsginarDignidades persistentAsginarDignidades = em.find(AsginarDignidades.class, asginarDignidades.getIdAsginarDignidades());
            Dignidades idDignidadesOld = persistentAsginarDignidades.getIdDignidades();
            Dignidades idDignidadesNew = asginarDignidades.getIdDignidades();
            Persona idPersonaOld = persistentAsginarDignidades.getIdPersona();
            Persona idPersonaNew = asginarDignidades.getIdPersona();
            if (idDignidadesNew != null) {
                idDignidadesNew = em.getReference(idDignidadesNew.getClass(), idDignidadesNew.getIdDigninades());
                asginarDignidades.setIdDignidades(idDignidadesNew);
            }
            if (idPersonaNew != null) {
                idPersonaNew = em.getReference(idPersonaNew.getClass(), idPersonaNew.getIdPersona());
                asginarDignidades.setIdPersona(idPersonaNew);
            }
            asginarDignidades = em.merge(asginarDignidades);
            if (idDignidadesOld != null && !idDignidadesOld.equals(idDignidadesNew)) {
                idDignidadesOld.getAsginarDignidadesList().remove(asginarDignidades);
                idDignidadesOld = em.merge(idDignidadesOld);
            }
            if (idDignidadesNew != null && !idDignidadesNew.equals(idDignidadesOld)) {
                idDignidadesNew.getAsginarDignidadesList().add(asginarDignidades);
                idDignidadesNew = em.merge(idDignidadesNew);
            }
            if (idPersonaOld != null && !idPersonaOld.equals(idPersonaNew)) {
                idPersonaOld.getAsginarDignidadesList().remove(asginarDignidades);
                idPersonaOld = em.merge(idPersonaOld);
            }
            if (idPersonaNew != null && !idPersonaNew.equals(idPersonaOld)) {
                idPersonaNew.getAsginarDignidadesList().add(asginarDignidades);
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
                Integer id = asginarDignidades.getIdAsginarDignidades();
                if (findAsginarDignidades(id) == null) {
                    throw new NonexistentEntityException("The asginarDignidades with id " + id + " no longer exists.");
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
            AsginarDignidades asginarDignidades;
            try {
                asginarDignidades = em.getReference(AsginarDignidades.class, id);
                asginarDignidades.getIdAsginarDignidades();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The asginarDignidades with id " + id + " no longer exists.", enfe);
            }
            Dignidades idDignidades = asginarDignidades.getIdDignidades();
            if (idDignidades != null) {
                idDignidades.getAsginarDignidadesList().remove(asginarDignidades);
                idDignidades = em.merge(idDignidades);
            }
            Persona idPersona = asginarDignidades.getIdPersona();
            if (idPersona != null) {
                idPersona.getAsginarDignidadesList().remove(asginarDignidades);
                idPersona = em.merge(idPersona);
            }
            em.remove(asginarDignidades);
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

    public List<AsginarDignidades> findAsginarDignidadesEntities() {
        return findAsginarDignidadesEntities(true, -1, -1);
    }

    public List<AsginarDignidades> findAsginarDignidadesEntities(int maxResults, int firstResult) {
        return findAsginarDignidadesEntities(false, maxResults, firstResult);
    }

    private List<AsginarDignidades> findAsginarDignidadesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AsginarDignidades.class));
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

    public AsginarDignidades findAsginarDignidades(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AsginarDignidades.class, id);
        } finally {
            em.close();
        }
    }

    public int getAsginarDignidadesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AsginarDignidades> rt = cq.from(AsginarDignidades.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
