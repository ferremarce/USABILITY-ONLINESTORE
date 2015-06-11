/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quickstore.ejb.facade;

import java.util.List;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import quickstore.ejb.entity.Permiso;
import quickstore.ejb.entity.Usuario;

/**
 *
 * @author jmferreira1978@gmail.com
 */
@Stateless
public class UsuarioDAO extends AbstractFacade<Usuario>  {
    @PersistenceContext(unitName = "quickstore_QUICKSTORE_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioDAO() {
        super(Usuario.class);
    }
    /**
     * Obtiene una Lista de Usuarios.
     *
     * @param criterio
     * @return Lista de Usuarios.
     */
    public List<Usuario> getAllUsuario(String criterio) {
        Query q = em.createQuery("SELECT a FROM Usuario a WHERE UPPER(a.nombres) LIKE :xCriterio OR UPPER(a.apellidos) LIKE :xCriterio ORDER BY a.apellidos, a.nombres");
        if (criterio.compareTo("") != 0) {
            q.setParameter("xCriterio", "%" + criterio.toUpperCase() + "%");
        } else {
            q.setParameter("xCriterio", "123456");
        }
        List<Usuario> tr = q.getResultList();
        return tr;

    }

    /**
     * Obtiene una Lista de Permisos de un Usuario.
     *
     * @param u
     * @return Lista de Permisos.
     */
    public List<Permiso> getPermisoUsuario(Usuario u) {
        Query q = em.createQuery("SELECT a FROM Permiso a WHERE a.idPermiso IN (SELECT b.idPermiso.idPermiso FROM PermisoRol b WHERE b.idRol.idRol=:xIdRol) ORDER BY a.nivel");
        q.setParameter("xIdRol", u.getIdRol().getIdRol());
        List<Permiso> tr = q.getResultList();
        return tr;
    }

    /**
     * Obtener un usuario por la cuenta
     *
     * @param cuenta
     * @return
     */
    public Usuario getUsuario(String cuenta) {
        Query q = em.createQuery("SELECT a FROM Usuario a WHERE a.cuenta=:xCuenta");
        q.setParameter("xCuenta", cuenta);
        try {
            Usuario tr = (Usuario) q.getSingleResult();
            return tr;
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error al recuperar el usuario de la cuenta [" + cuenta + "]", e);
            return null;
        }
    }

}
