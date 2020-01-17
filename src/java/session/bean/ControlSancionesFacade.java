/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session.bean;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import modelo.ControlSanciones;

/**
 *
 * @author Lenovo
 */
@Stateless
public class ControlSancionesFacade extends AbstractFacade<ControlSanciones> {

    @PersistenceContext(unitName = "ProyectoDeporteSistemaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ControlSancionesFacade() {
        super(ControlSanciones.class);
    }
    
}
