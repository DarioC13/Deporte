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
import modelo.MarcadorJugador;
import modelo.Persona;
import modelo.Sancion;

/**
 *
 * @author Francisco
 */
public class MarcadorJugadorJpaController implements Serializable {

    public MarcadorJugadorJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MarcadorJugador marcadorJugador) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Encuentros idEncuentros = marcadorJugador.getIdEncuentros();
            if (idEncuentros != null) {
                idEncuentros = em.getReference(idEncuentros.getClass(), idEncuentros.getIdEncuentros());
                marcadorJugador.setIdEncuentros(idEncuentros);
            }
            Persona idPersona = marcadorJugador.getIdPersona();
            if (idPersona != null) {
                idPersona = em.getReference(idPersona.getClass(), idPersona.getIdPersona());
                marcadorJugador.setIdPersona(idPersona);
            }
            Sancion idSanciones = marcadorJugador.getIdSanciones();
            if (idSanciones != null) {
                idSanciones = em.getReference(idSanciones.getClass(), idSanciones.getIdSanciones());
                marcadorJugador.setIdSanciones(idSanciones);
            }
            em.persist(marcadorJugador);
            if (idEncuentros != null) {
                idEncuentros.getMarcadorJugadorList().add(marcadorJugador);
                idEncuentros = em.merge(idEncuentros);
            }
            if (idPersona != null) {
                idPersona.getMarcadorJugadorList().add(marcadorJugador);
                idPersona = em.merge(idPersona);
            }
            if (idSanciones != null) {
                idSanciones.getMarcadorJugadorList().add(marcadorJugador);
                idSanciones = em.merge(idSanciones);
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

    public void edit(MarcadorJugador marcadorJugador) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            MarcadorJugador persistentMarcadorJugador = em.find(MarcadorJugador.class, marcadorJugador.getIdMarcador());
            Encuentros idEncuentrosOld = persistentMarcadorJugador.getIdEncuentros();
            Encuentros idEncuentrosNew = marcadorJugador.getIdEncuentros();
            Persona idPersonaOld = persistentMarcadorJugador.getIdPersona();
            Persona idPersonaNew = marcadorJugador.getIdPersona();
            Sancion idSancionesOld = persistentMarcadorJugador.getIdSanciones();
            Sancion idSancionesNew = marcadorJugador.getIdSanciones();
            if (idEncuentrosNew != null) {
                idEncuentrosNew = em.getReference(idEncuentrosNew.getClass(), idEncuentrosNew.getIdEncuentros());
                marcadorJugador.setIdEncuentros(idEncuentrosNew);
            }
            if (idPersonaNew != null) {
                idPersonaNew = em.getReference(idPersonaNew.getClass(), idPersonaNew.getIdPersona());
                marcadorJugador.setIdPersona(idPersonaNew);
            }
            if (idSancionesNew != null) {
                idSancionesNew = em.getReference(idSancionesNew.getClass(), idSancionesNew.getIdSanciones());
                marcadorJugador.setIdSanciones(idSancionesNew);
            }
            marcadorJugador = em.merge(marcadorJugador);
            if (idEncuentrosOld != null && !idEncuentrosOld.equals(idEncuentrosNew)) {
                idEncuentrosOld.getMarcadorJugadorList().remove(marcadorJugador);
                idEncuentrosOld = em.merge(idEncuentrosOld);
            }
            if (idEncuentrosNew != null && !idEncuentrosNew.equals(idEncuentrosOld)) {
                idEncuentrosNew.getMarcadorJugadorList().add(marcadorJugador);
                idEncuentrosNew = em.merge(idEncuentrosNew);
            }
            if (idPersonaOld != null && !idPersonaOld.equals(idPersonaNew)) {
                idPersonaOld.getMarcadorJugadorList().remove(marcadorJugador);
                idPersonaOld = em.merge(idPersonaOld);
            }
            if (idPersonaNew != null && !idPersonaNew.equals(idPersonaOld)) {
                idPersonaNew.getMarcadorJugadorList().add(marcadorJugador);
                idPersonaNew = em.merge(idPersonaNew);
            }
            if (idSancionesOld != null && !idSancionesOld.equals(idSancionesNew)) {
                idSancionesOld.getMarcadorJugadorList().remove(marcadorJugador);
                idSancionesOld = em.merge(idSancionesOld);
            }
            if (idSancionesNew != null && !idSancionesNew.equals(idSancionesOld)) {
                idSancionesNew.getMarcadorJugadorList().add(marcadorJugador);
                idSancionesNew = em.merge(idSancionesNew);
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
                Integer id = marcadorJugador.getIdMarcador();
                if (findMarcadorJugador(id) == null) {
                    throw new NonexistentEntityException("The marcadorJugador with id " + id + " no longer exists.");
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
            MarcadorJugador marcadorJugador;
            try {
                marcadorJugador = em.getReference(MarcadorJugador.class, id);
                marcadorJugador.getIdMarcador();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The marcadorJugador with id " + id + " no longer exists.", enfe);
            }
            Encuentros idEncuentros = marcadorJugador.getIdEncuentros();
            if (idEncuentros != null) {
                idEncuentros.getMarcadorJugadorList().remove(marcadorJugador);
                idEncuentros = em.merge(idEncuentros);
            }
            Persona idPersona = marcadorJugador.getIdPersona();
            if (idPersona != null) {
                idPersona.getMarcadorJugadorList().remove(marcadorJugador);
                idPersona = em.merge(idPersona);
            }
            Sancion idSanciones = marcadorJugador.getIdSanciones();
            if (idSanciones != null) {
                idSanciones.getMarcadorJugadorList().remove(marcadorJugador);
                idSanciones = em.merge(idSanciones);
            }
            em.remove(marcadorJugador);
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

    public List<MarcadorJugador> findMarcadorJugadorEntities() {
        return findMarcadorJugadorEntities(true, -1, -1);
    }

    public List<MarcadorJugador> findMarcadorJugadorEntities(int maxResults, int firstResult) {
        return findMarcadorJugadorEntities(false, maxResults, firstResult);
    }

    private List<MarcadorJugador> findMarcadorJugadorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MarcadorJugador.class));
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

    public MarcadorJugador findMarcadorJugador(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MarcadorJugador.class, id);
        } finally {
            em.close();
        }
    }

    public int getMarcadorJugadorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MarcadorJugador> rt = cq.from(MarcadorJugador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
