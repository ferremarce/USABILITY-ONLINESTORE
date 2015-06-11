/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quickstore.ejb.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import quickstore.ejb.entity.PermisoRol;

/**
 *
 * @author jmferreira1978@gmail.com
 */
@Stateless
public class PermisoRolDAO extends AbstractFacade<PermisoRol> {
    @PersistenceContext(unitName = "quickstore_QUICKSTORE_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PermisoRolDAO() {
        super(PermisoRol.class);
    }
    
}
