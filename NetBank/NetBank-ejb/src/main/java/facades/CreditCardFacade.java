package facades;

import entities.CreditCard;
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
    
    @Override
    public CreditCard findByCardNumber(Integer cardNumber){
        Query q = em.createNamedQuery("findByCardNumber", CreditCard.class); 
        q.setParameter("rNumber", cardNumber);
        return (CreditCard)q.getSingleResult();
    }
}
