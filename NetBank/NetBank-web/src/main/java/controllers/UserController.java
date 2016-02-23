
package controllers;

import entities.User;
import enums.Role;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import services.UserService;

/**
 *
 * @author Daniel Szabo
 */
@Named(value = "userController")
@SessionScoped
public class UserController implements Serializable{
    
    @Inject
    UserService userService;
    
    private User user = new User();
    private User selectedUser;
    
    public UserController() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }
    
    public List<User> findAll() {
        return this.userService.getUserList();
    }
    
    public String adminAddUser() {
        addUser();
        return "listUser?faces-redirect=true";
    }
    
    private void addUser() {
        user.setPosition(Role.USER);
        userService.addUser(user);
        this.user = new User();
    }
    
    public String deleteUser() {
    if (selectedUser != null){
        if (!"admin".equals(selectedUser.getLoginName())) {
            userService.removeUser(selectedUser);
            selectedUser = new User();
            return "userList?faces-redirect=true";
            } else {
                FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Deleting error", "This user has leaded projects or you tried to delete admin! >> nice try! "));
                return "userList?faces-redirect=false";
            }
        }
        FacesContext.getCurrentInstance().
        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
            "User has not selected", "You did not select any user!"));
        return "userList?faces-redirect=false";
    }
    
}
