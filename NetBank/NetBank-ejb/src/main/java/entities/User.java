package entities;

import enums.Role;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Daniel Szabo
 */
@Entity(name = "USER_TABLE")
@NamedQuery(name = "getUserByLoginName", query = "SELECT u FROM USER_TABLE u WHERE u.loginName = :lName")
public class User implements Serializable {
    
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;
    
    @NotNull
    private String loginName;
    
    @NotNull
    private String password;
    
    private String name;
    
    @NotNull
    private String email;
    
    private Role position;
    
    private String address;
    
    private String phoneNumber;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfBirth;
    
    @OneToMany(mappedBy = "user")
    private List<Account> accountList;

    public User() {
    }

    public User(String loginName, String password) {
        this.loginName = loginName;
        this.password = password;
    }

    public Long getId() {
        return id;
    }
    
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    public Role getPosition() {
        return position;
    }

    public void setPosition(Role position) {
        this.position = position;
    }

}
