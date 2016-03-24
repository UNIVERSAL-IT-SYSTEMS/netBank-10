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

/**
 *
 * @author Daniel Szabo
 */
@Entity
@NamedQuery(name = "getSpecifyAccount", query = "SELECT a FROM Account a WHERE a.user.id = :rId")
public class Account implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private int accountNumber;

    private int balance;

    @OneToMany(mappedBy = "sender")
    private List<Transaction> sendTransactionList;
    
    @OneToMany(mappedBy = "reciever")
    private List<Transaction> recievedTransactionList;
    

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

}
