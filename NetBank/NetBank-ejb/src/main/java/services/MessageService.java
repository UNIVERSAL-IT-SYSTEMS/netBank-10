package services;

import entities.Message;
import entities.Topic;
import entities.User;
import facades.MessageFacadeLocal;
import facades.TopicFacadeLocal;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import logging.LoggingInterceptor;

/**
 *
 * @author Daniel Szabo
 */

@Stateless
@LocalBean
@Interceptors(LoggingInterceptor.class)
public class MessageService {
    
    @Inject 
    TopicFacadeLocal topicFacade;
    
    @Inject
    MessageFacadeLocal messageFacade;
    
    public void createTopic(Topic topic){
        topicFacade.create(topic);
    }
    
    public void editTopic(Topic topic){
        topicFacade.edit(topic);
    }
    
    public void createMessage(Message message){
        messageFacade.create(message);
    }
    
    public List<Topic> findAllTopic(){
        return topicFacade.getAll();
    }
    
    public List<Topic> findByUser(User user){
        return topicFacade.findByUser(user.getId());
    }
    
    public List<Message> findByTopic(Topic topic){
        return messageFacade.findByTopic(topic.getId());
    }
}
