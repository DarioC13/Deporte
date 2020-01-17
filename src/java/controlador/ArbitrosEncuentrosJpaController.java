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
import modelo.Albitros;
import modelo.ArbitrosEncuentros;
import modelo.Encuentros;

/**
 *
 * @author Francisco
 */
public class ArbitrosEncuentrosJpaController implements Serializable {

    public ArbitrosEncuentrosJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ArbitrosEncuentros arbitrosEncuentros) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Albitros idAlbitro = arbitrosEncuentros.getIdAlbitro();
            if (idAlbitro != null) {
                idAlbitro = em.getReference(idAlbitro.getClass(), idAlbitro.getIdAlbitros());
                arbitrosEncuentros.setIdAlbitro(idAlbitro);
            }
            Encuentros idEncuentro = arbitrosEncuentros.getIdEncuentro();
            if (idEncuentro != null) {
                idEncuentro = em.getReference(idEncuentro.getClass(), idEncuentro.getIdEncuentros());
                arbitrosEncuentros.setIdEncuentro(idEncuentro);
            }
            em.persist(arbitrosEncuentros);
            if (idAlbitro != null) {
                idAlbitro.getArbitrosEncuentrosList().add(arbitrosEncuentros);
                idAlbitro = em.merge(idAlbitro);
            }
            if (idEncuentro != null) {
                idEncuentro.getArbitrosEncuentrosList().add(arbitrosEncuentros);
                idEncuentro = em.merge(idEncuentro);
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

    public void edit(ArbitrosEncuentros arbitrosEncuentros) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ArbitrosEncuentros persistentArbitrosEncuentros = em.find(ArbitrosEncuentros.class, arbitrosEncuentros.getIdAlbitrosEncuentroscol());
            Albitros idAlbitroOld = persistentArbitrosEncuentros.getIdAlbitro();
            Albitros idAlbitroNew = arbitrosEncuentros.getIdAlbitro();
            Encuentros idEncuentroOld = persistentArbitrosEncuentros.getIdEncuentro();
            Encuentros idEncuentroNew = arbitrosEncuentros.getIdEncuentro();
            if (idAlbitroNew != null) {
                idAlbitroNew = em.getReference(idAlbitroNew.getClass(), idAlbitroNew.getIdAlbitros());
                arbitrosEncuentros.setIdAlbitro(idAlbitroNew);
            }
            if (idEncuentroNew != null) {
                idEncuentroNew = em.getReference(idEncuentroNew.getClass(), idEncuentroNew.getIdEncuentros());
                arbitrosEncuentros.setIdEncuentro(idEncuentroNew);
            }
            arbitrosEncuentros = em.merge(arbitrosEncuentros);
            if (idAlbitroOld != null && !idAlbitroOld.equals(idAlbitroNew)) {
                idAlbitroOld.getArbitrosEncuentrosList().remove(arbitrosEncuentros);
                idAlbitroOld = em.merge(idAlbitroOld);
            }
            if (idAlbitroNew != null && !idAlbitroNew.equals(idAlbitroOld)) {
                idAlbitroNew.getArbitrosEncuentrosList().add(arbitrosEncuentros);
                idAlbitroNew = em.merge(idAlbitroNew);
            }
            if (idEncuentroOld != null && !idEncuentroOld.equals(idEncuentroNew)) {
                idEncuentroOld.getArbitrosEncuentrosList().remove(arbitrosEncuentros);
                idEncuentroOld = em.merge(idEncuentroOld);
            }
            if (idEncuentroNew != null && !idEncuentroNew.equals(idEncuentroOld)) {
                idEncuentroNew.getArbitrosEncuentrosList().add(arbitrosEncuentros);
                idEncuentroNew = em.merge(idEncuentroNew);
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
                Integer id = arbitrosEncuentros.getIdAlbitrosEncuentroscol();
                if (findArbitrosEncuentros(id) == null) {
                    throw new NonexistentEntityException("The arbitrosEncuentros with id " + id + " no longer exists.");
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
            ArbitrosEncuentros arbitrosEncuentros;
            try {
                arbitrosEncuentros = em.getReference(ArbitrosEncuentros.class, id);
                arbitrosEncuentros.getIdAlbitrosEncuentroscol();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The arbitrosEncuentros with id " + id + " no longer exists.", enfe);
            }
            Albitros idAlbitro = arbitrosEncuentros.getIdAlbitro();
            if (idAlbitro != null) {
                idAlbitro.getArbitrosEncuentrosList().remove(arbitrosEncuentros);
                idAlbitro = em.merge(idAlbitro);
            }
            Encuentros idEncuentro = arbitrosEncuentros.getIdEncuentro();
            if (idEncuentro != null) {
                idEncuentro.getArbitrosEncuentrosList().remove(arbitrosEncuentros);
                idEncuentro = em.merge(idEncuentro);
            }
            em.remove(arbitrosEncuentros);
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

    public List<ArbitrosEncuentros> findArbitrosEncuentrosEntities() {
        return findArbitrosEncuentrosEntities(true, -1, -1);
    }

    public List<ArbitrosEncuentros> findArbitrosEncuentrosEntities(int maxResults, int firstResult) {
        return findArbitrosEncuentrosEntities(false, maxResults, firstResult);
    }

    private List<ArbitrosEncuentros> findArbitrosEncuentrosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ArbitrosEncuentros.class));
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

    public ArbitrosEncuentros findArbitrosEncuentros(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ArbitrosEncuentros.class, id);
        } finally {
            em.close();
        }
    }

    public int getArbitrosEncuentrosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ArbitrosEncuentros> rt = cq.from(ArbitrosEncuentros.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
