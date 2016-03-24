package session;

import entities.User;
import enums.Role;
import services.UserService;
import java.io.Serializable;
import java.security.Principal;
import java.util.Calendar;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;


/**
 *
 * @author Daniel Szabo
 */
@ManagedBean(name="loggedIn")
@SessionScoped
public class SessionController implements Serializable{

    private User user;
    private Integer day;
    private final Role adminPos = Role.ADMIN;
    
    @Inject
    private UserService userService;
    
    @Inject
    private VisitorController visitorBean;

    private int visitorNumber;

    @PostConstruct
    public void setDay() {
        day = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
    }

    public Role getAdminPos() {
        return adminPos;
    }
    
    public Integer getVisitorNumber(){
        if (day < Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
                || (Calendar.getInstance().get(Calendar.DAY_OF_YEAR) == 1 && day != 1)) {
            visitorBean.setNumber(0);
            day = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
        }
        return visitorBean.getNumber();
    }
    
    public void setVisitorNumber(int visitorNumber) {
        this.visitorNumber = visitorNumber;
    }
    
     public User getUser() {
        Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        if (principal != null) {
                user = userService.findByLoginName(principal.getName());
         }
        return user;
    }  
    
}