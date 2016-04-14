package validators;

import controllers.CreditCardController;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import services.CreditCardService;

/**
 *
 * @author Daniel Szabo
 */
@ManagedBean(name="currentPinCodeValidator")
@RequestScoped
public class CurrentPinCodeValidator implements Validator {

    @ManagedProperty(value="#{creditCardController}")
    private CreditCardController cardContoroller;
    
    @Inject
    private CreditCardService cardService;
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

            Integer input = Integer.parseInt(value.toString());
            Integer current = cardContoroller.getCard().getPinCode();
            
            if(!input.equals(current)){
                throw new ValidatorException(new
                        FacesMessage("Hibás PIN kód!"));
            }

    }

    public CreditCardController getCardContoroller() {
        return cardContoroller;
    }

    public void setCardContoroller(CreditCardController cardContoroller) {
        this.cardContoroller = cardContoroller;
    }

    
    
}
