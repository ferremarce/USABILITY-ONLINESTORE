/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quickstore.ejb.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import quickstore.ejb.entity.Permiso;

/**
 *
 * @author jmferreira1978@gmail.com
 */
@Stateless
public class PermisoDAO extends AbstractFacade<Permiso>  {
    @PersistenceContext(unitName = "quickstore_QUICKSTORE_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PermisoDAO() {
        super(Permiso.class);
    }
    
}
