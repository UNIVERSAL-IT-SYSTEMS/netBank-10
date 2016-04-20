package controllers;

import entities.Account;
import entities.CreditCard;
import entities.Transaction;
import enums.Type;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import services.AccountService;
import services.TransactionService;

/**
 *
 * @author Daniel Szabo
 */
@Named(value = "transactionController")
@SessionScoped
public class TransactionController implements Serializable {

    @Inject
    AccountService accountService;

    @Inject
    TransactionService transactionService;

    private Transaction transaction;
    private Integer recieverNumber;

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public int getRecieverNumber() {
        return recieverNumber;
    }

    public void setRecieverNumber(int recieverNumber) {
        this.recieverNumber = recieverNumber;
    }

    public List<Transaction> getListByAccount(Account account) {
        return transactionService.listByAccount(account);
    }

    public String toAddTransfer(Account account) {
        if (!account.isDestroy()) {
            this.transaction = new Transaction();
            this.recieverNumber = 0;
            transaction.setSender(account);
            return "makeTransfer?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    "Sikertelen tranzakció!", "Zárolt számlán nem alkalmazható tranzakció!"));
            return "listAccountTransaction?faces-redirect=false";
        }
    }

    public String makeTransfer() {
        if (accountService.isAvailableAccountNumber(transaction.getSender().getAccountNumber()) || accountService.isAvailableAccountNumber(recieverNumber)) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    "Sikertelen tranzakció! A kedvezményezett számlaszám nem létezik",""));
            return "makeTransfer?faces-redirect=false";
        } else if (transaction.getSender().getBalance() < transaction.getAmount()) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    "Sikertelen tranzakció! Nincs elég egyenlege az átutalás létrehozásához",""));
            return "makeTransfer?faces-redirect=false";
        } else if (transaction.getAmount() < 1000) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    "Sikertelen tranzakció! Legalább 1000 Ft-t kell átutalni",""));
            return "makeTransfer?faces-redirect=false";
        } else {
            Transaction transfer = new Transaction();
            Account reciever = accountService.findByAccountNumber(recieverNumber);
            if (!reciever.isDestroy()) {
                transfer.setSender(transaction.getSender());
                transfer.setReciever(reciever);
                transfer.setAmount(transaction.getAmount());
                transfer.setDateOfTransaction(new Date());
                transfer.setDescription(transaction.getDescription());
                transfer.setTransactionType(Type.TRANSFER);

                transactionService.addTransaction(transfer);

                transaction.getSender().getSendTransactionList().add(transfer);
                transaction.getSender().setBalance(transaction.getSender().getBalance() - transaction.getAmount());
                reciever.getRecievedTransactionList().add(transfer);
                reciever.setBalance(reciever.getBalance() + transaction.getAmount());

                accountService.updateAccount(transaction.getSender());
                accountService.updateAccount(reciever);

                this.transaction = new Transaction();
                this.recieverNumber = 0;
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                                        "Siekres átutalás", "Sikeres átutalás"));
                return "listAccountTransaction?faces-redirect=true";

            } else {
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                                        "Sikertelen tranzakció! Zárolt bankszámlára nem lehet utalni",""));
                return "makeTransfer?faces-redirect=false";
            }
        }
    }

    public String toWitdrawalInBank(Account account) {
        if (!account.isDestroy()) {
            this.transaction = new Transaction();
            transaction.setSender(account);
            return "makeWithdrawalInBank?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    "Sikertelen tranzakció!", "Zárolt számlán nem alkalmazható tranzakció!"));
            return "listAccountTransaction?faces-redirect=false";
        }
    }

    public String makeWithdrawalInBank() {
        if (transaction.getSender().getBalance() < transaction.getAmount()) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    "Sikertelen kifizetés! Nincs elég egyenlege!",""));
            return "listAccountTransaction?faces-redirect=false";
        }else if(transaction.getAmount()<1000){
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    "Sikertelen kifizetés! Legalább 1000 forintot kell felvenni!",""));
            return "listAccountTransaction?faces-redirect=false";
        }
        else {
            Transaction withdrawal = new Transaction();
            withdrawal.setSender(transaction.getSender());
            withdrawal.setAmount(transaction.getAmount());
            withdrawal.setDateOfTransaction(new Date());
            withdrawal.setTransactionType(Type.WITHDRAWAL_IN_BANK);

            transactionService.addTransaction(withdrawal);

            transaction.getSender().getSendTransactionList().add(withdrawal);
            transaction.getSender().setBalance(transaction.getSender().getBalance() - transaction.getAmount());

            accountService.updateAccount(transaction.getSender());

            this.transaction = new Transaction();
            this.recieverNumber = 0;

            return "listAccountTransaction?faces-redirect=true";
        }
    }

    public String toInpayment(Account account) {
        if (!account.isDestroy()) {
            this.transaction = new Transaction();
            transaction.setReciever(account);
            return "makeInpay?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    "Sikertelen tranzakció! Zárolt számlán nem alkalmazható tranzakció!",""));
            return "listAccountTransaction?faces-redirect=false";
        }
    }

    public String makeInpayment() {

        if (transaction.getAmount() < 0) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    "Sikertelen tranzakció! Legalább 1000 Ft-t kell befizetni",""));
            return "makeInpay?faces-redirect=false";
        }
        Transaction inpayment = new Transaction();
        inpayment.setReciever(transaction.getReciever());
        inpayment.setAmount(transaction.getAmount());
        inpayment.setDateOfTransaction(new Date());
        inpayment.setTransactionType(Type.INPAYMENT);

        transactionService.addTransaction(inpayment);

        transaction.getReciever().getRecievedTransactionList().add(inpayment);
        transaction.getReciever().setBalance(transaction.getReciever().getBalance() + transaction.getAmount());

        accountService.updateAccount(transaction.getReciever());

        this.transaction = new Transaction();
        this.recieverNumber = 0;
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Sikeres befizetés", "Sikeres befizetés"));
        return "listAccountTransaction?faces-redirect=true";
    }

    
    public String toWitdrawalFromAtm(Account account) {
            this.transaction = new Transaction();
            transaction.setSender(account);
            return "/atm/makeWitdrawalFromAtm?faces-redirect=true";
    }
    
    public String makeWithdrawalFromAtm() {
        if (transaction.getSender().getBalance() < transaction.getAmount()) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    "Nincs elég egyenlege!", "Nincs elég egyenlege!"));
            return "/atm/index?faces-redirect=false";
        } else {
            Transaction withdrawal = new Transaction();
            withdrawal.setSender(transaction.getSender());
            withdrawal.setAmount(transaction.getAmount());
            withdrawal.setDateOfTransaction(new Date());
            withdrawal.setTransactionType(Type.WITHDRAWAL_FROM_ATM);

            transactionService.addTransaction(withdrawal);

            transaction.getSender().getSendTransactionList().add(withdrawal);
            transaction.getSender().setBalance(transaction.getSender().getBalance() - transaction.getAmount());

            accountService.updateAccount(transaction.getSender());

            this.transaction = new Transaction();
            this.recieverNumber = 0;

            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    "Sikeres készpénzfelvétel!", "Sikeres készpénzfelvétel!"));
            return "/atm/index?faces-redirect=false";
            
            /*
            return "/atm/index?faces-redirect=true";*/
        }
    }
}
