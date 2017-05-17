/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicauca.divsalud.sessionbeans;

import com.unicauca.divsalud.entidades.HistoriaModuloSexualidad;
import com.unicauca.divsalud.entidades.Paciente;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author acer_acer
 */
@Stateless
public class HistoriaModuloSexualidadFacade extends AbstractFacade<HistoriaModuloSexualidad> {

    @PersistenceContext(unitName = "divisionsaludPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HistoriaModuloSexualidadFacade() {
        super(HistoriaModuloSexualidad.class);
    }

    public HistoriaModuloSexualidad findById(int id) {
        Query query = em.createNamedQuery("HistoriaModuloSexualidad.findById");
        query.setParameter("id",id);
        HistoriaModuloSexualidad his = (HistoriaModuloSexualidad) query.getSingleResult();
        return his;
    }

}
