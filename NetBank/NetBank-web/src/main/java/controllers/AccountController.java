package controllers;

import entities.Account;
import entities.User;
import java.io.Serializable;
import java.util.List;
import java.util.Random;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import services.AccountService;
import services.UserService;

/**
 *
 * @author Daniel Szabo
 */
@Named(value = "accountController")
@SessionScoped
public class AccountController implements Serializable {

    @Inject
    AccountService accountService;

    @Inject
    UserService userService;

    private Account account = new Account();
    private Account selectedAccount;

    public AccountController() {
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getSelectedAccount() {
        return selectedAccount;
    }

    public void setSelectedAccount(Account selectedAccount) {
        this.selectedAccount = selectedAccount;
    }

    public String adminAddAccount(User user) {

        account = new Account();
        Random rand = new Random();

        account.setBalance(0);

        account.setAccountNumber(rand.nextInt(9000000) + 1000000);
        account.setBalance(0);
        account.setUser(user);

        accountService.addAccount(account);

        user.getAccountList().add(account);
        userService.updateUser(user);

        this.account = new Account();

        return "listUserAccount?faces-redirect=true";
    }

    public String deleteAccount() {
        if (selectedAccount != null) {

            accountService.removeAccount(selectedAccount);
            selectedAccount = new Account();
            return "listUserAccount?faces-redirect=true";

        }
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Account has not selected", "You did not select any account!"));
        return "listUserAccount?faces-redirect=false";
    }

    public List<Account> findAll(){
        return accountService.getAccountList();
    }
    
    public List<Account> getListByUser(User user) {
        return accountService.listByUser(user);
    }

    public int getAccountNumber(User user) {
        return accountService.listByUser(user).size();
    }


    public String selectedToTrancasctionList() {
        if (selectedAccount != null) {
            this.account = selectedAccount;
            selectedAccount = new Account();
            return "/admin/user/account/transaction/listAccountTransaction?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Bankszámla nincs kiválasztva", "Bankszámla nincs kiválasztva"));

            return "listUserAccount?faces-redirect=false";
        }
    }
}
