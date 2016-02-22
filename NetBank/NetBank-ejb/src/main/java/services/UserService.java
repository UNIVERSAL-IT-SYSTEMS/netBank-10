package services;

import entities.Group;
import entities.User;
import enums.Role;
import facades.AddressFacadeLocal;
import facades.GroupFacadeLocal;
import facades.UserFacadeLocal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import logging.LoggingInterceptor;

/**
 *
 * @author Daniel Szabo
 */
@Stateless
@LocalBean
@Interceptors(LoggingInterceptor.class)
public class UserService {

    @Inject
    UserFacadeLocal userFacade;

    @Inject
    AddressFacadeLocal addressFacade;

    @Inject
    GroupFacadeLocal groupFacade;

    private final String adminString = "admin";

    @PostConstruct
    public void init() {
        if (isAvailableLoginName(adminString)) {
            try {
                User admin = new User(adminString, adminString);
                admin.setPassword(encoding(admin.getPassword()));
                admin.setName(adminString);
                admin.setPosition(Role.ADMIN);
                userFacade.create(admin);
                Group group = new Group(Role.ADMIN, admin);
                groupFacade.create(group);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void addUser(User user) {
        if (isAvailableLoginName(user.getLoginName())) {
            try {
                user.setPassword(encoding(user.getPassword()));
                userFacade.create(user);
                Group group = new Group(Role.USER, user);
                groupFacade.create(group);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            // HibaÜzenet
        }

    }
    
    public void updateUser(User user) {
        userFacade.edit(user);
    }
    
    public List<User> getUserList(){
        return userFacade.findAll();
    }
    
    
   
    public Boolean isAvailableLoginName(String name) {
        List<User> userList = userFacade.findAll();
        for (User user : userList) {
            if (user.getLoginName().equals(name)) {
                return false;
            }
        }
        return true;
    }

    private String encoding(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());
        byte byteData[] = md.digest();
        StringBuilder newPassword = new StringBuilder();
        for (int i = 0; i < byteData.length; i++) {
            newPassword.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        return newPassword.toString();
    }
}
