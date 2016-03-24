package services;

import entities.Account;
import entities.User;
import facades.AccountFacadeLocal;
import facades.UserFacadeLocal;
import java.util.List;
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
    
    public void updateAccount(Account account){
        accountFacade.edit(account);
    }
    
    public void removeAccount(Account account) {
        accountFacade.remove(account);
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
   
    public List<Account> listByUser(User user){
        return accountFacade.specifyAccount(user.getId());
    }
   
    public Account findByAccountNumber(int number){
        List<Account> accountList = accountFacade.findAll();
        for (Account account : accountList) {
            if (account.getAccountNumber() == number) {
                return account;
            }
        }
        return null;
    }
}
