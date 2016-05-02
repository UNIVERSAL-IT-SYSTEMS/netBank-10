package controllers;

import entities.Account;
import entities.Message;
import entities.RegistratedUser;
import entities.Topic;
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
import services.AccountService;
import services.MessageService;
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

    @Inject
    AccountService accountService;
    
    @Inject
    MessageService messageService; 

    private User user = new User();
    private User selectedUser;

    private RegistratedUser regUser = new RegistratedUser();
    private RegistratedUser selectedRegUser;

    private String position = "User";
    private Role originalPosition;

    private String newPassword;

    private List<Account> accountList;
    
    private String message;
    
  

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

    public RegistratedUser getRegUser() {
        return regUser;
    }

    public void setRegUser(RegistratedUser regUser) {
        this.regUser = regUser;
    }

    public RegistratedUser getSelectedRegUser() {
        return selectedRegUser;
    }

    public void setSelectedRegUser(RegistratedUser selectedRegUser) {
        this.selectedRegUser = selectedRegUser;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<User> findAll() {
        return this.userService.getUserList();
    }

    public List<RegistratedUser> findAllReg() {
        return this.userService.getRegUserList();
    }

    public String adminAddUser() {
        addUser();
        return "listUser?faces-redirect=true";
    }

    public String toAddUser() {
        this.user = new User();
        return "addUser?faces-redirect=true";
    }

    public String registerUser() {
        regUser.setPosition(Role.USER);
        userService.register(regUser);
        regUser = new RegistratedUser();
        return "login?faces-redirect=true";
    }

    private void addUser() {
        user.setPosition(Role.USER);
        userService.addUser(user);
        this.user = new User();
    }

    public String deleteUser() {
        if (selectedUser != null) {
            if (!"admin".equals(selectedUser.getLoginName())) {
                List<Account> accountList = accountService.getAllByUser(selectedUser);
                for (Account account : accountList) {
                    account.setDestroy(true);
                    account.setUser(null);
                    accountService.updateAccount(account);
                }
                List<Topic> topicList = messageService.findByUser(selectedUser);
                for(Topic topic : topicList){
                    List<Message> messageList = messageService.findByTopic(topic);
                    topic.setLastMessage(null);
                    messageService.editTopic(topic);
                    for(Message message : messageList){
                        message.setSender(null);
                        message.setTopic(null);
                        messageService.removeMessage(message);
                    }
                    messageList.clear();
                    messageService.removeTopic(topic);
                }
                
                topicList.clear();
                
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

    public String selectedToUpdateProfile(String name) {
        this.selectedUser = userService.findByLoginName(name);
        if (selectedUser != null) {
            this.originalPosition = selectedUser.getPosition();
            this.user = selectedUser;
            return "/user/profilePage?faces-redirect=true";
        } else {
            return "?faces-redirect=false";
        }
    }

    public String updateUser() {
        modifyPosition(this.position);
        this.position = "User";
        userService.editUser(user, originalPosition);
        this.user = new User();
        return "listUser?faces-redirect=true";
    }

    public String updateProfileUser() throws NoSuchAlgorithmException {
        userService.editUser(user, originalPosition);
        this.user = new User();
        return "/user/index?faces-redirect=true";
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

    public String acceptRegister() {
        boolean isAccept = userService.acceptRegister(selectedRegUser);
        this.selectedRegUser = new RegistratedUser();
        
        if(isAccept){
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Felhasználó regisztrálása sikeres volt!",""));
            return "registrate?faces-redirect=true";
        }else{
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Felhasználó regisztrálása helytelen adatok miatt sikertelen volt!",""));
            return "registrate?faces-redirect=true";
        }
        
    }
    
    public String refuseRegister(){
        userService.refuseRegister(selectedRegUser, message);
        this.selectedRegUser = new RegistratedUser();
        this.message = new String();
        return "registrate?faces-redirect=true";
    }

    public String changePassword() {
        if (newPassword != null) {
            user.setPassword(newPassword);
            userService.editUserPassword(user);
        }
        return "/user/index?faces-redirect=true";
    }
    
    public String toUserAccount(User user){
        this.user=user;
        return "/admin/user/account/listUserAccount?faces-redirect=true";
    }
}
