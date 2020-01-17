/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session.bean;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import modelo.TipoAlbitro;

/**
 *
 * @author Lenovo
 */
@Stateless
public class TipoAlbitroFacade extends AbstractFacade<TipoAlbitro> {

    @PersistenceContext(unitName = "ProyectoDeporteSistemaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoAlbitroFacade() {
        super(TipoAlbitro.class);
    }
    
}
