package controllers;

import entities.Message;
import entities.Topic;
import entities.User;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import services.MessageService;

/**
 *
 * @author Daniel Szabo
 */
@Named(value = "messageController")
@SessionScoped
public class MessageController implements Serializable{
    
    @Inject
    MessageService messageService;
    
    private Topic topic = new Topic();
    private Topic selectedTopic = new Topic();
    
    private Message message = new Message();
    
   
    
    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Topic getSelectedTopic() {
        return selectedTopic;
    }

    public void setSelectedTopic(Topic selectedTopic) {
        this.selectedTopic = selectedTopic;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
    
   public List<Topic> findAllTopic(){
        return messageService.findAllTopic();
    }

    public List<Topic> findTopicByUser(User user){
        return messageService.findByUser(user);
    }
    
    public List<Message> findMessageByTopic(){
        return messageService.findByTopic(selectedTopic);
    }
            
            
     public String navigateToTopic(Topic parTopic){
        this.selectedTopic = parTopic;
        return "listMessages?faces-redirect=true";
    }
    
    
     public void createTopic(User user){
         topic.setUser(user);
         messageService.createTopic(topic);
         
         
         message.setDate(new Date());
         message.setSender(user);
         message.setTopic(topic);
         
         messageService.createMessage(message);
         
         topic.setLastMessage(message);
         messageService.editTopic(topic);
         
         this.message = new Message();
         this.topic= new Topic();
     }
     
     public void createMessage(User user){
         message.setSender(user);
         message.setDate(new Date());
         message.setTopic(selectedTopic);
         messageService.createMessage(message);
         
         selectedTopic.setLastMessage(message);
         messageService.editTopic(selectedTopic);
         
         this.message = new Message();
     }    
     
     public void deleteTopic(Topic parTopic){
         
     }
}
