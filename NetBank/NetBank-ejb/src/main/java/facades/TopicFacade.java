package facades;

import entities.Topic;
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
public class TopicFacade extends AbstractFacade<Topic> implements TopicFacadeLocal {
    @PersistenceContext(unitName = "com.mycompany_NetBank-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TopicFacade() {
        super(Topic.class);
    }
    
    @Override
    public List<Topic> findByUser(Long userId){
        Query q = em.createNamedQuery("getTopicByUser", Topic.class); 
        q.setParameter("rId", userId);
        return q.getResultList();
    }
    
    @Override
    public List<Topic> getAll(){
        Query q = em.createNamedQuery("getAllTopic", Topic.class); 
        return q.getResultList();
    }
    
}
