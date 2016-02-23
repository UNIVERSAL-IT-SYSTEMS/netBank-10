
package controllers;

import entities.User;
import enums.Role;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
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
    
    private String position = "User";
    private Role originalPosition;
    
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Role getOriginalPosition() {
        return originalPosition;
    }

    public void setOriginalPosition(Role originalPosition) {
        this.originalPosition = originalPosition;
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
    
    public int getAccountNumber(User user){
       return userService.findById(user.getId()).getAccountList().size();
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
    
    
    public String selectedToUpdate() {
        if (selectedUser != null) {
            this.originalPosition = selectedUser.getPosition();
            this.user = selectedUser;
            return "modifyUser?faces-redirect=true";
        }
        FacesContext.getCurrentInstance().
            addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
            "User has not selected", "You did not select any user!"));
        return "listUser?faces-redirect=false";
    }

    public String updateUser() throws NoSuchAlgorithmException {
        modifyPosition(this.position);
        this.position = "User";
        userService.editUser(user, originalPosition);
        user = new User();
        return "listUser?faces-redirect=true";
    }
    
    public void modifyPosition(String position) {
        switch (position) {
            case "Admin":
                this.user.setPosition(Role.ADMIN);
                break;
            default:
                this.user.setPosition(Role.USER);
                break;
        }
    }
    
    public String setNull() {
        user = new User();
        return "listUser?faces-redirect=true";
    }
}
