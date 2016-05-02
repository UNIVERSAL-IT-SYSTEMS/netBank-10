package controllers;

import entities.Account;
import entities.CreditCard;
import enums.CardType;
import java.io.Serializable;
import java.util.List;
import java.util.Random;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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

    private Integer newPassword;

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

    public Integer getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(Integer newPassword) {
        this.newPassword = newPassword;
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

    public List<CreditCard> getCreditCardListByAccountAdmin(Account account) {
        return cardService.getCreditCardListByAccountAdmin(account);
    }

    public List<CreditCard> getCreditCardListByAccountUser(Account account) {
        return cardService.getCreditCardListByAccountUser(account);
    }

    private void modifyType() {
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

    public String login() {
        CreditCard creditCard = cardService.findByCardNumber(cardNumber);
        if (null != creditCard && !creditCard.isDestroy()) {
            if (creditCard.getPinCode().equals(password)) {
                this.card = creditCard;
                this.cardNumber = null;
                this.password = null;
                return "/atm/index?faces-redirect=true";
            } else {
                this.cardNumber = null;
                this.password = null;
                return "/atm/loginerror?faces-redirect=true";
            }
        } else {
            this.cardNumber = null;
            this.password = null;
            return "/atm/loginerror?faces-redirect=true";
        }
    }

    public String changePinCode() {
        if (newPassword != null) {
            card.setPinCode(newPassword);
            cardService.updateCard(card);
        }
        return "/atm/index?faces-redirect=true";
    }

    public String logout() {
        this.card = new CreditCard();
        this.cardNumber = null;
        this.password = null;
        return "/atm/login?faces-redirect=true";
    }

    public String deleteCard() {
        if (selectedCard != null) {
            if (selectedCard.isDestroy()) {
                selectedCard.setDestroy(false);
            } else {
                selectedCard.setDestroy(true);
            }
            cardService.updateCard(selectedCard);
            selectedCard = new CreditCard();
            return "listAccountTransaction?faces-redirect=true";

        }
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Kérem válassza ki a zárolni / aktiválni kívánt bankkártyát!", ""));
        return "listAccountTransaction?faces-redirect=false";
    }
}
