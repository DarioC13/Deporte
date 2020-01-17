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
import modelo.Categoria;
import modelo.Encuentros;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import modelo.AsginarCampeonatoCategoria;

/**
 *
 * @author Francisco
 */
public class AsginarCampeonatoCategoriaJpaController implements Serializable {

    public AsginarCampeonatoCategoriaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AsginarCampeonatoCategoria asginarCampeonatoCategoria) throws RollbackFailureException, Exception {
        if (asginarCampeonatoCategoria.getEncuentrosList() == null) {
            asginarCampeonatoCategoria.setEncuentrosList(new ArrayList<Encuentros>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Categoria idCategoria = asginarCampeonatoCategoria.getIdCategoria();
            if (idCategoria != null) {
                idCategoria = em.getReference(idCategoria.getClass(), idCategoria.getIdCategoria());
                asginarCampeonatoCategoria.setIdCategoria(idCategoria);
            }
            List<Encuentros> attachedEncuentrosList = new ArrayList<Encuentros>();
            for (Encuentros encuentrosListEncuentrosToAttach : asginarCampeonatoCategoria.getEncuentrosList()) {
                encuentrosListEncuentrosToAttach = em.getReference(encuentrosListEncuentrosToAttach.getClass(), encuentrosListEncuentrosToAttach.getIdEncuentros());
                attachedEncuentrosList.add(encuentrosListEncuentrosToAttach);
            }
            asginarCampeonatoCategoria.setEncuentrosList(attachedEncuentrosList);
            em.persist(asginarCampeonatoCategoria);
            if (idCategoria != null) {
                idCategoria.getAsginarCampeonatoCategoriaList().add(asginarCampeonatoCategoria);
                idCategoria = em.merge(idCategoria);
            }
            for (Encuentros encuentrosListEncuentros : asginarCampeonatoCategoria.getEncuentrosList()) {
                AsginarCampeonatoCategoria oldAsignarCampeonatoCategoriaOfEncuentrosListEncuentros = encuentrosListEncuentros.getAsignarCampeonatoCategoria();
                encuentrosListEncuentros.setAsignarCampeonatoCategoria(asginarCampeonatoCategoria);
                encuentrosListEncuentros = em.merge(encuentrosListEncuentros);
                if (oldAsignarCampeonatoCategoriaOfEncuentrosListEncuentros != null) {
                    oldAsignarCampeonatoCategoriaOfEncuentrosListEncuentros.getEncuentrosList().remove(encuentrosListEncuentros);
                    oldAsignarCampeonatoCategoriaOfEncuentrosListEncuentros = em.merge(oldAsignarCampeonatoCategoriaOfEncuentrosListEncuentros);
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

    public void edit(AsginarCampeonatoCategoria asginarCampeonatoCategoria) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            AsginarCampeonatoCategoria persistentAsginarCampeonatoCategoria = em.find(AsginarCampeonatoCategoria.class, asginarCampeonatoCategoria.getIdasginarCampeonatoCategoria());
            Categoria idCategoriaOld = persistentAsginarCampeonatoCategoria.getIdCategoria();
            Categoria idCategoriaNew = asginarCampeonatoCategoria.getIdCategoria();
            List<Encuentros> encuentrosListOld = persistentAsginarCampeonatoCategoria.getEncuentrosList();
            List<Encuentros> encuentrosListNew = asginarCampeonatoCategoria.getEncuentrosList();
            if (idCategoriaNew != null) {
                idCategoriaNew = em.getReference(idCategoriaNew.getClass(), idCategoriaNew.getIdCategoria());
                asginarCampeonatoCategoria.setIdCategoria(idCategoriaNew);
            }
            List<Encuentros> attachedEncuentrosListNew = new ArrayList<Encuentros>();
            for (Encuentros encuentrosListNewEncuentrosToAttach : encuentrosListNew) {
                encuentrosListNewEncuentrosToAttach = em.getReference(encuentrosListNewEncuentrosToAttach.getClass(), encuentrosListNewEncuentrosToAttach.getIdEncuentros());
                attachedEncuentrosListNew.add(encuentrosListNewEncuentrosToAttach);
            }
            encuentrosListNew = attachedEncuentrosListNew;
            asginarCampeonatoCategoria.setEncuentrosList(encuentrosListNew);
            asginarCampeonatoCategoria = em.merge(asginarCampeonatoCategoria);
            if (idCategoriaOld != null && !idCategoriaOld.equals(idCategoriaNew)) {
                idCategoriaOld.getAsginarCampeonatoCategoriaList().remove(asginarCampeonatoCategoria);
                idCategoriaOld = em.merge(idCategoriaOld);
            }
            if (idCategoriaNew != null && !idCategoriaNew.equals(idCategoriaOld)) {
                idCategoriaNew.getAsginarCampeonatoCategoriaList().add(asginarCampeonatoCategoria);
                idCategoriaNew = em.merge(idCategoriaNew);
            }
            for (Encuentros encuentrosListOldEncuentros : encuentrosListOld) {
                if (!encuentrosListNew.contains(encuentrosListOldEncuentros)) {
                    encuentrosListOldEncuentros.setAsignarCampeonatoCategoria(null);
                    encuentrosListOldEncuentros = em.merge(encuentrosListOldEncuentros);
                }
            }
            for (Encuentros encuentrosListNewEncuentros : encuentrosListNew) {
                if (!encuentrosListOld.contains(encuentrosListNewEncuentros)) {
                    AsginarCampeonatoCategoria oldAsignarCampeonatoCategoriaOfEncuentrosListNewEncuentros = encuentrosListNewEncuentros.getAsignarCampeonatoCategoria();
                    encuentrosListNewEncuentros.setAsignarCampeonatoCategoria(asginarCampeonatoCategoria);
                    encuentrosListNewEncuentros = em.merge(encuentrosListNewEncuentros);
                    if (oldAsignarCampeonatoCategoriaOfEncuentrosListNewEncuentros != null && !oldAsignarCampeonatoCategoriaOfEncuentrosListNewEncuentros.equals(asginarCampeonatoCategoria)) {
                        oldAsignarCampeonatoCategoriaOfEncuentrosListNewEncuentros.getEncuentrosList().remove(encuentrosListNewEncuentros);
                        oldAsignarCampeonatoCategoriaOfEncuentrosListNewEncuentros = em.merge(oldAsignarCampeonatoCategoriaOfEncuentrosListNewEncuentros);
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
                Integer id = asginarCampeonatoCategoria.getIdasginarCampeonatoCategoria();
                if (findAsginarCampeonatoCategoria(id) == null) {
                    throw new NonexistentEntityException("The asginarCampeonatoCategoria with id " + id + " no longer exists.");
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
            AsginarCampeonatoCategoria asginarCampeonatoCategoria;
            try {
                asginarCampeonatoCategoria = em.getReference(AsginarCampeonatoCategoria.class, id);
                asginarCampeonatoCategoria.getIdasginarCampeonatoCategoria();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The asginarCampeonatoCategoria with id " + id + " no longer exists.", enfe);
            }
            Categoria idCategoria = asginarCampeonatoCategoria.getIdCategoria();
            if (idCategoria != null) {
                idCategoria.getAsginarCampeonatoCategoriaList().remove(asginarCampeonatoCategoria);
                idCategoria = em.merge(idCategoria);
            }
            List<Encuentros> encuentrosList = asginarCampeonatoCategoria.getEncuentrosList();
            for (Encuentros encuentrosListEncuentros : encuentrosList) {
                encuentrosListEncuentros.setAsignarCampeonatoCategoria(null);
                encuentrosListEncuentros = em.merge(encuentrosListEncuentros);
            }
            em.remove(asginarCampeonatoCategoria);
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

    public List<AsginarCampeonatoCategoria> findAsginarCampeonatoCategoriaEntities() {
        return findAsginarCampeonatoCategoriaEntities(true, -1, -1);
    }

    public List<AsginarCampeonatoCategoria> findAsginarCampeonatoCategoriaEntities(int maxResults, int firstResult) {
        return findAsginarCampeonatoCategoriaEntities(false, maxResults, firstResult);
    }

    private List<AsginarCampeonatoCategoria> findAsginarCampeonatoCategoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AsginarCampeonatoCategoria.class));
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

    public AsginarCampeonatoCategoria findAsginarCampeonatoCategoria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AsginarCampeonatoCategoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getAsginarCampeonatoCategoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AsginarCampeonatoCategoria> rt = cq.from(AsginarCampeonatoCategoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
