package facades;

import entities.Message;
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
public class MessageFacade extends AbstractFacade<Message> implements MessageFacadeLocal {
    @PersistenceContext(unitName = "com.mycompany_NetBank-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MessageFacade() {
        super(Message.class);
    }
    
    @Override
    public List<Message> findByTopic(Long topicId){
        Query q = em.createNamedQuery("getMessagesByTopic", Message.class); 
        q.setParameter("rId", topicId);
        return q.getResultList();
    }
    
}
