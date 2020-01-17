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
import modelo.Encuentros;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import modelo.Esenarios;

/**
 *
 * @author Francisco
 */
public class EsenariosJpaController implements Serializable {

    public EsenariosJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Esenarios esenarios) throws RollbackFailureException, Exception {
        if (esenarios.getEncuentrosList() == null) {
            esenarios.setEncuentrosList(new ArrayList<Encuentros>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Encuentros> attachedEncuentrosList = new ArrayList<Encuentros>();
            for (Encuentros encuentrosListEncuentrosToAttach : esenarios.getEncuentrosList()) {
                encuentrosListEncuentrosToAttach = em.getReference(encuentrosListEncuentrosToAttach.getClass(), encuentrosListEncuentrosToAttach.getIdEncuentros());
                attachedEncuentrosList.add(encuentrosListEncuentrosToAttach);
            }
            esenarios.setEncuentrosList(attachedEncuentrosList);
            em.persist(esenarios);
            for (Encuentros encuentrosListEncuentros : esenarios.getEncuentrosList()) {
                Esenarios oldIdEsenarioOfEncuentrosListEncuentros = encuentrosListEncuentros.getIdEsenario();
                encuentrosListEncuentros.setIdEsenario(esenarios);
                encuentrosListEncuentros = em.merge(encuentrosListEncuentros);
                if (oldIdEsenarioOfEncuentrosListEncuentros != null) {
                    oldIdEsenarioOfEncuentrosListEncuentros.getEncuentrosList().remove(encuentrosListEncuentros);
                    oldIdEsenarioOfEncuentrosListEncuentros = em.merge(oldIdEsenarioOfEncuentrosListEncuentros);
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

    public void edit(Esenarios esenarios) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Esenarios persistentEsenarios = em.find(Esenarios.class, esenarios.getIdEsenarios());
            List<Encuentros> encuentrosListOld = persistentEsenarios.getEncuentrosList();
            List<Encuentros> encuentrosListNew = esenarios.getEncuentrosList();
            List<Encuentros> attachedEncuentrosListNew = new ArrayList<Encuentros>();
            for (Encuentros encuentrosListNewEncuentrosToAttach : encuentrosListNew) {
                encuentrosListNewEncuentrosToAttach = em.getReference(encuentrosListNewEncuentrosToAttach.getClass(), encuentrosListNewEncuentrosToAttach.getIdEncuentros());
                attachedEncuentrosListNew.add(encuentrosListNewEncuentrosToAttach);
            }
            encuentrosListNew = attachedEncuentrosListNew;
            esenarios.setEncuentrosList(encuentrosListNew);
            esenarios = em.merge(esenarios);
            for (Encuentros encuentrosListOldEncuentros : encuentrosListOld) {
                if (!encuentrosListNew.contains(encuentrosListOldEncuentros)) {
                    encuentrosListOldEncuentros.setIdEsenario(null);
                    encuentrosListOldEncuentros = em.merge(encuentrosListOldEncuentros);
                }
            }
            for (Encuentros encuentrosListNewEncuentros : encuentrosListNew) {
                if (!encuentrosListOld.contains(encuentrosListNewEncuentros)) {
                    Esenarios oldIdEsenarioOfEncuentrosListNewEncuentros = encuentrosListNewEncuentros.getIdEsenario();
                    encuentrosListNewEncuentros.setIdEsenario(esenarios);
                    encuentrosListNewEncuentros = em.merge(encuentrosListNewEncuentros);
                    if (oldIdEsenarioOfEncuentrosListNewEncuentros != null && !oldIdEsenarioOfEncuentrosListNewEncuentros.equals(esenarios)) {
                        oldIdEsenarioOfEncuentrosListNewEncuentros.getEncuentrosList().remove(encuentrosListNewEncuentros);
                        oldIdEsenarioOfEncuentrosListNewEncuentros = em.merge(oldIdEsenarioOfEncuentrosListNewEncuentros);
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
                Integer id = esenarios.getIdEsenarios();
                if (findEsenarios(id) == null) {
                    throw new NonexistentEntityException("The esenarios with id " + id + " no longer exists.");
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
            Esenarios esenarios;
            try {
                esenarios = em.getReference(Esenarios.class, id);
                esenarios.getIdEsenarios();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The esenarios with id " + id + " no longer exists.", enfe);
            }
            List<Encuentros> encuentrosList = esenarios.getEncuentrosList();
            for (Encuentros encuentrosListEncuentros : encuentrosList) {
                encuentrosListEncuentros.setIdEsenario(null);
                encuentrosListEncuentros = em.merge(encuentrosListEncuentros);
            }
            em.remove(esenarios);
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

    public List<Esenarios> findEsenariosEntities() {
        return findEsenariosEntities(true, -1, -1);
    }

    public List<Esenarios> findEsenariosEntities(int maxResults, int firstResult) {
        return findEsenariosEntities(false, maxResults, firstResult);
    }

    private List<Esenarios> findEsenariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Esenarios.class));
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

    public Esenarios findEsenarios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Esenarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getEsenariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Esenarios> rt = cq.from(Esenarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
