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
import quickstore.ejb.entity.Articulo;

/**
 *
 * @author root
 */
@Stateless
public class ArticuloDAO extends AbstractFacade<Articulo> {

    @PersistenceContext(unitName = "quickstore_QUICKSTORE_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ArticuloDAO() {
        super(Articulo.class);
    }

    public List<Articulo> findAllbyCriterio(String criterio) {
        Query q = em.createQuery("SELECT a FROM Articulo a WHERE UPPER(a.nombreArticulo) LIKE :xCriterio ORDER BY a.nombreArticulo ");
        q.setParameter("xCriterio", "%" + criterio.toUpperCase() + "%");
        //LOG.log(Level.INFO, "findAllbyTipo: {0}", sql);
        List<Articulo> tr = q.getResultList();
        return tr;
    }

    public List<Articulo> findAllbyCategoria(Integer idCategoria) {
        Query q = em.createQuery("SELECT a FROM Articulo a WHERE a.idCategoria.idSubTipo=:xIdCategoria ORDER BY a.nombreArticulo ");
        q.setParameter("xIdCategoria", idCategoria);
        //LOG.log(Level.INFO, "findAllbyTipo: {0}", sql);
        List<Articulo> tr = q.getResultList();
        return tr;
    }

    public List<Articulo> findTopVendidos(Integer top) {
        Query q = em.createQuery("SELECT a FROM Articulo a ORDER BY a.nombreArticulo ");
        q.setMaxResults(top);
        //LOG.log(Level.INFO, "findAllbyTipo: {0}", sql);
        List<Articulo> tr = q.getResultList();
        return tr;
    }
}
