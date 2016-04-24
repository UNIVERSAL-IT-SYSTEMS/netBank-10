/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.RegistratedUser;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Daniel Szabo
 */
@Stateless
public class RegistratedUserFacade extends AbstractFacade<RegistratedUser> implements RegistratedUserFacadeLocal {
    @PersistenceContext(unitName = "com.mycompany_NetBank-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RegistratedUserFacade() {
        super(RegistratedUser.class);
    }
    
    @Override
    public List<RegistratedUser> getAll(){
        Query q = em.createNamedQuery("getAll", RegistratedUser.class);
        return q.getResultList();
    }
    
}
