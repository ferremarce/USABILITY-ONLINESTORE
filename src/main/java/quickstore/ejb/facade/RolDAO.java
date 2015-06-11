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
import quickstore.ejb.entity.Permiso;
import quickstore.ejb.entity.Rol;

/**
 *
 * @author jmferreira1978@gmail.com
 */
@Stateless
public class RolDAO extends AbstractFacade<Rol> {

    @PersistenceContext(unitName = "quickstore_QUICKSTORE_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RolDAO() {
        super(Rol.class);
    }

    public List<Permiso> getPermisoRol(Rol u) {
        Query q = em.createQuery("SELECT a FROM Permiso a WHERE a.idPermiso IN (SELECT DISTINCT b.idPermiso.idPermiso FROM PermisoRol b WHERE b.idRol.idRol=:xIdRol) ORDER BY a.nivel");
        q.setParameter("xIdRol", u.getIdRol());
        List<Permiso> tr = q.getResultList();
        return tr;
    }

    public void borrarTodoPermisoRol(Integer idRol) {
        Query q = em.createQuery("DELETE FROM PermisoRol a WHERE a.idRol.idRol = :xIdRol");
        q.setParameter("xIdRol", idRol);
        q.executeUpdate();
    }

    public List<Rol> getRolDiarioSesiones(Boolean conAdmin) {
        Query q;
        if (conAdmin) {
            q = em.createQuery("SELECT a FROM Rol a WHERE a.descripcionRol LIKE 'DS%' OR a.idRol=1 ");
        } else {
            q = em.createQuery("SELECT a FROM Rol a WHERE a.descripcionRol LIKE 'DS%'");
        }
        List<Rol> tr = q.getResultList();
        return tr;
    }
}
