
package services;

import entities.Account;
import entities.CreditCard;
import facades.CreditCardFacadeLocal;
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
public class CreditCardService {
    
    @Inject
    CreditCardFacadeLocal creditCardFacade;

    
    public void addCreditCard(CreditCard card) {
        if (isAvailableCardNumber(card.getNumber())) { 
                creditCardFacade.create(card); 
        } else {
            // HibaÜzenet
        }
    }
    
    public List<CreditCard> getCreditCardListByAccount(Account account) {
        return creditCardFacade.listByAccount(account.getId());
    }
    
    public Boolean isAvailableCardNumber(int number) {
        List<CreditCard> cardList = creditCardFacade.findAll();
        for (CreditCard card : cardList) {
            if (card.getNumber() == number) {
                return false;
            }
        }
        return true;
    }
    
    public void updateCreditCard(CreditCard card){
        creditCardFacade.edit(card);
    }
    
    public void removeCreditCard(CreditCard card) {
        creditCardFacade.remove(card);
    }
    
    public List<CreditCard> getCreditCardList(){
        return creditCardFacade.findAll();
    }
    
    public CreditCard findByCardNumber(Integer cardNumber){
        CreditCard creditCard = creditCardFacade.findByCardNumber(cardNumber);
        return creditCard;
    }
}
