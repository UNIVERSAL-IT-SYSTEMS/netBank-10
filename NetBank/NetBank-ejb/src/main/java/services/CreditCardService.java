package services;

import entities.Account;
import entities.CreditCard;
import facades.CreditCardFacadeLocal;
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
public class CreditCardService {

    @Inject
    CreditCardFacadeLocal creditCardFacade;

    public void addCreditCard(CreditCard card) {
        if (isAvailableCardNumber(card.getNumber())) {
            creditCardFacade.create(card);
        } else {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, "Ez a kártyaszám már foglalt");
        }
    }

    public List<CreditCard> getCreditCardListByAccountAdmin(Account account) {
        return creditCardFacade.listByAccountAdmin(account.getId());
    }

    public List<CreditCard> getCreditCardListByAccountUser(Account account) {
        return creditCardFacade.listByAccountUser(account.getId());
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

    public void updateCreditCard(CreditCard card) {
        creditCardFacade.edit(card);
    }

    public List<CreditCard> getCreditCardList() {
        return creditCardFacade.findAll();
    }

    public CreditCard findByCardNumber(Integer cardNumber) {
        CreditCard creditCard = creditCardFacade.findByCardNumber(cardNumber);
        return creditCard;
    }

    public void updateCard(CreditCard card) {
        creditCardFacade.edit(card);
    }
}
