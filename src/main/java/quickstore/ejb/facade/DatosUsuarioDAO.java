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
import quickstore.ejb.entity.DatosUsuario;

/**
 *
 * @author root
 */
@Stateless
public class DatosUsuarioDAO extends AbstractFacade<DatosUsuario> {

    @PersistenceContext(unitName = "quickstore_QUICKSTORE_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DatosUsuarioDAO() {
        super(DatosUsuario.class);
    }

    public List<DatosUsuario> findAllbyEmail(String email) {
        Query q = em.createQuery("SELECT a FROM DatosUsuario a WHERE a.email LIKE :xEmail ");
        q.setParameter("xEmail", email);
        //LOG.log(Level.INFO, "findAllbyTipo: {0}", sql);
        List<DatosUsuario> tr = q.getResultList();
        return tr;
    }

}
