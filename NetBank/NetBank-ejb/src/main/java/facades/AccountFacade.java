package facades;

import entities.Account;
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
public class AccountFacade extends AbstractFacade<Account> implements AccountFacadeLocal {

    @PersistenceContext(unitName = "com.mycompany_NetBank-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccountFacade() {
        super(Account.class);
    }
    
    public List<Account> getAllByUser(Long id){
        Query q = em.createNamedQuery("getAllByUser", Account.class); 
        q.setParameter("rId", id);
        return q.getResultList();
    }
    
    public List<Account> getNotDestroyByUser(Long id){
        Query q = em.createNamedQuery("getNotDestroyByUser", Account.class); 
        q.setParameter("rId", id);
        return q.getResultList();
    }
    
    public List<Account> getAllNotDestroy(){
        Query q = em.createNamedQuery("getAllNotDestroy", Account.class); 
        return q.getResultList();
    }
    
    
}
