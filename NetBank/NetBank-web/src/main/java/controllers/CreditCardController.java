package controllers;

import entities.Account;
import entities.CreditCard;
import enums.CardType;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
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
    
    private Integer cardNumber;
    private Integer password;
    
    private CreditCard card = new CreditCard();
    private CreditCard selectedCard;
    
    private String type = "MASTER_CARD";
    
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Integer cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Integer getPassword() {
        return password;
    }

    public void setPassword(Integer password) {
        this.password = password;
    }

     public String addCreditCard(Account account) {
        card = new CreditCard();
        modifyType();
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
    
    private void modifyType(){
        switch (type) {
            case "MASTER_CARD":
                this.card.setType(CardType.MASTER_CARD);
                break;
            case "MAESTRO":
                this.card.setType(CardType.MAESTRO);
                break;
            default:
                this.card.setType(CardType.VISA);
                break;
        }
        this.type = "MASTER_CARD";;
    }
    
    public String login(){
        CreditCard creditCard = cardService.findByCardNumber(cardNumber);
        if(null!=creditCard && !creditCard.isDestroy()){
            if(creditCard.getPinCode().equals(password)){
                this.card = creditCard;
                this.cardNumber = null;
                this.password = null;
                return "/atm/index?faces-redirect=true";
            }else {
                this.cardNumber = null;
                this.password = null;
                return "/atm/loginerror?faces-redirect=true";
            }
        }else{
            this.cardNumber = null;
            this.password = null;
            return "/atm/loginerror?faces-redirect=true";
        }
    }
    
    public String logout(){
        this.card = new CreditCard();
        this.cardNumber = null;
        this.password = null;
        return "/atm/login?faces-redirect=true";
    }

}
