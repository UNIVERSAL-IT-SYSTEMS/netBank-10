package entities;

import enums.Role;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 *
 * @author Daniel Szabo
 */
@Entity(name = "GROUP_TABLE")
public class Group implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "group_id")
    private Long id;

    private static final long serialVersionUID = 1L;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne
    @JoinColumn(name = "loginName", referencedColumnName = "loginName")
    private User user;

    public Group() {
    }

    public Group(Role role, User user) {
        this.role = role;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        return (object instanceof Group) && (id != null)
                ? id.equals(((Group) object).id)
                : (object == this);
    }

    @Override
    public String toString() {
        return "entities.Group[ id=" + id + " ]";
    }
}