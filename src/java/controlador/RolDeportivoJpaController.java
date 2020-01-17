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
import modelo.Persona;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import modelo.RolDeportivo;

/**
 *
 * @author Francisco
 */
public class RolDeportivoJpaController implements Serializable {

    public RolDeportivoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RolDeportivo rolDeportivo) throws RollbackFailureException, Exception {
        if (rolDeportivo.getPersonaList() == null) {
            rolDeportivo.setPersonaList(new ArrayList<Persona>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Persona> attachedPersonaList = new ArrayList<Persona>();
            for (Persona personaListPersonaToAttach : rolDeportivo.getPersonaList()) {
                personaListPersonaToAttach = em.getReference(personaListPersonaToAttach.getClass(), personaListPersonaToAttach.getIdPersona());
                attachedPersonaList.add(personaListPersonaToAttach);
            }
            rolDeportivo.setPersonaList(attachedPersonaList);
            em.persist(rolDeportivo);
            for (Persona personaListPersona : rolDeportivo.getPersonaList()) {
                RolDeportivo oldIdRolDeportivoOfPersonaListPersona = personaListPersona.getIdRolDeportivo();
                personaListPersona.setIdRolDeportivo(rolDeportivo);
                personaListPersona = em.merge(personaListPersona);
                if (oldIdRolDeportivoOfPersonaListPersona != null) {
                    oldIdRolDeportivoOfPersonaListPersona.getPersonaList().remove(personaListPersona);
                    oldIdRolDeportivoOfPersonaListPersona = em.merge(oldIdRolDeportivoOfPersonaListPersona);
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

    public void edit(RolDeportivo rolDeportivo) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            RolDeportivo persistentRolDeportivo = em.find(RolDeportivo.class, rolDeportivo.getIdRolDeportivo());
            List<Persona> personaListOld = persistentRolDeportivo.getPersonaList();
            List<Persona> personaListNew = rolDeportivo.getPersonaList();
            List<Persona> attachedPersonaListNew = new ArrayList<Persona>();
            for (Persona personaListNewPersonaToAttach : personaListNew) {
                personaListNewPersonaToAttach = em.getReference(personaListNewPersonaToAttach.getClass(), personaListNewPersonaToAttach.getIdPersona());
                attachedPersonaListNew.add(personaListNewPersonaToAttach);
            }
            personaListNew = attachedPersonaListNew;
            rolDeportivo.setPersonaList(personaListNew);
            rolDeportivo = em.merge(rolDeportivo);
            for (Persona personaListOldPersona : personaListOld) {
                if (!personaListNew.contains(personaListOldPersona)) {
                    personaListOldPersona.setIdRolDeportivo(null);
                    personaListOldPersona = em.merge(personaListOldPersona);
                }
            }
            for (Persona personaListNewPersona : personaListNew) {
                if (!personaListOld.contains(personaListNewPersona)) {
                    RolDeportivo oldIdRolDeportivoOfPersonaListNewPersona = personaListNewPersona.getIdRolDeportivo();
                    personaListNewPersona.setIdRolDeportivo(rolDeportivo);
                    personaListNewPersona = em.merge(personaListNewPersona);
                    if (oldIdRolDeportivoOfPersonaListNewPersona != null && !oldIdRolDeportivoOfPersonaListNewPersona.equals(rolDeportivo)) {
                        oldIdRolDeportivoOfPersonaListNewPersona.getPersonaList().remove(personaListNewPersona);
                        oldIdRolDeportivoOfPersonaListNewPersona = em.merge(oldIdRolDeportivoOfPersonaListNewPersona);
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
                Integer id = rolDeportivo.getIdRolDeportivo();
                if (findRolDeportivo(id) == null) {
                    throw new NonexistentEntityException("The rolDeportivo with id " + id + " no longer exists.");
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
            RolDeportivo rolDeportivo;
            try {
                rolDeportivo = em.getReference(RolDeportivo.class, id);
                rolDeportivo.getIdRolDeportivo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rolDeportivo with id " + id + " no longer exists.", enfe);
            }
            List<Persona> personaList = rolDeportivo.getPersonaList();
            for (Persona personaListPersona : personaList) {
                personaListPersona.setIdRolDeportivo(null);
                personaListPersona = em.merge(personaListPersona);
            }
            em.remove(rolDeportivo);
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

    public List<RolDeportivo> findRolDeportivoEntities() {
        return findRolDeportivoEntities(true, -1, -1);
    }

    public List<RolDeportivo> findRolDeportivoEntities(int maxResults, int firstResult) {
        return findRolDeportivoEntities(false, maxResults, firstResult);
    }

    private List<RolDeportivo> findRolDeportivoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RolDeportivo.class));
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

    public RolDeportivo findRolDeportivo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RolDeportivo.class, id);
        } finally {
            em.close();
        }
    }

    public int getRolDeportivoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RolDeportivo> rt = cq.from(RolDeportivo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
