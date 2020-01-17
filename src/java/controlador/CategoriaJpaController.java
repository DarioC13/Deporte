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
import modelo.AsginarCampeonatoCategoria;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import modelo.Categoria;

/**
 *
 * @author Francisco
 */
public class CategoriaJpaController implements Serializable {

    public CategoriaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Categoria categoria) throws RollbackFailureException, Exception {
        if (categoria.getAsginarCampeonatoCategoriaList() == null) {
            categoria.setAsginarCampeonatoCategoriaList(new ArrayList<AsginarCampeonatoCategoria>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<AsginarCampeonatoCategoria> attachedAsginarCampeonatoCategoriaList = new ArrayList<AsginarCampeonatoCategoria>();
            for (AsginarCampeonatoCategoria asginarCampeonatoCategoriaListAsginarCampeonatoCategoriaToAttach : categoria.getAsginarCampeonatoCategoriaList()) {
                asginarCampeonatoCategoriaListAsginarCampeonatoCategoriaToAttach = em.getReference(asginarCampeonatoCategoriaListAsginarCampeonatoCategoriaToAttach.getClass(), asginarCampeonatoCategoriaListAsginarCampeonatoCategoriaToAttach.getIdasginarCampeonatoCategoria());
                attachedAsginarCampeonatoCategoriaList.add(asginarCampeonatoCategoriaListAsginarCampeonatoCategoriaToAttach);
            }
            categoria.setAsginarCampeonatoCategoriaList(attachedAsginarCampeonatoCategoriaList);
            em.persist(categoria);
            for (AsginarCampeonatoCategoria asginarCampeonatoCategoriaListAsginarCampeonatoCategoria : categoria.getAsginarCampeonatoCategoriaList()) {
                Categoria oldIdCategoriaOfAsginarCampeonatoCategoriaListAsginarCampeonatoCategoria = asginarCampeonatoCategoriaListAsginarCampeonatoCategoria.getIdCategoria();
                asginarCampeonatoCategoriaListAsginarCampeonatoCategoria.setIdCategoria(categoria);
                asginarCampeonatoCategoriaListAsginarCampeonatoCategoria = em.merge(asginarCampeonatoCategoriaListAsginarCampeonatoCategoria);
                if (oldIdCategoriaOfAsginarCampeonatoCategoriaListAsginarCampeonatoCategoria != null) {
                    oldIdCategoriaOfAsginarCampeonatoCategoriaListAsginarCampeonatoCategoria.getAsginarCampeonatoCategoriaList().remove(asginarCampeonatoCategoriaListAsginarCampeonatoCategoria);
                    oldIdCategoriaOfAsginarCampeonatoCategoriaListAsginarCampeonatoCategoria = em.merge(oldIdCategoriaOfAsginarCampeonatoCategoriaListAsginarCampeonatoCategoria);
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

    public void edit(Categoria categoria) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Categoria persistentCategoria = em.find(Categoria.class, categoria.getIdCategoria());
            List<AsginarCampeonatoCategoria> asginarCampeonatoCategoriaListOld = persistentCategoria.getAsginarCampeonatoCategoriaList();
            List<AsginarCampeonatoCategoria> asginarCampeonatoCategoriaListNew = categoria.getAsginarCampeonatoCategoriaList();
            List<AsginarCampeonatoCategoria> attachedAsginarCampeonatoCategoriaListNew = new ArrayList<AsginarCampeonatoCategoria>();
            for (AsginarCampeonatoCategoria asginarCampeonatoCategoriaListNewAsginarCampeonatoCategoriaToAttach : asginarCampeonatoCategoriaListNew) {
                asginarCampeonatoCategoriaListNewAsginarCampeonatoCategoriaToAttach = em.getReference(asginarCampeonatoCategoriaListNewAsginarCampeonatoCategoriaToAttach.getClass(), asginarCampeonatoCategoriaListNewAsginarCampeonatoCategoriaToAttach.getIdasginarCampeonatoCategoria());
                attachedAsginarCampeonatoCategoriaListNew.add(asginarCampeonatoCategoriaListNewAsginarCampeonatoCategoriaToAttach);
            }
            asginarCampeonatoCategoriaListNew = attachedAsginarCampeonatoCategoriaListNew;
            categoria.setAsginarCampeonatoCategoriaList(asginarCampeonatoCategoriaListNew);
            categoria = em.merge(categoria);
            for (AsginarCampeonatoCategoria asginarCampeonatoCategoriaListOldAsginarCampeonatoCategoria : asginarCampeonatoCategoriaListOld) {
                if (!asginarCampeonatoCategoriaListNew.contains(asginarCampeonatoCategoriaListOldAsginarCampeonatoCategoria)) {
                    asginarCampeonatoCategoriaListOldAsginarCampeonatoCategoria.setIdCategoria(null);
                    asginarCampeonatoCategoriaListOldAsginarCampeonatoCategoria = em.merge(asginarCampeonatoCategoriaListOldAsginarCampeonatoCategoria);
                }
            }
            for (AsginarCampeonatoCategoria asginarCampeonatoCategoriaListNewAsginarCampeonatoCategoria : asginarCampeonatoCategoriaListNew) {
                if (!asginarCampeonatoCategoriaListOld.contains(asginarCampeonatoCategoriaListNewAsginarCampeonatoCategoria)) {
                    Categoria oldIdCategoriaOfAsginarCampeonatoCategoriaListNewAsginarCampeonatoCategoria = asginarCampeonatoCategoriaListNewAsginarCampeonatoCategoria.getIdCategoria();
                    asginarCampeonatoCategoriaListNewAsginarCampeonatoCategoria.setIdCategoria(categoria);
                    asginarCampeonatoCategoriaListNewAsginarCampeonatoCategoria = em.merge(asginarCampeonatoCategoriaListNewAsginarCampeonatoCategoria);
                    if (oldIdCategoriaOfAsginarCampeonatoCategoriaListNewAsginarCampeonatoCategoria != null && !oldIdCategoriaOfAsginarCampeonatoCategoriaListNewAsginarCampeonatoCategoria.equals(categoria)) {
                        oldIdCategoriaOfAsginarCampeonatoCategoriaListNewAsginarCampeonatoCategoria.getAsginarCampeonatoCategoriaList().remove(asginarCampeonatoCategoriaListNewAsginarCampeonatoCategoria);
                        oldIdCategoriaOfAsginarCampeonatoCategoriaListNewAsginarCampeonatoCategoria = em.merge(oldIdCategoriaOfAsginarCampeonatoCategoriaListNewAsginarCampeonatoCategoria);
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
                Integer id = categoria.getIdCategoria();
                if (findCategoria(id) == null) {
                    throw new NonexistentEntityException("The categoria with id " + id + " no longer exists.");
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
            Categoria categoria;
            try {
                categoria = em.getReference(Categoria.class, id);
                categoria.getIdCategoria();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categoria with id " + id + " no longer exists.", enfe);
            }
            List<AsginarCampeonatoCategoria> asginarCampeonatoCategoriaList = categoria.getAsginarCampeonatoCategoriaList();
            for (AsginarCampeonatoCategoria asginarCampeonatoCategoriaListAsginarCampeonatoCategoria : asginarCampeonatoCategoriaList) {
                asginarCampeonatoCategoriaListAsginarCampeonatoCategoria.setIdCategoria(null);
                asginarCampeonatoCategoriaListAsginarCampeonatoCategoria = em.merge(asginarCampeonatoCategoriaListAsginarCampeonatoCategoria);
            }
            em.remove(categoria);
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

    public List<Categoria> findCategoriaEntities() {
        return findCategoriaEntities(true, -1, -1);
    }

    public List<Categoria> findCategoriaEntities(int maxResults, int firstResult) {
        return findCategoriaEntities(false, maxResults, firstResult);
    }

    private List<Categoria> findCategoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Categoria.class));
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

    public Categoria findCategoria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Categoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Categoria> rt = cq.from(Categoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
