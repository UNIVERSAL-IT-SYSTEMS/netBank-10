package controllers;

import entities.Account;
import entities.CreditCard;
import java.io.Serializable;
import java.util.List;
import java.util.Random;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import services.AccountService;
import services.CreditCardService;

/**
 *
 * @author Daniel Szabo
 */
@Named(value = "creditCardController")
@SessionScoped
public class CreditCardController implements Serializable {
    
    @Inject
    CreditCardService cardService;
    
    @Inject
    AccountService accountService;
    
    private CreditCard card = new CreditCard();
    private CreditCard selectedCard;

    public CreditCardController() {
    }

    public CreditCard getCard() {
        return card;
    }

    public void setCard(CreditCard card) {
        this.card = card;
    }

    public CreditCard getSelectedCard() {
        return selectedCard;
    }

    public void setSelectedCard(CreditCard selectedCard) {
        this.selectedCard = selectedCard;
    }
    
     public String addCreditCard(Account account) {

        card = new CreditCard();
        Random rand = new Random();
        
        card.setNumber(rand.nextInt(9000000) + 1000000);
        card.setAccount(account);
        card.setPinCode(rand.nextInt(9000) + 1000);

        cardService.addCreditCard(card);
        account.getCreditCardList().add(card);
        accountService.updateAccount(account);


        this.card = new CreditCard();

        return "listAccountTransaction?faces-redirect=true";
    }
    
    public List<CreditCard> getCreditCardListByAccount(Account account){
        return cardService.getCreditCardListByAccount(account);
    }
    
    

}
