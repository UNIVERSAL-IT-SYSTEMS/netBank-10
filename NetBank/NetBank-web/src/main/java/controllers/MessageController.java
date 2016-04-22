package controllers;

import entities.Message;
import entities.Topic;
import entities.User;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
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
    private Message newMessage = new Message();
    
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

    public Message getNewMessage() {
        return newMessage;
    }

    public void setNewMessage(Message newMessage) {
        this.newMessage = newMessage;
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
        return "listTopic?faces-redirect=true";
    }
    
    
     public String createTopic(User user){
         topic.setUser(user);
         messageService.createTopic(topic);
         
         newMessage.setDate(new Date());
         newMessage.setSender(user);
         newMessage.setTopic(topic);

         
         messageService.createMessage(newMessage);
         
         topic.setLastMessage(newMessage);
         messageService.editTopic(topic);
         
         this.newMessage = new Message();
         this.topic = new Topic();
         return "listTopic?faces-redirect=true";
     }
     
     public String createMessage(User user){
         message.setSender(user);
         message.setDate(new Date());
         message.setTopic(selectedTopic);
         messageService.createMessage(message);
         
         selectedTopic.setLastMessage(message);
         messageService.editTopic(selectedTopic);
         
         this.message = new Message();
         return "listTopic?faces-redirect=true";
     }    
     
     public void deleteTopic(Topic parTopic){
         
     }
}
