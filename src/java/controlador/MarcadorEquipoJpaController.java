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
import modelo.EstadoEncuentro;
import modelo.MarcadorEquipo;

/**
 *
 * @author Francisco
 */
public class MarcadorEquipoJpaController implements Serializable {

    public MarcadorEquipoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MarcadorEquipo marcadorEquipo) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Encuentros idEncuentro = marcadorEquipo.getIdEncuentro();
            if (idEncuentro != null) {
                idEncuentro = em.getReference(idEncuentro.getClass(), idEncuentro.getIdEncuentros());
                marcadorEquipo.setIdEncuentro(idEncuentro);
            }
            EstadoEncuentro idEstadoEncuentro = marcadorEquipo.getIdEstadoEncuentro();
            if (idEstadoEncuentro != null) {
                idEstadoEncuentro = em.getReference(idEstadoEncuentro.getClass(), idEstadoEncuentro.getIdEstadoEncuentro());
                marcadorEquipo.setIdEstadoEncuentro(idEstadoEncuentro);
                marcadorEquipo.setDiferenciaGoles(marcadorEquipo.getNumGolFavor()-marcadorEquipo.getNumGolContra());
            }
            em.persist(marcadorEquipo);
            if (idEncuentro != null) {
                idEncuentro.getMarcadorEquipoList().add(marcadorEquipo);
                idEncuentro = em.merge(idEncuentro);
            }
            if (idEstadoEncuentro != null) {
                idEstadoEncuentro.getMarcadorEquipoList().add(marcadorEquipo);
                idEstadoEncuentro = em.merge(idEstadoEncuentro);
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

    public void edit(MarcadorEquipo marcadorEquipo) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            MarcadorEquipo persistentMarcadorEquipo = em.find(MarcadorEquipo.class, marcadorEquipo.getIdMarcadorEquipo());
            Encuentros idEncuentroOld = persistentMarcadorEquipo.getIdEncuentro();
            Encuentros idEncuentroNew = marcadorEquipo.getIdEncuentro();
            EstadoEncuentro idEstadoEncuentroOld = persistentMarcadorEquipo.getIdEstadoEncuentro();
            EstadoEncuentro idEstadoEncuentroNew = marcadorEquipo.getIdEstadoEncuentro();
            if (idEncuentroNew != null) {
                idEncuentroNew = em.getReference(idEncuentroNew.getClass(), idEncuentroNew.getIdEncuentros());
                marcadorEquipo.setIdEncuentro(idEncuentroNew);
            }
            if (idEstadoEncuentroNew != null) {
                idEstadoEncuentroNew = em.getReference(idEstadoEncuentroNew.getClass(), idEstadoEncuentroNew.getIdEstadoEncuentro());
                marcadorEquipo.setIdEstadoEncuentro(idEstadoEncuentroNew);
            }
            marcadorEquipo = em.merge(marcadorEquipo);
            if (idEncuentroOld != null && !idEncuentroOld.equals(idEncuentroNew)) {
                idEncuentroOld.getMarcadorEquipoList().remove(marcadorEquipo);
                idEncuentroOld = em.merge(idEncuentroOld);
            }
            if (idEncuentroNew != null && !idEncuentroNew.equals(idEncuentroOld)) {
                idEncuentroNew.getMarcadorEquipoList().add(marcadorEquipo);
                idEncuentroNew = em.merge(idEncuentroNew);
            }
            if (idEstadoEncuentroOld != null && !idEstadoEncuentroOld.equals(idEstadoEncuentroNew)) {
                idEstadoEncuentroOld.getMarcadorEquipoList().remove(marcadorEquipo);
                idEstadoEncuentroOld = em.merge(idEstadoEncuentroOld);
            }
            if (idEstadoEncuentroNew != null && !idEstadoEncuentroNew.equals(idEstadoEncuentroOld)) {
                idEstadoEncuentroNew.getMarcadorEquipoList().add(marcadorEquipo);
                idEstadoEncuentroNew = em.merge(idEstadoEncuentroNew);
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
                Integer id = marcadorEquipo.getIdMarcadorEquipo();
                if (findMarcadorEquipo(id) == null) {
                    throw new NonexistentEntityException("The marcadorEquipo with id " + id + " no longer exists.");
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
            MarcadorEquipo marcadorEquipo;
            try {
                marcadorEquipo = em.getReference(MarcadorEquipo.class, id);
                marcadorEquipo.getIdMarcadorEquipo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The marcadorEquipo with id " + id + " no longer exists.", enfe);
            }
            Encuentros idEncuentro = marcadorEquipo.getIdEncuentro();
            if (idEncuentro != null) {
                idEncuentro.getMarcadorEquipoList().remove(marcadorEquipo);
                idEncuentro = em.merge(idEncuentro);
            }
            EstadoEncuentro idEstadoEncuentro = marcadorEquipo.getIdEstadoEncuentro();
            if (idEstadoEncuentro != null) {
                idEstadoEncuentro.getMarcadorEquipoList().remove(marcadorEquipo);
                idEstadoEncuentro = em.merge(idEstadoEncuentro);
            }
            em.remove(marcadorEquipo);
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

    public List<MarcadorEquipo> findMarcadorEquipoEntities() {
        return findMarcadorEquipoEntities(true, -1, -1);
    }

    public List<MarcadorEquipo> findMarcadorEquipoEntities(int maxResults, int firstResult) {
        return findMarcadorEquipoEntities(false, maxResults, firstResult);
    }

    private List<MarcadorEquipo> findMarcadorEquipoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MarcadorEquipo.class));
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

    public MarcadorEquipo findMarcadorEquipo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MarcadorEquipo.class, id);
        } finally {
            em.close();
        }
    }

    public int getMarcadorEquipoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MarcadorEquipo> rt = cq.from(MarcadorEquipo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
