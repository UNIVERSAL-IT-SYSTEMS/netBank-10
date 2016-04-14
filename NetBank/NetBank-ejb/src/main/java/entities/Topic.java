package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author Daniel Szabo
 */
@Entity()
@NamedQueries({
    @NamedQuery(name = "getTopicByUser", 
            query = "SELECT t FROM Topic t WHERE t.user.id = :rId ORDER BY t.lastMessage.date DESC"),
    @NamedQuery(name = "getAllTopic", 
            query = "SELECT t FROM Topic t ORDER BY t.lastMessage.date DESC")
}) 

public class Topic implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;
    
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    private String theme;
    
    @OneToMany(mappedBy = "topic", cascade = CascadeType.REMOVE)
    private List<Message> messageList;
    
    @ManyToOne
    @JoinColumn(name = "last_message")
    private Message lastMessage;

    public Topic() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    public Message getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
    }
  
}
