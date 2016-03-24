package services;

import entities.Account;
import entities.Transaction;
import entities.User;
import facades.TransactionFacadeLocal;
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
public class TransactionService {
    
    @Inject
    TransactionFacadeLocal transactionFacade;
    
    public void addTransaction(Transaction transaction) {
        transactionFacade.create(transaction);
    }
    
    public void removeAccount(Transaction transaction) {
        transactionFacade.remove(transaction);
    }
    
    public List<Transaction> getTransactionList(){
        return transactionFacade.findAll();
    }
    
    public List<Transaction> listByAccount(Account account){
        return transactionFacade.specifyTransaction(account.getId());
    }
}
