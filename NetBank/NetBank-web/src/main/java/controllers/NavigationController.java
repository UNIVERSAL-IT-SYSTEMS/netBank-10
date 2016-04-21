package controllers;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Daniel Szabo
 */
@Named(value = "navigationController")
@SessionScoped
public class NavigationController implements Serializable {

    public String toListTransaction() {
        return "listAccountTransaction?faces-redirect=true";
    }
    
    public String toListUserAccount(){
        return "listUserAccount?faces-redirect=true";
    }
    
    public String toListUser(){
        return "listUser?faces-redirect=true";
    }
    
    public String toRegistrate(){
        return "registrate?faces-redirect=true";
    }

}
