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
import modelo.MarcadorEquipo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import modelo.EstadoEncuentro;

/**
 *
 * @author Francisco
 */
public class EstadoEncuentroJpaController implements Serializable {

    public EstadoEncuentroJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EstadoEncuentro estadoEncuentro) throws RollbackFailureException, Exception {
        if (estadoEncuentro.getMarcadorEquipoList() == null) {
            estadoEncuentro.setMarcadorEquipoList(new ArrayList<MarcadorEquipo>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<MarcadorEquipo> attachedMarcadorEquipoList = new ArrayList<MarcadorEquipo>();
            for (MarcadorEquipo marcadorEquipoListMarcadorEquipoToAttach : estadoEncuentro.getMarcadorEquipoList()) {
                marcadorEquipoListMarcadorEquipoToAttach = em.getReference(marcadorEquipoListMarcadorEquipoToAttach.getClass(), marcadorEquipoListMarcadorEquipoToAttach.getIdMarcadorEquipo());
                attachedMarcadorEquipoList.add(marcadorEquipoListMarcadorEquipoToAttach);
            }
            estadoEncuentro.setMarcadorEquipoList(attachedMarcadorEquipoList);
            em.persist(estadoEncuentro);
            for (MarcadorEquipo marcadorEquipoListMarcadorEquipo : estadoEncuentro.getMarcadorEquipoList()) {
                EstadoEncuentro oldIdEstadoEncuentroOfMarcadorEquipoListMarcadorEquipo = marcadorEquipoListMarcadorEquipo.getIdEstadoEncuentro();
                marcadorEquipoListMarcadorEquipo.setIdEstadoEncuentro(estadoEncuentro);
                marcadorEquipoListMarcadorEquipo = em.merge(marcadorEquipoListMarcadorEquipo);
                if (oldIdEstadoEncuentroOfMarcadorEquipoListMarcadorEquipo != null) {
                    oldIdEstadoEncuentroOfMarcadorEquipoListMarcadorEquipo.getMarcadorEquipoList().remove(marcadorEquipoListMarcadorEquipo);
                    oldIdEstadoEncuentroOfMarcadorEquipoListMarcadorEquipo = em.merge(oldIdEstadoEncuentroOfMarcadorEquipoListMarcadorEquipo);
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

    public void edit(EstadoEncuentro estadoEncuentro) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            EstadoEncuentro persistentEstadoEncuentro = em.find(EstadoEncuentro.class, estadoEncuentro.getIdEstadoEncuentro());
            List<MarcadorEquipo> marcadorEquipoListOld = persistentEstadoEncuentro.getMarcadorEquipoList();
            List<MarcadorEquipo> marcadorEquipoListNew = estadoEncuentro.getMarcadorEquipoList();
            List<MarcadorEquipo> attachedMarcadorEquipoListNew = new ArrayList<MarcadorEquipo>();
            for (MarcadorEquipo marcadorEquipoListNewMarcadorEquipoToAttach : marcadorEquipoListNew) {
                marcadorEquipoListNewMarcadorEquipoToAttach = em.getReference(marcadorEquipoListNewMarcadorEquipoToAttach.getClass(), marcadorEquipoListNewMarcadorEquipoToAttach.getIdMarcadorEquipo());
                attachedMarcadorEquipoListNew.add(marcadorEquipoListNewMarcadorEquipoToAttach);
            }
            marcadorEquipoListNew = attachedMarcadorEquipoListNew;
            estadoEncuentro.setMarcadorEquipoList(marcadorEquipoListNew);
            estadoEncuentro = em.merge(estadoEncuentro);
            for (MarcadorEquipo marcadorEquipoListOldMarcadorEquipo : marcadorEquipoListOld) {
                if (!marcadorEquipoListNew.contains(marcadorEquipoListOldMarcadorEquipo)) {
                    marcadorEquipoListOldMarcadorEquipo.setIdEstadoEncuentro(null);
                    marcadorEquipoListOldMarcadorEquipo = em.merge(marcadorEquipoListOldMarcadorEquipo);
                }
            }
            for (MarcadorEquipo marcadorEquipoListNewMarcadorEquipo : marcadorEquipoListNew) {
                if (!marcadorEquipoListOld.contains(marcadorEquipoListNewMarcadorEquipo)) {
                    EstadoEncuentro oldIdEstadoEncuentroOfMarcadorEquipoListNewMarcadorEquipo = marcadorEquipoListNewMarcadorEquipo.getIdEstadoEncuentro();
                    marcadorEquipoListNewMarcadorEquipo.setIdEstadoEncuentro(estadoEncuentro);
                    marcadorEquipoListNewMarcadorEquipo = em.merge(marcadorEquipoListNewMarcadorEquipo);
                    if (oldIdEstadoEncuentroOfMarcadorEquipoListNewMarcadorEquipo != null && !oldIdEstadoEncuentroOfMarcadorEquipoListNewMarcadorEquipo.equals(estadoEncuentro)) {
                        oldIdEstadoEncuentroOfMarcadorEquipoListNewMarcadorEquipo.getMarcadorEquipoList().remove(marcadorEquipoListNewMarcadorEquipo);
                        oldIdEstadoEncuentroOfMarcadorEquipoListNewMarcadorEquipo = em.merge(oldIdEstadoEncuentroOfMarcadorEquipoListNewMarcadorEquipo);
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
                Integer id = estadoEncuentro.getIdEstadoEncuentro();
                if (findEstadoEncuentro(id) == null) {
                    throw new NonexistentEntityException("The estadoEncuentro with id " + id + " no longer exists.");
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
            EstadoEncuentro estadoEncuentro;
            try {
                estadoEncuentro = em.getReference(EstadoEncuentro.class, id);
                estadoEncuentro.getIdEstadoEncuentro();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estadoEncuentro with id " + id + " no longer exists.", enfe);
            }
            List<MarcadorEquipo> marcadorEquipoList = estadoEncuentro.getMarcadorEquipoList();
            for (MarcadorEquipo marcadorEquipoListMarcadorEquipo : marcadorEquipoList) {
                marcadorEquipoListMarcadorEquipo.setIdEstadoEncuentro(null);
                marcadorEquipoListMarcadorEquipo = em.merge(marcadorEquipoListMarcadorEquipo);
            }
            em.remove(estadoEncuentro);
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

    public List<EstadoEncuentro> findEstadoEncuentroEntities() {
        return findEstadoEncuentroEntities(true, -1, -1);
    }

    public List<EstadoEncuentro> findEstadoEncuentroEntities(int maxResults, int firstResult) {
        return findEstadoEncuentroEntities(false, maxResults, firstResult);
    }

    private List<EstadoEncuentro> findEstadoEncuentroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EstadoEncuentro.class));
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

    public EstadoEncuentro findEstadoEncuentro(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EstadoEncuentro.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadoEncuentroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EstadoEncuentro> rt = cq.from(EstadoEncuentro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
