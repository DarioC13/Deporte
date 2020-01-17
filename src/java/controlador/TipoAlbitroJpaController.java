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
import modelo.Albitros;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import modelo.TipoAlbitro;

/**
 *
 * @author Francisco
 */
public class TipoAlbitroJpaController implements Serializable {

    public TipoAlbitroJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoAlbitro tipoAlbitro) throws RollbackFailureException, Exception {
        if (tipoAlbitro.getAlbitrosList() == null) {
            tipoAlbitro.setAlbitrosList(new ArrayList<Albitros>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Albitros> attachedAlbitrosList = new ArrayList<Albitros>();
            for (Albitros albitrosListAlbitrosToAttach : tipoAlbitro.getAlbitrosList()) {
                albitrosListAlbitrosToAttach = em.getReference(albitrosListAlbitrosToAttach.getClass(), albitrosListAlbitrosToAttach.getIdAlbitros());
                attachedAlbitrosList.add(albitrosListAlbitrosToAttach);
            }
            tipoAlbitro.setAlbitrosList(attachedAlbitrosList);
            em.persist(tipoAlbitro);
            for (Albitros albitrosListAlbitros : tipoAlbitro.getAlbitrosList()) {
                TipoAlbitro oldIdTipoAlbitroOfAlbitrosListAlbitros = albitrosListAlbitros.getIdTipoAlbitro();
                albitrosListAlbitros.setIdTipoAlbitro(tipoAlbitro);
                albitrosListAlbitros = em.merge(albitrosListAlbitros);
                if (oldIdTipoAlbitroOfAlbitrosListAlbitros != null) {
                    oldIdTipoAlbitroOfAlbitrosListAlbitros.getAlbitrosList().remove(albitrosListAlbitros);
                    oldIdTipoAlbitroOfAlbitrosListAlbitros = em.merge(oldIdTipoAlbitroOfAlbitrosListAlbitros);
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

    public void edit(TipoAlbitro tipoAlbitro) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoAlbitro persistentTipoAlbitro = em.find(TipoAlbitro.class, tipoAlbitro.getIdTipoAlbitro());
            List<Albitros> albitrosListOld = persistentTipoAlbitro.getAlbitrosList();
            List<Albitros> albitrosListNew = tipoAlbitro.getAlbitrosList();
            List<Albitros> attachedAlbitrosListNew = new ArrayList<Albitros>();
            for (Albitros albitrosListNewAlbitrosToAttach : albitrosListNew) {
                albitrosListNewAlbitrosToAttach = em.getReference(albitrosListNewAlbitrosToAttach.getClass(), albitrosListNewAlbitrosToAttach.getIdAlbitros());
                attachedAlbitrosListNew.add(albitrosListNewAlbitrosToAttach);
            }
            albitrosListNew = attachedAlbitrosListNew;
            tipoAlbitro.setAlbitrosList(albitrosListNew);
            tipoAlbitro = em.merge(tipoAlbitro);
            for (Albitros albitrosListOldAlbitros : albitrosListOld) {
                if (!albitrosListNew.contains(albitrosListOldAlbitros)) {
                    albitrosListOldAlbitros.setIdTipoAlbitro(null);
                    albitrosListOldAlbitros = em.merge(albitrosListOldAlbitros);
                }
            }
            for (Albitros albitrosListNewAlbitros : albitrosListNew) {
                if (!albitrosListOld.contains(albitrosListNewAlbitros)) {
                    TipoAlbitro oldIdTipoAlbitroOfAlbitrosListNewAlbitros = albitrosListNewAlbitros.getIdTipoAlbitro();
                    albitrosListNewAlbitros.setIdTipoAlbitro(tipoAlbitro);
                    albitrosListNewAlbitros = em.merge(albitrosListNewAlbitros);
                    if (oldIdTipoAlbitroOfAlbitrosListNewAlbitros != null && !oldIdTipoAlbitroOfAlbitrosListNewAlbitros.equals(tipoAlbitro)) {
                        oldIdTipoAlbitroOfAlbitrosListNewAlbitros.getAlbitrosList().remove(albitrosListNewAlbitros);
                        oldIdTipoAlbitroOfAlbitrosListNewAlbitros = em.merge(oldIdTipoAlbitroOfAlbitrosListNewAlbitros);
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
                Integer id = tipoAlbitro.getIdTipoAlbitro();
                if (findTipoAlbitro(id) == null) {
                    throw new NonexistentEntityException("The tipoAlbitro with id " + id + " no longer exists.");
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
            TipoAlbitro tipoAlbitro;
            try {
                tipoAlbitro = em.getReference(TipoAlbitro.class, id);
                tipoAlbitro.getIdTipoAlbitro();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoAlbitro with id " + id + " no longer exists.", enfe);
            }
            List<Albitros> albitrosList = tipoAlbitro.getAlbitrosList();
            for (Albitros albitrosListAlbitros : albitrosList) {
                albitrosListAlbitros.setIdTipoAlbitro(null);
                albitrosListAlbitros = em.merge(albitrosListAlbitros);
            }
            em.remove(tipoAlbitro);
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

    public List<TipoAlbitro> findTipoAlbitroEntities() {
        return findTipoAlbitroEntities(true, -1, -1);
    }

    public List<TipoAlbitro> findTipoAlbitroEntities(int maxResults, int firstResult) {
        return findTipoAlbitroEntities(false, maxResults, firstResult);
    }

    private List<TipoAlbitro> findTipoAlbitroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoAlbitro.class));
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

    public TipoAlbitro findTipoAlbitro(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoAlbitro.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoAlbitroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoAlbitro> rt = cq.from(TipoAlbitro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
