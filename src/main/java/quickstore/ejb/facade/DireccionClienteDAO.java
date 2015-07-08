/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quickstore.ejb.facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import quickstore.ejb.entity.DireccionCliente;

/**
 *
 * @author root
 */
@Stateless
public class DireccionClienteDAO extends AbstractFacade<DireccionCliente> {

    @PersistenceContext(unitName = "quickstore_QUICKSTORE_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DireccionClienteDAO() {
        super(DireccionCliente.class);
    }

    public List<DireccionCliente> getAllDireccionCliente(Integer id) {
        Query q = em.createQuery("SELECT a FROM DireccionCliente a WHERE a.idCliente.idCliente=:xIdCliente ORDER BY a.idDireccion");
        q.setParameter("xIdCliente", id);
        //LOG.log(Level.INFO, "findAllbyTipo: {0}", sql);
        List<DireccionCliente> tr = q.getResultList();
        return tr;
    }
}
