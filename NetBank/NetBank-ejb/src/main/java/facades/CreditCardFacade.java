/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.CreditCard;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Dani
 */
@Stateless
public class CreditCardFacade extends AbstractFacade<CreditCard> implements CreditCardFacadeLocal {
    @PersistenceContext(unitName = "com.mycompany_NetBank-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CreditCardFacade() {
        super(CreditCard.class);
    }

    @Override
    public List<CreditCard> listByAccount(Long id) {
        Query q = em.createNamedQuery("getListByAccount", CreditCard.class); 
        q.setParameter("rId", id);
        return q.getResultList();
    }
    
}
