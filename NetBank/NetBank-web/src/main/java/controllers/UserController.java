package controllers;

import entities.Account;
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
public class UserController implements Serializable {

    @Inject
    UserService userService;

    private User user = new User();
    private User selectedUser;

    private String position = "User";
    private Role originalPosition;

    private List<Account> accountList;

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

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    public List<User> findAll() {
        return this.userService.getUserList();
    }

    public String adminAddUser() {
        addUser();
        return "listUser?faces-redirect=true";
    }

    public String toAddUser() {
        this.user = new User();
        return "addUser?faces-redirect=true";
    }

    private void addUser() {
        user.setPosition(Role.USER);
        userService.addUser(user);
        this.user = new User();
    }

    public String deleteUser() {
        if (selectedUser != null) {
            if (!"admin".equals(selectedUser.getLoginName())) {
                userService.removeUser(selectedUser);
                selectedUser = new User();
                return "listUser?faces-redirect=true";
            } else {
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Deleting error", "This user has leaded projects or you tried to delete admin! >> nice try! "));
                return "listUser?faces-redirect=false";
            }
        } else {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "User has not selected", "You did not select any user!"));
            return "listUser?faces-redirect=false";
        }
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
        this.user = new User();
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

    public String selectedToAccountList() {
        if (selectedUser != null) {
            this.user = selectedUser;
            selectedUser = new User();
            accountList = userService.getAccountList(user);
            return "/admin/user/account/listUserAccount?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Felhasználó nincs kiválasztva", "Felhasználó nincs kiválasztva"));

            return "/admin/user/account/listUser?faces-redirect=false";
        }
    }

    public String selectedToAccountList(User parUser) {
        if (parUser != null) {
            this.user = parUser;
            return "/admin/user/account/listUserAccount?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Felhasználó nincs kiválasztva", "Felhasználó nincs kiválasztva"));

            return "listAccount?faces-redirect=false";
        }
    }

    public String setNull() {
        user = new User();
        return "listUser?faces-redirect=true";
    }
}