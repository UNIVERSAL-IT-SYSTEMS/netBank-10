package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Daniel Szabo
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "getNotDestroyByUser", query = "SELECT a FROM Account a WHERE a.user.id = :rId and a.destroy = false"),
    @NamedQuery(name = "getAllByUser", query = "SELECT a FROM Account a WHERE a.user.id = :rId"),
    @NamedQuery(name = "getAllNotDestroy", query = "SELECT a FROM Account a WHERE a.destroy = false"),
})   
public class Account implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    private int accountNumber;

    private int balance;
    
    private boolean destroy = false;

    @OneToMany(mappedBy = "sender")
    private List<Transaction> sendTransactionList;
    
    @OneToMany(mappedBy = "reciever")
    private List<Transaction> recievedTransactionList;
    
    @OneToMany(mappedBy = "account")
    private List<CreditCard> creditCardList;
    

    public Account() {
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public boolean isDestroy() {
        return destroy;
    }

    public void setDestroy(boolean destroy) {
        this.destroy = destroy;
    }
    
    public List<Transaction> getSendTransactionList() {
        return sendTransactionList;
    }

    public void setSendTransactionList(List<Transaction> sendTransactionList) {
        this.sendTransactionList = sendTransactionList;
    }

    public List<Transaction> getRecievedTransactionList() {
        return recievedTransactionList;
    }

    public void setRecievedTransactionList(List<Transaction> recievedTransactionList) {
        this.recievedTransactionList = recievedTransactionList;
    }

    public List<CreditCard> getCreditCardList() {
        return creditCardList;
    }

    public void setCreditCardList(List<CreditCard> creditCardList) {
        this.creditCardList = creditCardList;
    }

    
}
