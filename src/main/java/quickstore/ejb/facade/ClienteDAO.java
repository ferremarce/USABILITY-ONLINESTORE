/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quickstore.ejb.facade;

import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import quickstore.ejb.entity.Cliente;

/**
 *
 * @author root
 */
@Stateless
public class ClienteDAO extends AbstractFacade<Cliente> {

    @PersistenceContext(unitName = "quickstore_QUICKSTORE_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClienteDAO() {
        super(Cliente.class);
    }

    public Cliente getCliente(String mail) {
        Query q = em.createQuery("SELECT a FROM Cliente a WHERE a.email LIKE :xCuenta");
        q.setParameter("xCuenta", mail);
        try {
            Cliente tr = (Cliente) q.getSingleResult();
            return tr;
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error al recuperar el cliente de la cuenta [" + mail + "]", e);
            return null;
        }
    }

}
