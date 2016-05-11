package entities;

import enums.CardType;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Daniel Szabo
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "getListByAccountAdmin", query = "SELECT c FROM CreditCard c WHERE c.account.id = :rId"),
    @NamedQuery(name = "getListByAccountUser", query = "SELECT c FROM CreditCard c WHERE c.account.id = :rId AND c.destroy = false"),
    @NamedQuery(name = "findByCardNumber", query = "SELECT c FROM CreditCard c WHERE c.number = :rNumber")
}) 
public class CreditCard implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "card_id")
    private Long id;

    @NotNull
    private Integer number;

    @Enumerated(EnumType.STRING)
    private CardType type;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Min(1000)
    @Max(9999)
    @NotNull
    private Integer pinCode;

    private boolean destroy = false;

    public CreditCard() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Integer getPinCode() {
        return pinCode;
    }

    public void setPinCode(Integer pinCode) {
        this.pinCode = pinCode;
    }

    public boolean isDestroy() {
        return destroy;
    }

    public void setDestroy(boolean destroy) {
        this.destroy = destroy;
    }

}
