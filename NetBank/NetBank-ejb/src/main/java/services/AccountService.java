package services;

import entities.Account;
import entities.Group;
import entities.User;
import enums.Role;
import facades.AccountFacadeLocal;
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
public class AccountService {

    @Inject
    UserFacadeLocal userFacade;
    
    @Inject
    AccountFacadeLocal accountFacade;


    public void addAccount(Account account) {
        if (isAvailableAccountNumber(account.getAccountNumber())) { 
                accountFacade.create(account); 
        } else {
            // HibaÜzenet
        }
    }
    
    public List<Account> getAccountList(){
        return accountFacade.findAll();
    }

    public Boolean isAvailableAccountNumber(int number) {
        List<Account> accountList = accountFacade.findAll();
        for (Account account : accountList) {
            if (account.getAccountNumber() == number) {
                return false;
            }
        }
        return true;
    }

}
