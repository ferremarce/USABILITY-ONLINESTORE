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
import quickstore.ejb.entity.ArticuloAdjunto;

/**
 *
 * @author root
 */
@Stateless
public class ArticuloAdjuntoDAO extends AbstractFacade<ArticuloAdjunto> {
    @PersistenceContext(unitName = "quickstore_QUICKSTORE_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ArticuloAdjuntoDAO() {
        super(ArticuloAdjunto.class);
    }
    
     public List<ArticuloAdjunto> findAllbyArticulo(Integer idArticulo) {
        Query q = em.createQuery("SELECT a FROM ArticuloAdjunto a WHERE a.idArticulo.idArticulo=:xIdArticulo ");
        q.setParameter("xIdArticulo", idArticulo);
        List<ArticuloAdjunto> tr = q.getResultList();
        return tr;
    }
}
