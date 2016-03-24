package session;

import entities.User;
import services.UserService;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Daniel Szabo
 */
@ManagedBean(name = "logout")
@SessionScoped
public class LogoutController implements Serializable {

    private String loggedInUserName;
    private User loggedInUser;

    @Inject
    UserService userService;

    public String getLoggedInUserName() {
        return loggedInUserName;
    }

    public void setLoggedInUserName(String loggedInUserName) {
        this.loggedInUserName = loggedInUserName;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public String logout() {

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest req
                = (HttpServletRequest) context.getExternalContext().getRequest();

        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

        try {
            req.logout();
        } catch (ServletException ex) {
            Logger.getLogger(LogoutController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "/login?faces-redirect=true";
    }
}
