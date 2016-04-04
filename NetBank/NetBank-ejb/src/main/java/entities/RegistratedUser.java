package entities;

import enums.Role;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Daniel Szabo
 */
@Entity(name = "REGISTRATED_USER")
@NamedQuery(name = "getAll", query = "SELECT r FROM REGISTRATED_USER r")
public class RegistratedUser implements Serializable {
    
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;
    
    private String loginName;
    
    private String password;
    
    private String name;
    
    private String email;
    
    private Role position;
    
    private String address;
    
    private String phoneNumber;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfBirth;

    public RegistratedUser() {
    }

    public RegistratedUser(String loginName, String password) {
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

    public Role getPosition() {
        return position;
    }

    public void setPosition(Role position) {
        this.position = position;
    }
}