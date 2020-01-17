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
import modelo.Inscripcion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import modelo.Equipo;

/**
 *
 * @author Francisco
 */
public class EquipoJpaController implements Serializable {

    public EquipoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Equipo equipo) throws RollbackFailureException, Exception {
        if (equipo.getInscripcionList() == null) {
            equipo.setInscripcionList(new ArrayList<Inscripcion>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Inscripcion> attachedInscripcionList = new ArrayList<Inscripcion>();
            for (Inscripcion inscripcionListInscripcionToAttach : equipo.getInscripcionList()) {
                inscripcionListInscripcionToAttach = em.getReference(inscripcionListInscripcionToAttach.getClass(), inscripcionListInscripcionToAttach.getIdInscripcion());
                attachedInscripcionList.add(inscripcionListInscripcionToAttach);
            }
            equipo.setInscripcionList(attachedInscripcionList);
            em.persist(equipo);
            for (Inscripcion inscripcionListInscripcion : equipo.getInscripcionList()) {
                Equipo oldIdEquipoOfInscripcionListInscripcion = inscripcionListInscripcion.getIdEquipo();
                inscripcionListInscripcion.setIdEquipo(equipo);
                inscripcionListInscripcion = em.merge(inscripcionListInscripcion);
                if (oldIdEquipoOfInscripcionListInscripcion != null) {
                    oldIdEquipoOfInscripcionListInscripcion.getInscripcionList().remove(inscripcionListInscripcion);
                    oldIdEquipoOfInscripcionListInscripcion = em.merge(oldIdEquipoOfInscripcionListInscripcion);
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

    public void edit(Equipo equipo) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Equipo persistentEquipo = em.find(Equipo.class, equipo.getIdEquipo());
            List<Inscripcion> inscripcionListOld = persistentEquipo.getInscripcionList();
            List<Inscripcion> inscripcionListNew = equipo.getInscripcionList();
            List<Inscripcion> attachedInscripcionListNew = new ArrayList<Inscripcion>();
            for (Inscripcion inscripcionListNewInscripcionToAttach : inscripcionListNew) {
                inscripcionListNewInscripcionToAttach = em.getReference(inscripcionListNewInscripcionToAttach.getClass(), inscripcionListNewInscripcionToAttach.getIdInscripcion());
                attachedInscripcionListNew.add(inscripcionListNewInscripcionToAttach);
            }
            inscripcionListNew = attachedInscripcionListNew;
            equipo.setInscripcionList(inscripcionListNew);
            equipo = em.merge(equipo);
            for (Inscripcion inscripcionListOldInscripcion : inscripcionListOld) {
                if (!inscripcionListNew.contains(inscripcionListOldInscripcion)) {
                    inscripcionListOldInscripcion.setIdEquipo(null);
                    inscripcionListOldInscripcion = em.merge(inscripcionListOldInscripcion);
                }
            }
            for (Inscripcion inscripcionListNewInscripcion : inscripcionListNew) {
                if (!inscripcionListOld.contains(inscripcionListNewInscripcion)) {
                    Equipo oldIdEquipoOfInscripcionListNewInscripcion = inscripcionListNewInscripcion.getIdEquipo();
                    inscripcionListNewInscripcion.setIdEquipo(equipo);
                    inscripcionListNewInscripcion = em.merge(inscripcionListNewInscripcion);
                    if (oldIdEquipoOfInscripcionListNewInscripcion != null && !oldIdEquipoOfInscripcionListNewInscripcion.equals(equipo)) {
                        oldIdEquipoOfInscripcionListNewInscripcion.getInscripcionList().remove(inscripcionListNewInscripcion);
                        oldIdEquipoOfInscripcionListNewInscripcion = em.merge(oldIdEquipoOfInscripcionListNewInscripcion);
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
                Integer id = equipo.getIdEquipo();
                if (findEquipo(id) == null) {
                    throw new NonexistentEntityException("The equipo with id " + id + " no longer exists.");
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
            Equipo equipo;
            try {
                equipo = em.getReference(Equipo.class, id);
                equipo.getIdEquipo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The equipo with id " + id + " no longer exists.", enfe);
            }
            List<Inscripcion> inscripcionList = equipo.getInscripcionList();
            for (Inscripcion inscripcionListInscripcion : inscripcionList) {
                inscripcionListInscripcion.setIdEquipo(null);
                inscripcionListInscripcion = em.merge(inscripcionListInscripcion);
            }
            em.remove(equipo);
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

    public List<Equipo> findEquipoEntities() {
        return findEquipoEntities(true, -1, -1);
    }

    public List<Equipo> findEquipoEntities(int maxResults, int firstResult) {
        return findEquipoEntities(false, maxResults, firstResult);
    }

    private List<Equipo> findEquipoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Equipo.class));
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

    public Equipo findEquipo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Equipo.class, id);
        } finally {
            em.close();
        }
    }

    public int getEquipoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Equipo> rt = cq.from(Equipo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
