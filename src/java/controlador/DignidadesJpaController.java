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
import modelo.AsginarDignidades;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import modelo.Dignidades;

/**
 *
 * @author Francisco
 */
public class DignidadesJpaController implements Serializable {

    public DignidadesJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Dignidades dignidades) throws RollbackFailureException, Exception {
        if (dignidades.getAsginarDignidadesList() == null) {
            dignidades.setAsginarDignidadesList(new ArrayList<AsginarDignidades>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<AsginarDignidades> attachedAsginarDignidadesList = new ArrayList<AsginarDignidades>();
            for (AsginarDignidades asginarDignidadesListAsginarDignidadesToAttach : dignidades.getAsginarDignidadesList()) {
                asginarDignidadesListAsginarDignidadesToAttach = em.getReference(asginarDignidadesListAsginarDignidadesToAttach.getClass(), asginarDignidadesListAsginarDignidadesToAttach.getIdAsginarDignidades());
                attachedAsginarDignidadesList.add(asginarDignidadesListAsginarDignidadesToAttach);
            }
            dignidades.setAsginarDignidadesList(attachedAsginarDignidadesList);
            em.persist(dignidades);
            for (AsginarDignidades asginarDignidadesListAsginarDignidades : dignidades.getAsginarDignidadesList()) {
                Dignidades oldIdDignidadesOfAsginarDignidadesListAsginarDignidades = asginarDignidadesListAsginarDignidades.getIdDignidades();
                asginarDignidadesListAsginarDignidades.setIdDignidades(dignidades);
                asginarDignidadesListAsginarDignidades = em.merge(asginarDignidadesListAsginarDignidades);
                if (oldIdDignidadesOfAsginarDignidadesListAsginarDignidades != null) {
                    oldIdDignidadesOfAsginarDignidadesListAsginarDignidades.getAsginarDignidadesList().remove(asginarDignidadesListAsginarDignidades);
                    oldIdDignidadesOfAsginarDignidadesListAsginarDignidades = em.merge(oldIdDignidadesOfAsginarDignidadesListAsginarDignidades);
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

    public void edit(Dignidades dignidades) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Dignidades persistentDignidades = em.find(Dignidades.class, dignidades.getIdDigninades());
            List<AsginarDignidades> asginarDignidadesListOld = persistentDignidades.getAsginarDignidadesList();
            List<AsginarDignidades> asginarDignidadesListNew = dignidades.getAsginarDignidadesList();
            List<AsginarDignidades> attachedAsginarDignidadesListNew = new ArrayList<AsginarDignidades>();
            for (AsginarDignidades asginarDignidadesListNewAsginarDignidadesToAttach : asginarDignidadesListNew) {
                asginarDignidadesListNewAsginarDignidadesToAttach = em.getReference(asginarDignidadesListNewAsginarDignidadesToAttach.getClass(), asginarDignidadesListNewAsginarDignidadesToAttach.getIdAsginarDignidades());
                attachedAsginarDignidadesListNew.add(asginarDignidadesListNewAsginarDignidadesToAttach);
            }
            asginarDignidadesListNew = attachedAsginarDignidadesListNew;
            dignidades.setAsginarDignidadesList(asginarDignidadesListNew);
            dignidades = em.merge(dignidades);
            for (AsginarDignidades asginarDignidadesListOldAsginarDignidades : asginarDignidadesListOld) {
                if (!asginarDignidadesListNew.contains(asginarDignidadesListOldAsginarDignidades)) {
                    asginarDignidadesListOldAsginarDignidades.setIdDignidades(null);
                    asginarDignidadesListOldAsginarDignidades = em.merge(asginarDignidadesListOldAsginarDignidades);
                }
            }
            for (AsginarDignidades asginarDignidadesListNewAsginarDignidades : asginarDignidadesListNew) {
                if (!asginarDignidadesListOld.contains(asginarDignidadesListNewAsginarDignidades)) {
                    Dignidades oldIdDignidadesOfAsginarDignidadesListNewAsginarDignidades = asginarDignidadesListNewAsginarDignidades.getIdDignidades();
                    asginarDignidadesListNewAsginarDignidades.setIdDignidades(dignidades);
                    asginarDignidadesListNewAsginarDignidades = em.merge(asginarDignidadesListNewAsginarDignidades);
                    if (oldIdDignidadesOfAsginarDignidadesListNewAsginarDignidades != null && !oldIdDignidadesOfAsginarDignidadesListNewAsginarDignidades.equals(dignidades)) {
                        oldIdDignidadesOfAsginarDignidadesListNewAsginarDignidades.getAsginarDignidadesList().remove(asginarDignidadesListNewAsginarDignidades);
                        oldIdDignidadesOfAsginarDignidadesListNewAsginarDignidades = em.merge(oldIdDignidadesOfAsginarDignidadesListNewAsginarDignidades);
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
                Integer id = dignidades.getIdDigninades();
                if (findDignidades(id) == null) {
                    throw new NonexistentEntityException("The dignidades with id " + id + " no longer exists.");
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
            Dignidades dignidades;
            try {
                dignidades = em.getReference(Dignidades.class, id);
                dignidades.getIdDigninades();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The dignidades with id " + id + " no longer exists.", enfe);
            }
            List<AsginarDignidades> asginarDignidadesList = dignidades.getAsginarDignidadesList();
            for (AsginarDignidades asginarDignidadesListAsginarDignidades : asginarDignidadesList) {
                asginarDignidadesListAsginarDignidades.setIdDignidades(null);
                asginarDignidadesListAsginarDignidades = em.merge(asginarDignidadesListAsginarDignidades);
            }
            em.remove(dignidades);
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

    public List<Dignidades> findDignidadesEntities() {
        return findDignidadesEntities(true, -1, -1);
    }

    public List<Dignidades> findDignidadesEntities(int maxResults, int firstResult) {
        return findDignidadesEntities(false, maxResults, firstResult);
    }

    private List<Dignidades> findDignidadesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Dignidades.class));
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

    public Dignidades findDignidades(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Dignidades.class, id);
        } finally {
            em.close();
        }
    }

    public int getDignidadesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Dignidades> rt = cq.from(Dignidades.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
