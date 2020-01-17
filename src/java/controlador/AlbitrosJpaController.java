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
import modelo.TipoAlbitro;
import modelo.ArbitrosEncuentros;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import modelo.Albitros;

/**
 *
 * @author Francisco
 */
public class AlbitrosJpaController implements Serializable {

    public AlbitrosJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Albitros albitros) throws RollbackFailureException, Exception {
        if (albitros.getArbitrosEncuentrosList() == null) {
            albitros.setArbitrosEncuentrosList(new ArrayList<ArbitrosEncuentros>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Persona idPersona = albitros.getIdPersona();
            if (idPersona != null) {
                idPersona = em.getReference(idPersona.getClass(), idPersona.getIdPersona());
                albitros.setIdPersona(idPersona);
            }
            TipoAlbitro idTipoAlbitro = albitros.getIdTipoAlbitro();
            if (idTipoAlbitro != null) {
                idTipoAlbitro = em.getReference(idTipoAlbitro.getClass(), idTipoAlbitro.getIdTipoAlbitro());
                albitros.setIdTipoAlbitro(idTipoAlbitro);
            }
            List<ArbitrosEncuentros> attachedArbitrosEncuentrosList = new ArrayList<ArbitrosEncuentros>();
            for (ArbitrosEncuentros arbitrosEncuentrosListArbitrosEncuentrosToAttach : albitros.getArbitrosEncuentrosList()) {
                arbitrosEncuentrosListArbitrosEncuentrosToAttach = em.getReference(arbitrosEncuentrosListArbitrosEncuentrosToAttach.getClass(), arbitrosEncuentrosListArbitrosEncuentrosToAttach.getIdAlbitrosEncuentroscol());
                attachedArbitrosEncuentrosList.add(arbitrosEncuentrosListArbitrosEncuentrosToAttach);
            }
            albitros.setArbitrosEncuentrosList(attachedArbitrosEncuentrosList);
            em.persist(albitros);
            if (idPersona != null) {
                idPersona.getAlbitrosList().add(albitros);
                idPersona = em.merge(idPersona);
            }
            if (idTipoAlbitro != null) {
                idTipoAlbitro.getAlbitrosList().add(albitros);
                idTipoAlbitro = em.merge(idTipoAlbitro);
            }
            for (ArbitrosEncuentros arbitrosEncuentrosListArbitrosEncuentros : albitros.getArbitrosEncuentrosList()) {
                Albitros oldIdAlbitroOfArbitrosEncuentrosListArbitrosEncuentros = arbitrosEncuentrosListArbitrosEncuentros.getIdAlbitro();
                arbitrosEncuentrosListArbitrosEncuentros.setIdAlbitro(albitros);
                arbitrosEncuentrosListArbitrosEncuentros = em.merge(arbitrosEncuentrosListArbitrosEncuentros);
                if (oldIdAlbitroOfArbitrosEncuentrosListArbitrosEncuentros != null) {
                    oldIdAlbitroOfArbitrosEncuentrosListArbitrosEncuentros.getArbitrosEncuentrosList().remove(arbitrosEncuentrosListArbitrosEncuentros);
                    oldIdAlbitroOfArbitrosEncuentrosListArbitrosEncuentros = em.merge(oldIdAlbitroOfArbitrosEncuentrosListArbitrosEncuentros);
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

    public void edit(Albitros albitros) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Albitros persistentAlbitros = em.find(Albitros.class, albitros.getIdAlbitros());
            Persona idPersonaOld = persistentAlbitros.getIdPersona();
            Persona idPersonaNew = albitros.getIdPersona();
            TipoAlbitro idTipoAlbitroOld = persistentAlbitros.getIdTipoAlbitro();
            TipoAlbitro idTipoAlbitroNew = albitros.getIdTipoAlbitro();
            List<ArbitrosEncuentros> arbitrosEncuentrosListOld = persistentAlbitros.getArbitrosEncuentrosList();
            List<ArbitrosEncuentros> arbitrosEncuentrosListNew = albitros.getArbitrosEncuentrosList();
            if (idPersonaNew != null) {
                idPersonaNew = em.getReference(idPersonaNew.getClass(), idPersonaNew.getIdPersona());
                albitros.setIdPersona(idPersonaNew);
            }
            if (idTipoAlbitroNew != null) {
                idTipoAlbitroNew = em.getReference(idTipoAlbitroNew.getClass(), idTipoAlbitroNew.getIdTipoAlbitro());
                albitros.setIdTipoAlbitro(idTipoAlbitroNew);
            }
            List<ArbitrosEncuentros> attachedArbitrosEncuentrosListNew = new ArrayList<ArbitrosEncuentros>();
            for (ArbitrosEncuentros arbitrosEncuentrosListNewArbitrosEncuentrosToAttach : arbitrosEncuentrosListNew) {
                arbitrosEncuentrosListNewArbitrosEncuentrosToAttach = em.getReference(arbitrosEncuentrosListNewArbitrosEncuentrosToAttach.getClass(), arbitrosEncuentrosListNewArbitrosEncuentrosToAttach.getIdAlbitrosEncuentroscol());
                attachedArbitrosEncuentrosListNew.add(arbitrosEncuentrosListNewArbitrosEncuentrosToAttach);
            }
            arbitrosEncuentrosListNew = attachedArbitrosEncuentrosListNew;
            albitros.setArbitrosEncuentrosList(arbitrosEncuentrosListNew);
            albitros = em.merge(albitros);
            if (idPersonaOld != null && !idPersonaOld.equals(idPersonaNew)) {
                idPersonaOld.getAlbitrosList().remove(albitros);
                idPersonaOld = em.merge(idPersonaOld);
            }
            if (idPersonaNew != null && !idPersonaNew.equals(idPersonaOld)) {
                idPersonaNew.getAlbitrosList().add(albitros);
                idPersonaNew = em.merge(idPersonaNew);
            }
            if (idTipoAlbitroOld != null && !idTipoAlbitroOld.equals(idTipoAlbitroNew)) {
                idTipoAlbitroOld.getAlbitrosList().remove(albitros);
                idTipoAlbitroOld = em.merge(idTipoAlbitroOld);
            }
            if (idTipoAlbitroNew != null && !idTipoAlbitroNew.equals(idTipoAlbitroOld)) {
                idTipoAlbitroNew.getAlbitrosList().add(albitros);
                idTipoAlbitroNew = em.merge(idTipoAlbitroNew);
            }
            for (ArbitrosEncuentros arbitrosEncuentrosListOldArbitrosEncuentros : arbitrosEncuentrosListOld) {
                if (!arbitrosEncuentrosListNew.contains(arbitrosEncuentrosListOldArbitrosEncuentros)) {
                    arbitrosEncuentrosListOldArbitrosEncuentros.setIdAlbitro(null);
                    arbitrosEncuentrosListOldArbitrosEncuentros = em.merge(arbitrosEncuentrosListOldArbitrosEncuentros);
                }
            }
            for (ArbitrosEncuentros arbitrosEncuentrosListNewArbitrosEncuentros : arbitrosEncuentrosListNew) {
                if (!arbitrosEncuentrosListOld.contains(arbitrosEncuentrosListNewArbitrosEncuentros)) {
                    Albitros oldIdAlbitroOfArbitrosEncuentrosListNewArbitrosEncuentros = arbitrosEncuentrosListNewArbitrosEncuentros.getIdAlbitro();
                    arbitrosEncuentrosListNewArbitrosEncuentros.setIdAlbitro(albitros);
                    arbitrosEncuentrosListNewArbitrosEncuentros = em.merge(arbitrosEncuentrosListNewArbitrosEncuentros);
                    if (oldIdAlbitroOfArbitrosEncuentrosListNewArbitrosEncuentros != null && !oldIdAlbitroOfArbitrosEncuentrosListNewArbitrosEncuentros.equals(albitros)) {
                        oldIdAlbitroOfArbitrosEncuentrosListNewArbitrosEncuentros.getArbitrosEncuentrosList().remove(arbitrosEncuentrosListNewArbitrosEncuentros);
                        oldIdAlbitroOfArbitrosEncuentrosListNewArbitrosEncuentros = em.merge(oldIdAlbitroOfArbitrosEncuentrosListNewArbitrosEncuentros);
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
                Integer id = albitros.getIdAlbitros();
                if (findAlbitros(id) == null) {
                    throw new NonexistentEntityException("The albitros with id " + id + " no longer exists.");
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
            Albitros albitros;
            try {
                albitros = em.getReference(Albitros.class, id);
                albitros.getIdAlbitros();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The albitros with id " + id + " no longer exists.", enfe);
            }
            Persona idPersona = albitros.getIdPersona();
            if (idPersona != null) {
                idPersona.getAlbitrosList().remove(albitros);
                idPersona = em.merge(idPersona);
            }
            TipoAlbitro idTipoAlbitro = albitros.getIdTipoAlbitro();
            if (idTipoAlbitro != null) {
                idTipoAlbitro.getAlbitrosList().remove(albitros);
                idTipoAlbitro = em.merge(idTipoAlbitro);
            }
            List<ArbitrosEncuentros> arbitrosEncuentrosList = albitros.getArbitrosEncuentrosList();
            for (ArbitrosEncuentros arbitrosEncuentrosListArbitrosEncuentros : arbitrosEncuentrosList) {
                arbitrosEncuentrosListArbitrosEncuentros.setIdAlbitro(null);
                arbitrosEncuentrosListArbitrosEncuentros = em.merge(arbitrosEncuentrosListArbitrosEncuentros);
            }
            em.remove(albitros);
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

    public List<Albitros> findAlbitrosEntities() {
        return findAlbitrosEntities(true, -1, -1);
    }

    public List<Albitros> findAlbitrosEntities(int maxResults, int firstResult) {
        return findAlbitrosEntities(false, maxResults, firstResult);
    }

    private List<Albitros> findAlbitrosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Albitros.class));
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

    public Albitros findAlbitros(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Albitros.class, id);
        } finally {
            em.close();
        }
    }

    public int getAlbitrosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Albitros> rt = cq.from(Albitros.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
