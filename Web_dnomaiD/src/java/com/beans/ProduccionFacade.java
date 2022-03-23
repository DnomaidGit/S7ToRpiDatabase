/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

import com.entity.Produccion;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author JLO
 */
@Stateless
public class ProduccionFacade extends AbstractFacade<Produccion> {

    @PersistenceContext(unitName = "dnomaiD_WebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProduccionFacade() {
        super(Produccion.class);
    }
    
}
