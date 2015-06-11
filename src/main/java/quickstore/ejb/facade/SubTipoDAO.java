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
import quickstore.ejb.entity.SubTipo;

/**
 *
 * @author jmferreira1978@gmail.com
 */
@Stateless
public class SubTipoDAO extends AbstractFacade<SubTipo>  {

    @PersistenceContext(unitName = "quickstore_QUICKSTORE_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SubTipoDAO() {
        super(SubTipo.class);
    }

    public List<SubTipo> findAllbyTipo(Integer idTipo) {
        String sql = "SELECT a FROM SubTipo a WHERE a.idTipo.idTipo=:xIdTipo ORDER BY a.descripcionSubTipo";
        Query q = em.createQuery(sql);
        q.setParameter("xIdTipo", idTipo);
        //LOG.log(Level.INFO, "findAllbyTipo: {0}", sql);
        List<SubTipo> tr = q.getResultList();
        return tr;
    }
}
