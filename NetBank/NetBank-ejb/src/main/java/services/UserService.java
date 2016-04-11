package services;

import entities.Account;
import entities.Group;
import entities.User;
import entities.RegistratedUser;
import enums.Role;
import facades.GroupFacadeLocal;
import facades.RegistratedUserFacadeLocal;
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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
    RegistratedUserFacadeLocal registrate;

    @Inject
    GroupFacadeLocal groupFacade;

    @Inject
    EmailSendingService emailService;

    private final String ADMINSTRING = "admin";

    private final String REGISTRATION_MESSAGE = "Sikeresen regisztrált az SZDT Bank rendszerébe, "
            + "amint ügyintézőnk jóváhagyja a regisztrációt, azonnal beléphet és használhatja bankunk rendszerét!\n"
            + "\n"
            + "Üdvözlettel, SZDT Bank!";

    private final String ACTIVATION_MESSAGE = "Regisztrációja jóváhagyásra került, "
            + "most már beléphet és használhatja bankunk rendszerét!\n"
            + "\n"
            + "Üdvözlettel, SZDT Bank!";
    
    private final String DECLINE_MESSAGE = "Regisztrációja visszautasításra került, "
            + "ennek oka: "
            + "\n";

    @PersistenceContext(unitName = "com.mycompany_NetBank-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @PostConstruct
    public void init() {
        if (isAvailableLoginName(ADMINSTRING)) {
            try {
                User admin = new User(ADMINSTRING, ADMINSTRING);
                admin.setPassword(encoding(admin.getPassword()));
                admin.setName(ADMINSTRING);
                admin.setPosition(Role.ADMIN);
                admin.setEmail("admin@netbank.com");
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
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, "Ez a felhasználónév már foglalt");
        }
    }

    public void register(RegistratedUser user) {
        if (isAvailableLoginName(user.getLoginName())) {
            registrate.create(user);
            StringBuilder sb = new StringBuilder();
            sb.append("Tisztelt ");
            sb.append(user.getName());
            sb.append(" ! \n");
            sb.append("\n");
            sb.append(REGISTRATION_MESSAGE);
            String messageBody = sb.toString();
            emailService.sendEmail(user.getEmail(), "Üdvözli önt az SZDT Bank", messageBody);
        } else {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, "Ez a felhasználónév már foglalt");
        }
    }

    public void acceptRegister(RegistratedUser regUser) {
        User user = new User();
        user.setAddress(regUser.getAddress());
        user.setDateOfBirth(regUser.getDateOfBirth());
        user.setEmail(regUser.getEmail());
        user.setLoginName(regUser.getLoginName());
        user.setPassword(regUser.getPassword());
        user.setPhoneNumber(regUser.getPhoneNumber());
        user.setName(regUser.getName());
        user.setPosition(Role.USER);
        addUser(user);
        registrate.remove(regUser);

        StringBuilder sb = new StringBuilder();
        sb.append("Tisztelt ");
        sb.append(user.getName());
        sb.append(" ! \n");
        sb.append(" \n");
        sb.append(ACTIVATION_MESSAGE);
        String messageBody = sb.toString();
        emailService.sendEmail(user.getEmail(), "Regisztrációja jóváhagyásra került", messageBody);
    }
    
    public void refuseRegister(RegistratedUser regUser, String message){
        
        StringBuilder sb = new StringBuilder();
        sb.append("Tisztelt ");
        sb.append(regUser.getName());
        sb.append(" ! \n");
        sb.append(" \n");
        sb.append(ACTIVATION_MESSAGE);
        sb.append(message);
        sb.append("\nÜdvözlettel, SZDT Bank!");
        String messageBody = sb.toString();
        emailService.sendEmail(regUser.getEmail(), "Regisztrációja visszautasításra került", messageBody);
        registrate.remove(regUser);
    }    
    public void editUser(User user, Role originalPosition) throws NoSuchAlgorithmException {
        Group group = groupFacade.findByLoginName(user.getLoginName());
        if (originalPosition.equals(user.getPosition())) {
            userFacade.edit(user);
        } else if (user.getPosition().equals(Role.ADMIN)) {
            userFacade.edit(user);
            group.setRole(Role.ADMIN);
            groupFacade.edit(group);
        } else {
            userFacade.edit(user);
            group.setRole(Role.USER);
            groupFacade.edit(group);
        }
    }

    public void removeUser(User user) {
        Group group = groupFacade.findByLoginName(user.getLoginName());
        groupFacade.remove(group);
        userFacade.remove(user);
    }

    public void updateUser(User user) {
        userFacade.edit(user);
    }

    public List<User> getUserList() {
        return userFacade.findAll();
    }

    public List<RegistratedUser> getRegUserList() {
        Query q = em.createNamedQuery("getAll", RegistratedUser.class);
        return q.getResultList();
    }

    public User findById(Long id) {
        return userFacade.find(id);
    }

    public List<Account> getAccountList(User user) {
        return userFacade.find(user.getId()).getAccountList();
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
    
    public Boolean isAvailableEmail(String email) {
        List<User> userList = userFacade.findAll();
        for (User user : userList) {
            if (user.getEmail().equals(email)) {
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

    public User findByLoginName(String name) {
        Query q = em.createNamedQuery("getUserByLoginName", User.class);
        q.setParameter("lName", name);
        User user = (User) q.getSingleResult();
        return user;
    }
}
