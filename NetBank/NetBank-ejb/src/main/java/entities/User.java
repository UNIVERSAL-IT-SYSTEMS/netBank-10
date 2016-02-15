package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Daniel Szabo
 */
@Entity(name = "USER_TABLE")
public class User implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String loginName;
    
    private String password;
    
    private String name;
    
    private String email;
    
    @OneToMany
    private Address address;
    
    private String phoneNumber;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfBirth;
    
    @ManyToOne
    private List<Account> accountList;
    
    //Group?
    //private Role role;
    
    
    
}
