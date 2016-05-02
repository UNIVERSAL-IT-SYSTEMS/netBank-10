package services;

import entities.Account;
import entities.User;
import facades.AccountFacadeLocal;
import facades.UserFacadeLocal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, "Ez a számlaszám már foglalt");
        }
    }

    public void updateAccount(Account account) {
        accountFacade.edit(account);
    }

    public List<Account> getAccountList() {
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

    public List<Account> getAllByUser(User user) {
        return accountFacade.getAllByUser(user.getId());
    }

    public List<Account> getNotDestroyByUser(User user) {
        return accountFacade.getNotDestroyByUser(user.getId());
    }

    public List<Account> getAllNotDestroy() {
        return accountFacade.getAllNotDestroy();
    }

    public Account findByAccountNumber(int number) {
        List<Account> accountList = accountFacade.findAll();
        for (Account account : accountList) {
            if (account.getAccountNumber() == number) {
                return account;
            }
        }
        return null;
    }
}
