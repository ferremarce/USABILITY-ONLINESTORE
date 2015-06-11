/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quickstore.ejb.facade;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author jmferreira1978@gmail.com
 * @param <T>
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;
    static final Logger LOG = Logger.getLogger(AbstractFacade.class.getName());

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        try {
            getEntityManager().persist(entity);
            LOG.log(Level.INFO, "Creación exitosa en Entidad: " + entity.getClass() + " [" + entity.toString() + "]", entity);
        } catch (DatabaseException ex) {
            LOG.log(Level.SEVERE, "Error en Creación en la entidad: " + entity.getClass() + " [" + entity.toString() + "]", ex);
        }
    }

    public void update(T entity) {
        try {
            getEntityManager().merge(entity);
            LOG.log(Level.INFO, "Actualización exitosa en Entidad: " + entity.getClass() + " [" + entity.toString() + "]", entity);
        } catch (DatabaseException ex) {
            LOG.log(Level.SEVERE, "Error en Actualización en la entidad: " + entity.getClass() + " [" + entity.toString() + "]", ex);
        }
    }

    public void remove(T entity) {
        try {
            getEntityManager().remove(getEntityManager().merge(entity));
            LOG.log(Level.INFO, "Borrado exitoso en Entidad: " + entity.getClass() + " [" + entity.toString() + "]", entity);
        } catch (DatabaseException ex) {
            LOG.log(Level.SEVERE, "Error en Borrado en la entidad: " + entity.getClass() + " [" + entity.toString() + "]", ex);
        }

    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findAllSorted(String field, String order) {
        String sql = "SELECT a FROM " + this.entityClass.getName() + " a ORDER BY a." + field + " " + order;
        LOG.log(Level.INFO, "findAllSorted: {0}", sql);
        Query q = getEntityManager().createQuery(sql);
        List<T> tr = q.getResultList();
        return tr;
    }

    public List<T> findAllIn(String field, List<Integer> listaValores) {
        String sql = "SELECT a FROM " + this.entityClass.getName() + " a WHERE a." + field + " IN :lista";
        LOG.log(Level.INFO, "findAllIn: {0}", sql);
        Query q = getEntityManager().createQuery(sql);
        q.setParameter("lista", listaValores);
        List<T> tr = q.getResultList();
        return tr;
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
